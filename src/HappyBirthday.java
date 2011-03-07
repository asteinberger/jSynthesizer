


public class HappyBirthday {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// set key and notes
		Key key = new Key("G", "major", 120.0, 1.0);
		String[] notes0 = {key.note(5,3).getNotes().get(0)};
		String[] notes1 = {key.note(1,2).getNotes().get(0),
				key.note(5,2).getNotes().get(0),
				key.note(3,3).getNotes().get(0),
				key.note(6,3).getNotes().get(0)};
		String[] notes2 = {key.note(1,2).getNotes().get(0),
				key.note(5,2).getNotes().get(0),
				key.note(5,3).getNotes().get(0)};
		String[] notes3 = {key.note(5,2).getNotes().get(0),
				key.note(3,3).getNotes().get(0),
				key.note(1,4).getNotes().get(0)};
		String[] notes4 = {key.note(2,2).getNotes().get(0),
				key.note(5,2).getNotes().get(0),
				key.note(4,3).getNotes().get(0),
				key.note(7,3).getNotes().get(0)};
		String[] notes5 = {key.note(5,2).getNotes().get(0),
				key.note(5,3).getNotes().get(0)};
		String[] notes6 = {key.note(5,1).getNotes().get(0),
				key.note(5,2).getNotes().get(0),
				key.note(4,3).getNotes().get(0),
				key.note(6,3).getNotes().get(0)};
		String[] notes7 = {key.note(5,1).getNotes().get(0),
				key.note(5,2).getNotes().get(0),
				key.note(5,3).getNotes().get(0)};
		String[] notes8 = {key.note(5,1).getNotes().get(0),
				key.note(5,2).getNotes().get(0),
				key.note(4,3).getNotes().get(0),
				key.note(2,4).getNotes().get(0)};
		String[] notes9 = {key.note(1,2).getNotes().get(0),
				key.note(5,2).getNotes().get(0),
				key.note(3,3).getNotes().get(0),
				key.note(1,4).getNotes().get(0)};
		String[] notes10 = {key.note(1,2).getNotes().get(0),
				key.note(1,3).getNotes().get(0),
				key.note(3,3).getNotes().get(0),
				key.note(5,3).getNotes().get(0)};
		String[] notes11 = {key.note(3,2).getNotes().get(0),
				key.note(1,3).getNotes().get(0),
				key.note(5,3).getNotes().get(0),
				key.note(1,4).getNotes().get(0),
				key.note(5,4).getNotes().get(0)};
		String[] notes12 = {key.note(3,2).getNotes().get(0),
				key.note(1,3).getNotes().get(0),
				key.note(5,3).getNotes().get(0),
				key.note(1,4).getNotes().get(0),
				key.note(3,4).getNotes().get(0)};
		String[] notes13 = {key.note(3,2).getNotes().get(0),
				key.note(1,3).getNotes().get(0),
				key.note(5,3).getNotes().get(0),
				key.note(1,4).getNotes().get(0)};
		String[] notes14 = {key.note(4,2).getNotes().get(0),
				key.note(1,3).getNotes().get(0),
				key.note(5,3).getNotes().get(0),
				key.note(7,3).getNotes().get(0)};
		String[] notes15 = {key.note(4,2).getNotes().get(0),
				key.note(1,3).getNotes().get(0),
				key.note(4,3).getNotes().get(0),
				key.note(6,3).getNotes().get(0)};
		String[] notes16 = {key.note(2,2).getNotes().get(0),
				key.note(6,2).getNotes().get(0),
				key.note(2,3).getNotes().get(0),
				key.note(4,3).getNotes().get(0),
				key.note(4,4).getNotes().get(0)};
		String[] notes17 = {key.note(5,2).getNotes().get(0),
				key.note(5,3).getNotes().get(0),
				key.note(3,4).getNotes().get(0)};
		String[] notes18 = {key.note(5,2).getNotes().get(0),
				key.note(5,3).getNotes().get(0),
				key.note(1,4).getNotes().get(0)};
		String[] notes19 = {key.note(5,2).getNotes().get(0),
				key.note(7,2).getNotes().get(0),
				key.note(4,3).getNotes().get(0),
				key.note(2,4).getNotes().get(0)};
		String[] notes20 = {key.note(1,2).getNotes().get(0),
				key.note(5,2).getNotes().get(0),
				key.note(1,3).getNotes().get(0),
				key.note(3,3).getNotes().get(0),
				key.note(1,4).getNotes().get(0)};
		
		// line 1
		key.newChord(notes0).playNotes(1.0);
		key.newChord(notes1).playNotes(1.0);
		key.newChord(notes2).playNotes(1.0);
		key.newChord(notes3).playNotes(1.0);
		key.newChord(notes4).playNotes(2.0);
		
		// line 2
		
		key.newChord(notes5).playNotes(1.0);
		key.newChord(notes6).playNotes(1.0);
		key.newChord(notes7).playNotes(1.0);
		key.newChord(notes8).playNotes(1.0);
		key.newChord(notes9).playNotes(2.0);
		
		// line 3
		key.newChord(notes10).playNotes(1.0);
		key.newChord(notes11).playNotes(1.0);
		key.newChord(notes12).playNotes(1.0);
		key.newChord(notes13).playNotes(1.0);
		key.newChord(notes14).playNotes(1.0);
		key.newChord(notes15).playNotes(1.0);
		
		// line 4
		key.newChord(notes16).playNotes(1.0);
		key.newChord(notes17).playNotes(1.0);
		key.newChord(notes18).playNotes(1.0);
		key.newChord(notes19).playNotes(1.0);
		key.newChord(notes20).playNotes(2.0);
		
	}

}
