import java.io.IOException;


public class SpectralAnalysisTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		SpectralAnalysis sa = new SpectralAnalysis("FunkGuitar.au");
		Synthesizer synth = new Synthesizer();
		synth.setAudioData(sa.getAudioData());
		synth.play();
		sa.ConvertFileToAIFF("FG.aiff");
		
	}

}
