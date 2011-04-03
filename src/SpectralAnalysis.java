import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
//import java.nio.ByteBuffer;
//import java.nio.ShortBuffer;
import java.util.Arrays;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SpectralAnalysis {

	protected AudioFormat audioFormat;
	private String filename;
	private byte[] audioData;
	private static int totalFramesRead;

	public SpectralAnalysis(String f) {
		this.setFilename(f);
		this.setAudioData(getAudioData(f));
	} // end SpectralAnalysis constructor

	public void ConvertFileToAIFF(String outputPath) throws IOException {

		AudioFileFormat inFileFormat;
		File inFile;
		File outFile;

		try {
			inFile = new File(this.filename);
			outFile = new File(outputPath);
		} catch (NullPointerException ex) {
			System.out.println("Error: one of the ConvertFileToAIFF"
					+ " parameters is null!");
			return;
		}

		try {
			// query file type
			inFileFormat = AudioSystem.getAudioFileFormat(inFile);
			if (inFileFormat.getType() != AudioFileFormat.Type.AIFF) {
				// inFile is not AIFF, so let's try to convert it.
				AudioInputStream inFileAIS = AudioSystem
						.getAudioInputStream(inFile);
				inFileAIS.reset(); // rewind
				if (AudioSystem.isFileTypeSupported(AudioFileFormat.Type.AIFF,
						inFileAIS)) {
					// inFileAIS can be converted to AIFF.
					// so write the AudioInputStream to the
					// output file.
					AudioSystem.write(inFileAIS, AudioFileFormat.Type.AIFF,
							outFile);
					System.out.println("Successfully made AIFF file, "
							+ outFile.getPath() + ", from "
							+ inFileFormat.getType() + " file, "
							+ inFile.getPath() + ".");
					inFileAIS.close();
					return; // All done now
				} else
					System.out.println("Warning: AIFF conversion of "
							+ inFile.getPath()
							+ " is not currently supported by AudioSystem.");
			} else
				System.out.println("Input file " + inFile.getPath()
						+ " is AIFF. Conversion is unnecessary.");
		} catch (UnsupportedAudioFileException e) {
			System.out.println("Error: " + inFile.getPath()
					+ " is not a supported audio file type!");

		}
	}

	public static byte[] getAudioData(String filename) {

		setTotalFramesRead(0);
		File fileIn = new File(filename);

		// somePathName is a pre-existing string whose value was
		// based on a user selection.
		try {

			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileIn);
			int bytesPerFrame = audioInputStream.getFormat().getFrameSize();

			if (bytesPerFrame == AudioSystem.NOT_SPECIFIED) {
				// some audio formats may have unspecified frame size
				// in that case we may read any amount of bytes
				bytesPerFrame = 1;
			}

			// Set an arbitrary buffer size of 1024 frames.
			int numBytes = 1024 * bytesPerFrame;
			byte[] audioBytes = new byte[numBytes];

			try {

				int numBytesRead = 0;
				int numFramesRead = 0;

				// Try to read numBytes bytes from the file.
				while ((numBytesRead = audioInputStream.read(audioBytes)) != -1) {

					// Calculate the number of frames actually read.
					numFramesRead = numBytesRead / bytesPerFrame;
					setTotalFramesRead(getTotalFramesRead() + numFramesRead);

					System.out.println(numBytesRead);
					// Here, do something useful with the audio data that's
					// now in the audioBytes array...

				}

				System.out.println(Arrays.toString(audioBytes));

				double[] audioLeft = new double [audioBytes.length/2+1];
				double[] audioRight = new double [audioBytes.length/2+1];
				
				for (int i = 0; i < audioBytes.length; i += 4) {
					double left = (audioBytes[i] & 0xff) | (audioBytes[i + 1] << 8);
					double right = (audioBytes[i + 2] & 0xff) | (audioBytes[i + 3] << 8);
					audioLeft[i/4] = left;
					audioRight[i/4] = right;
					out(i+","+left+","+right, "audioData.csv");
				} // end for
				
				Fourier f = new Fourier(audioLeft, audioRight);
				double[][] spectrum = f.transform();
				for (int i = 0; i < spectrum.length; i++) {
					System.out.print("["+i+", "+spectrum[i][0]+", "+spectrum[i][1]+"] ");
					out(i+","+spectrum[i][0]+","+spectrum[i][1], "spectrumData.csv");
				} // end for

				return audioBytes;

			} catch (Exception ex) {
				// Handle the error...

			}

		} catch (Exception e) {

			// Handle the error...

		}

		return null;

	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public byte[] getAudioData() {
		return audioData;
	}

	public void setAudioData(byte[] audioData) {
		this.audioData = audioData;
	}

	public static int getTotalFramesRead() {
		return totalFramesRead;
	}

	public static void setTotalFramesRead(int totalFramesRead) {
		SpectralAnalysis.totalFramesRead = totalFramesRead;
	}

	/**
	 * Append data to serverData CVS file
	 * 
	 * @param event
	 * @param socket
	 * @param duration
	 * @throws IOException
	 */
	private static void out(String line, String filename) throws IOException {

		// Create FileWriter object for clientData CSV
		FileWriter dataStream = new FileWriter(filename, true);

		// Create BufferedWriter object to append to clientData CSV
		BufferedWriter dataFile = new BufferedWriter(dataStream);

		// Write data to file
		dataFile.write(line+"\n");

		// Close BufferedWriter object
		dataFile.close();

		// Close FileWriter object
		dataStream.close();

	} // end out()

}
