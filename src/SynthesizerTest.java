/** SynthesizerTest.java
 * Created by Adam Steinberger
 * GNU General Public License v3, February 2011
 */

public class SynthesizerTest {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		Synthesizer synthesizer = new Synthesizer(60.0, 1.0, "sine");
//		System.out.println(synthesizer);

		synthesizer.tone(440.0);
//		System.out.println(synthesizer);
		synthesizer.play();
		
		double[] freqs = new double [3];
		freqs[0] = 440.0;
		freqs[1] = 660.0;
		freqs[2] = 330.0;
		synthesizer.tones(freqs);
		synthesizer.play();
		
		synthesizer.vibrato(440.0, 8000.0, 0.2, 6.0);
//		System.out.println(synthesizer);
		synthesizer.play();
		
		synthesizer.vibrato(440.0, 16000.0, 1.0, 0.0);
//		System.out.println(synthesizer);
		synthesizer.play();

	} // end main()

} // end SynthesizerTest class
