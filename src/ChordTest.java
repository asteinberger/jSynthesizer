/**
 * Test the functionality of the Chord class.
 * GNU General Public License v3, February 2011.
 * @author Adam Steinberger
 */

import java.util.HashMap;
import java.util.Random;

public class ChordTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Chord chord = new Chord(100.0,1.0);
		HashMap<Integer,String> midiToNote = chord.getTwelveTone().getMidiToNote();
		int[] midiNum = chord.getTwelveTone().getMidiNum();
		for (int i = 0; i < 100; i++) {
//		while (true) {
			chord = randChord(chord,midiNum,midiToNote);
			System.out.println(chord);
//			System.out.println(chord.distances());
			System.out.println(chord.intervals() + "\n");
//			System.out.println(chord.chords());
			chord.playNotes();
			chord.clear();
		}
	}
	
	private static Chord randChord(Chord chord, int[] midiNum, HashMap<Integer, String> midiToNote) {
		Chord result = chord;
		Random generator = new Random();
		int ceiling = midiNum.length-13;
		int size = generator.nextInt(6)+2;
		int[] random = new int [size];
		String[] note = new String [size];
//		System.out.println(size);
		random[0] = generator.nextInt(ceiling-24)+24;
		note[0] = midiToNote.get(random[0]);
		result.addNote(note[0]);
//		System.out.println("random[0] = " + random[0] + ", ceiling = " + ceiling);
		for (int i = 1; i < size; i++) {
			if (random[i-1]+12 < midiNum.length-13) {
				ceiling = random[i-1]+12;
			} else {
				ceiling = midiNum.length-13;
			}
			random[i] = generator.nextInt(ceiling-random[i-1])+random[i-1];
//			System.out.println("random[" + i + "] = " + random[i] + ", ceiling = " + ceiling);
			note[i] = midiToNote.get(random[i]);
			if (!note[i].equals(note[i-1])) {
				result.addNote(note[i]);
			}
//			} else {
//				i--;
//			}
		}
		return result;
	}

}
