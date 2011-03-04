/**
 * Test the functionality of the TwelveTone class.
 * GNU General Public License v3, February 2011.
 * @author Adam Steinberger
 */


public class TwelveToneTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TwelveTone twelveTone = new TwelveTone(50.0,1.0);
		twelveTone.playNote("A3");
	}

}
