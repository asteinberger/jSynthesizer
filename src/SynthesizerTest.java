/**
 * Test the functionality of the Synthesizer class.
 * GNU General Public License v3, February 2011.
 * @author Adam Steinberger
 */

public class SynthesizerTest {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		Synthesizer synthesizer = new Synthesizer(120.0, 2.0, "sine");
		synthesizer.setAttack(0.5f);
		synthesizer.setDecay(0.5f);
		synthesizer.setSustainLevel(0.5f);
		synthesizer.setRelease(0.5f);
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
		
		synthesizer.tone(0.0);
		synthesizer.play();
		
//		synthesizer.vibrato(440.0, 24000.0, 0.2, 4.4);
////		System.out.println(synthesizer);
//		synthesizer.play();
		
		synthesizer.vibrato(440.0, 1000.0, 1.0/10.0, 2.0);
//		System.out.println(synthesizer);
		synthesizer.play();

	} // end main()

} // end SynthesizerTest class
