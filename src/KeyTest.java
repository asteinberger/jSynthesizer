/**
 * Test the functionality of the Key class.
 * GNU General Public License v3, February 2011.
 * @author Adam Steinberger
 */

public class KeyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Key key = new Key("C", "major", 200.0, 1.0);
		Chord tonic = key.note(1,3);
		Chord supertonic = key.note(2,3);
		Chord mediant = key.note(3,3);
		Chord subdominant = key.note(4,3);
		Chord dominant = key.note(5,3);
		Chord submediant = key.note(6,3);
		Chord leadingTone = key.note(7,3);
		Chord octave = key.note(1,4);
		tonic.playNotes();
		supertonic.playNotes();
		mediant.playNotes();
		subdominant.playNotes();
		dominant.playNotes();
		submediant.playNotes();
		leadingTone.playNotes();
		octave.playNotes();
		key.scaleUp(3);
		key = new Key("F#", "melodic minor", 200.0, 0.75);
		key.scaleDown(3);
		Chord chord1 = key.triad(1,3);
		Chord chord4 = key.triad(4,3);
		Chord chord5 = key.triad(5,3);
		chord1.playNotes();
		chord4.playNotes();
		chord1.playNotes();
		chord5.playNotes();
		chord1.playNotes();
	}

}
