/**
 * Play A4.
 * GNU General Public License v3, February 2011.
 * @author Adam Steinberger
 */

public class A4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Chord chord = new Chord(60.0,1.0);
		chord.addNote("A4");
		for (int i = 0; i < 100; i++) {
			chord.playNotes();
		} // end for
	} // end main()

} // end A4 class
