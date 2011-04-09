/**
 * Synthesize sound using the Java Sound API.
 * The following program was originally inspired by R. G. Baldwin's AudioSynth01.java,
 * and modified portions of his code have been included in this program. The source code
 * for R. G. Baldwin's original synthesizer program can be found at
 * http://www.developer.com/java/other/article.php/2226701#Complete%20Program%20Listings.
 * GNU General Public License v3, February 2011.
 * @author Adam Steinberger
 */

import javax.sound.sampled.*;
import java.io.*;
import java.nio.*;
import java.util.Arrays;

public class Synthesizer {

	// General instance variables used to create a SourceDataLine object
	protected AudioFormat audioFormat;
	protected AudioInputStream audioInputStream;
	protected SourceDataLine sourceDataLine;
	private double[] sineTable = new double [512];

	// audio format parameters
	private final float sampleRate = 44100.0F; // Allowable 8000,11025,16000,22050,44100
	private final int sampleSizeInBits = 16; // Allowable 8,16
	private int channels = 1; // Allowable 1,2
	private final boolean signed = true; // Allowable true,false
	private final boolean bigEndian = true; // Allowable true,false
	private final int maxVolume = 24000;
	private double bpm;
	private double beats;
	private double seconds;
	private double oneSecondMono = sampleRate * 2.0;
	private int bytesPerSampMono = 2; // 1 sample = 1 channel, 2 bytes per channel
	private double oneSecondStereo = sampleRate * 4.0;
	private int bytesPerSampStereo = 4; // 1 sample = 2 channels, 2 bytes per channel
	private int byteLengthMono;
	private double beatLengthMono;
	private int byteLengthStereo;
	private double beatLengthStereo;
	private byte[] audioData;
	private int samples;
	private double samplesPerBeat;
	private ByteBuffer byteBuffer;
	protected ShortBuffer shortBuffer;
	private String wave;
	private float attack;
	private float decay;
	private float sustainLevel;
	private float release;
	private int decayStart;
	private int sustainStart;
	private int releaseStart;

	/**
	 * Default Synthesizer constructor with 100 bpm, 1 beat, and sine wave
	 */
	public Synthesizer() {
		this.bpm = 100.0;
		this.beats = 1.0;
		this.wave = "sine";
		this.attack = 0.95f;
		this.decay = 0.95f;
		this.sustainLevel = 0.65f;
		this.release = 0.65f;
		updateVars();
		buildTable();
	} // end Synthesizer() constructor
	
	/**
	 * Synthesizer constructor
	 * @param bpm beats per minute; must be greater than zero
	 * @param beats duration of sound generated
	 * @param wave which wave type to use for sound synthesis; sine, sawtooth, or triangle
	 */
	public Synthesizer(double bpm, double beats, String wave) {
		this.bpm = bpm;
		this.beats = beats;
		this.wave = wave;
		this.attack = 0.95f;
		this.decay = 0.95f;
		this.sustainLevel = 0.65f;
		this.release = 0.65f;
		updateVars();
		buildTable();
	} // end Synthesizer() constructor
	
	/**
	 * Synthesizer constructor
	 * @param bpm beats per minute; must be greater than zero
	 * @param beats duration of sound generated
	 * @param wave which wave type to use for sound synthesis; sine, sawtooth, or triangle
	 */
	public Synthesizer(double duration, String wave) {
		this.seconds = duration;
		this.bpm = 60.0/duration;
		this.beats = 1.0;
		this.wave = wave;
		this.attack = 0.95f;
		this.decay = 0.95f;
		this.sustainLevel = 0.65f;
		this.release = 0.65f;
		updateVars();
		buildTable();
	} // end Synthesizer() constructor
	
	private void buildTable() {
		double freq = 1.0/512.0;
		for (int i = 0; i < 512; i++) {
			this.sineTable[i] = sine(freq,i);
		} // end for
		System.out.println(Arrays.toString(this.sineTable));
	} // end buildTable()

	public String getWave() {
		return this.wave;
	} // end getWave()

	public void setWave(String wave) {
		this.wave = wave;
	} // end setWave()

	public AudioFormat getAudioFormat() {
		return this.audioFormat;
	} // end getAudioFormat()

	public void setAudioFormat(AudioFormat audioFormat) {
		this.audioFormat = audioFormat;
	} // end setAudioFormat()

	public AudioInputStream getAudioInputStream() {
		return this.audioInputStream;
	} // end getAudioInputStream()

	public void setAudioInputStream(AudioInputStream audioInputStream) {
		this.audioInputStream = audioInputStream;
	} // end setAudioInputStream()

	public SourceDataLine getSourceDataLine() {
		return this.sourceDataLine;
	} // end getSourceDataLine()

	public void setSourceDataLine(SourceDataLine sourceDataLine) {
		this.sourceDataLine = sourceDataLine;
	} // end setSourceDataLine()

	public float getSampleRate() {
		return this.sampleRate;
	} // end getSampleRate()

	public int getSampleSizeInBits() {
		return this.sampleSizeInBits;
	} // end getSampleSizeInBits()

	public int getChannels() {
		return this.channels;
	} // end getChannels()

	public void setChannels(int channels) {
		this.channels = channels;
	} // end setChannels()

	public boolean isSigned() {
		return this.signed;
	} // end isSigned()

	public boolean isBigEndian() {
		return this.bigEndian;
	} // end isBigEndian()

	public int getMaxVolume() {
		return this.maxVolume;
	} // end getMaxVolume()

	public double getBpm() {
		return this.bpm;
	} // end getBpm()

	public void setBpm(double bpm) {
		this.bpm = bpm;
		updateVars();
	} // end setBpm()

	public double getBeats() {
		return this.beats;
	} // end getBeats()

	public void setBeats(double beats) {
		this.beats = beats;
		updateVars();
	} // end setBeats()
	
	public void setAttack(float attack) {
		this.attack = attack;
		if (this.attack > 0.9f) {
			this.attack = 0.9f;
		} // end if
		updateVars();
	} // end setAttack()
	
	public float getAttack() {
		return this.attack;
	} // end getAttack()

	public float getDecay() {
		return this.decay;
	} // end getDecay()

	public float getSustainLevel() {
		return this.sustainLevel;
	} // end getSustainLevel()

	public float getRelease() {
		return this.release;
	} // end getRelease()

	public void setDecay(float decay) {
		this.decay = decay;
		if (this.decay > 0.9f) {
			this.decay = 0.9f;
		} // end if
		updateVars();
	} // end setDecay()
	
	public void setSustainLevel(float sustainLevel) {
		this.sustainLevel = sustainLevel;
		if (this.sustainLevel > 0.9f) {
			this.sustainLevel = 0.9f;
		} // end if
		updateVars();
	} // end setSustainLevel()
	
	public void setRelease(float release) {
		this.release = release;
		if (this.release > 0.9f) {
			this.release = 0.9f;
		} // end if
		updateVars();
	} // end setSustainLevel()

	public double getSeconds() {
		return this.seconds;
	} // end getSeconds()

	public double getOneSecondMono() {
		return this.oneSecondMono;
	} // end getOneSecondMono()

	public int getBytesPerSampMono() {
		return this.bytesPerSampMono;
	} // end getBytesPerSampMono()

	public double getOneSecondStereo() {
		return this.oneSecondStereo;
	} // end getOneSecondStereo()

	public int getBytesPerSampStereo() {
		return this.bytesPerSampStereo;
	} // end getBytesPerSampStereo()

	public int getByteLengthMono() {
		return this.byteLengthMono;
	} // end getByteLengthMono()

	public double getBeatLengthMono() {
		return this.beatLengthMono;
	} // end getBeatLengthMono()

	public int getByteLengthStereo() {
		return this.byteLengthStereo;
	} // end getByteLengthStereo()

	public double getBeatLengthStereo() {
		return this.beatLengthStereo;
	} // end getBeatLengthStereo()

	public byte[] getAudioData() {
		return this.audioData;
	} // end getAudioData()

	public void setAudioData(byte[] audioData) {
		this.audioData = audioData;
	} // end setAudioData()

	public int getSamples() {
		return this.samples;
	} // end getSamples()

	public double getSamplesPerBeat() {
		return this.samplesPerBeat;
	} // end getSamplesPerBeat()

	private void updateVars() {
		this.seconds = this.beats/this.bpm*60.0;
		this.byteLengthMono = (int) (this.oneSecondMono * this.beats * 60.0 / this.bpm);
		this.beatLengthMono = this.byteLengthMono / this.beats;
		this.byteLengthStereo = (int) (this.oneSecondStereo * this.beats * 60.0 / this.bpm);
		this.beatLengthStereo = this.byteLengthStereo / this.beats;
		this.audioData = new byte[this.byteLengthStereo];
		this.samples = this.byteLengthMono/this.bytesPerSampMono;
		this.samplesPerBeat = this.samples/this.beats;
		this.decayStart = (int) this.attack*this.samples/4;
		this.sustainStart = this.decayStart + (int) this.decay*this.samples/4;
		this.releaseStart = this.samples - (int) this.release*this.samples/4;
	} // end updateVars()

	private void wrapBuffer() {
		this.byteBuffer = ByteBuffer.wrap(this.audioData);
		this.shortBuffer = this.byteBuffer.asShortBuffer();
	} // end wrapBuffer()

	private double wave(double freq, double time, String wave) {
		double result = 0;
		if (wave.equals("sine")) {
			result = sine(freq,time);
		} else if (wave.equals("sawtooth")) {
			result = sawtooth(freq,time);
		} else if (wave.equals("triangle")) {
			result = triangle(freq,time);
		} // end if
		return result;
	} // end wave()
	
	private double envelope(double time) {
		double result = 0.0;
		if (this.sustainLevel > 0.9f) {
			this.sustainLevel = 0.9f;
		} // end if
		if (time < decayStart) {
			double slope = this.maxVolume/this.decayStart;
			result = slope*time;
		} else if ((time >= this.decayStart) && (time < this.sustainStart)) {
			double slope = this.maxVolume*(this.sustainLevel-1)/(this.sustainStart-this.decayStart);
			result = slope*(time-this.decayStart)+this.maxVolume;
		} else if ((time >= this.sustainStart) && (time < this.releaseStart)) {
			result = this.maxVolume*this.sustainLevel;
		} else {
			double slope = -1*this.maxVolume*this.sustainLevel/(this.samples-this.releaseStart);
			result = slope*(time-this.samples);
		} // end if
		return result;
	} // end envelope

	private double sine(double freq, double time) {
		return Math.sin(2.0*Math.PI*freq*time);
	} // end sine()

	private double sawtooth(double freq, double time) {
		return 2.0*(freq*time - Math.floor(freq*time + 0.5));
	} // end sawtooth()

	private double triangle(double freq, double time) {
		return 2.0*Math.abs(sawtooth(freq, time))-1.0;
	} // end triangle()

	protected double average(double[] freqs) {
		double sum = 0;
		for (int index = 0; index < freqs.length; index++)
			sum += freqs[index];
		return sum / (double) freqs.length;
	} // end average()

	/**
	 * Bounce synthetic audio data to .AU audio file
	 * @param filename DO NOT include filename extension
	 */
	public void bounce(String filename) {

		try {

			InputStream byteArrayInputStream;
			int size = this.audioData.length;
			if (this.channels == 1)
				size = size / 2;

			// Get an input stream on the byte array containing the data
			byteArrayInputStream = new ByteArrayInputStream(this.audioData);

			// Get the required audio format
			this.audioFormat = new AudioFormat(this.sampleRate, this.sampleSizeInBits,
					this.channels, this.signed, this.bigEndian);

			// Get an audio input stream from the ByteArrayInputStream
			this.audioInputStream = new AudioInputStream(byteArrayInputStream,
					this.audioFormat, size/this.audioFormat.getFrameSize());

			// Get info on the required data line
			DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, this.audioFormat);

			// Get a SourceDataLine object
			this.sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);

			// Write the data to an output file with the name provided by the text field
			// in the South of the GUI.
			try {
				AudioSystem.write(this.audioInputStream, AudioFileFormat.Type.AU,
						new File(filename + ".au"));
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			} // end try/catch

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		} // end try/catch

	} // end fileData()

	/**
	 * Play synthetic audio data through audio card speakers
	 */
	public void play() {

		try {

			System.out.println(Arrays.toString(this.audioData));
			
			InputStream byteArrayInputStream;
			int size = this.audioData.length;
			if (this.channels == 1)
				size = size / 2;

			// Get an input stream on the byte array containing the data
			byteArrayInputStream = new ByteArrayInputStream(this.audioData);

			// Get the required audio format
			this.audioFormat = new AudioFormat(this.sampleRate, this.sampleSizeInBits,
					this.channels, this.signed, this.bigEndian);

			// Get an audio input stream from the ByteArrayInputStream
			this.audioInputStream = new AudioInputStream(byteArrayInputStream,
					this.audioFormat, size/this.audioFormat.getFrameSize());

			// Get info on the required data line
			DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, this.audioFormat);

			// Get a SourceDataLine object
			this.sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);

			// Create a thread to play back the data and start it running.  It will run until all
			// the data has been played back
			Latch l = new Latch(1);
			ListenThread lt = new ListenThread(l,this.sourceDataLine,this.audioFormat,this.audioInputStream);
			Thread t = new Thread(lt);
			t.start();
			l.awaitZero();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		} // end try/catch

	} // end play()

	/**
	 * Generate a monaural tone at a given frequency
	 * @param freq frequency; must be greater than zero Hz
	 */
	public void tone(double freq) {

		wrapBuffer();
		this.channels = 1; // Java allows 1 or 2

		for (int count = 0; count < this.samples; count++) {
			double time = count/this.sampleRate;
			double value = wave(freq,time,this.wave);
			value += 0.03125*wave(2*freq, time, this.wave);
			value += 0.015625*wave(4*freq, time, this.wave);
			double gain = envelope(time);
//			System.out.println(freq);
			if (freq<260.0) {
//				gain += 8000.0;
				value += 0.03125*wave(8*freq, time, this.wave);
				value += 0.03125*wave(16*freq, time, this.wave);
				value += 0.03125*wave(32*freq, time, this.wave);
			}
//			double[] output = {freq,gain*value};
//			System.out.println(Arrays.toString(output));
			this.shortBuffer.put((short) (gain*value));
		} // end for

	} // end tone()

	/**
	 * Generate a monaural tone of multiple frequencies
	 * @param freqs frequencies array; must have one or more freq greater than zero Hz
	 */
	public void tones(double[] freqs) {

		wrapBuffer();
		this.channels = 1; // Java allows 1 or 2

		for (int count = 0; count < this.samples; count++) {

			double time = count/this.sampleRate;
			double vals [] = new double [freqs.length];

			for (int count2 = 0; count2 < freqs.length; count2++) {
				vals[count2] = wave(freqs[count2],time,this.wave);
				vals[count2] += 0.03125*wave(2*freqs[count2], time, this.wave);
				vals[count2] += 0.015625*wave(4*freqs[count2], time, this.wave);
			} // end for

			double value = average(vals);
			double gain = envelope(time);
//			System.out.println(freqs[0]);
			if (freqs[0]<260.0) {
				gain += 1000.0;
			} // end if
			this.shortBuffer.put((short) (gain*value));

		} // end for

	} // end tones()

	public double unitGenerator(double freq, double amp, double time) {
		return amp*wave(freq,time,this.wave);
	} // end unitGenHelper()

	public void vibrato(double centerFreq, double centerAmp, double modRatio, double fmIndex) {
		wrapBuffer();
		this.channels = 1; // Java allows 1 or 2
		double modFreq = modRatio * centerFreq;
		double deltaFreq = fmIndex * modFreq;
//		System.out.println(modFreq);
		for (int count = 0; count < this.samples; count++) {
			double time = count/this.sampleRate;
			double freq = centerFreq + unitGenerator(modFreq,deltaFreq,time);
			double value = unitGenerator(freq,centerAmp,time);
//			double[] output = {freq,modFreq,value};
//			System.out.println(Arrays.toString(output));
			this.shortBuffer.put((short) (value));
		} // end for
	} // end vibrato()

	public String toString() {
		return "Synthesizer [audioFormat=" + audioFormat + ", audioInputStream="
				+ audioInputStream + ", sourceDataLine=" + sourceDataLine
				+ ", sampleRate=" + sampleRate + ", sampleSizeInBits="
				+ sampleSizeInBits + ", channels=" + channels + ", signed="
				+ signed + ", bigEndian=" + bigEndian + ", maxVolume="
				+ maxVolume + ", bpm=" + bpm + ", beats=" + beats
				+ ", audioData=" + Arrays.toString(audioData) + ", byteBuffer="
				+ byteBuffer + ", shortBuffer=" + shortBuffer + "]";
	} // end toString()

	/**
	 * ListenThread inner class for audio data play back
	 * @author asteinb1
	 *
	 */
	protected class ListenThread implements Runnable {

		//This is a working buffer used to transfer the data between the AudioInputStream and
		// the SourceDataLine.  The size is rather arbitrary.
		private byte playBuffer[] = new byte[16384];
		private Latch listenLatch;
		private SourceDataLine sourceDataLine;
		private AudioFormat audioFormat;
		private AudioInputStream audioInputStream;

		public ListenThread(Latch l, SourceDataLine sdl, AudioFormat af, AudioInputStream ais) {
			this.listenLatch = l;
			this.sourceDataLine = sdl;
			this.audioFormat = af;
			this.audioInputStream = ais;
		} // end ListenThread constructor

		public void run() {

			try {

				// Open and start the SourceDataLine
				this.sourceDataLine.open(this.audioFormat);
				this.sourceDataLine.start();

				int cnt;

				// Transfer the audio data to the speakers
				while ((cnt = this.audioInputStream.read(this.playBuffer, 0, this.playBuffer.length)) != -1) {

					// Keep looping until the input read method returns -1 for empty stream.
					if (cnt > 0) {

						// Write data to the internal buffer of the data line where it will be
						// delivered to the speakers in real time
						this.sourceDataLine.write(this.playBuffer, 0, cnt);

					} // end if

				} // end while

				// Block and wait for internal buffer of the SourceDataLine to become empty.
				this.sourceDataLine.drain();

				// Finish with the SourceDataLine
				this.sourceDataLine.stop();
				this.sourceDataLine.close();
				this.listenLatch.countDown();

			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			} // end try/catch

		} // end run()

	} // end ListenThread class

} // end Synthesizer class