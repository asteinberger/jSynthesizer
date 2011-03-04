/** ScaleTest.java
 * Created by Adam Steinberger
 * GNU General Public License v3, February 2011
 */

public class ScaleTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scale scale = new Scale();
		int[] chord = new int [2];
		chord[0] = 45;
		chord[1] = 50;
		scale.playNotes(chord);
		scale.loopNotes();
	} // end main()

} // end ScaleTest class
