/** Key.java
 * Created by Adam Steinberger
 * GNU General Public License v3, February 2011
 */

public class Key {

	private TwelveTone twelveTone;
	private Scale scale;
	private Synthesizer synthesizer;
	private String tonic;
	private String type;
	private int[] distances;
	private int[] major = {0, 2, 4, 5, 7, 9, 11, 12, 14, 16, 17, 19, 21, 23, 24};
	private int[] minor = {0, 2, 3, 5, 7, 8, 10, 12, 14, 15, 17, 18, 20, 23, 24};
	private int[] hMinor = {0, 2, 3, 5, 7, 8, 11, 12, 14, 15, 17, 18, 21, 23, 24};
	private int[] mMinor = {0, 2, 3, 5, 7, 9, 11, 12, 14, 15, 17, 19, 21, 23, 24};
	
	public Key(String tonic, String type, double bpm, double beats) {
		this.twelveTone = new TwelveTone(bpm,beats);
		this.scale = this.twelveTone.getScale();
		this.synthesizer = this.scale.getSynth();
		this.tonic = tonic;
		this.type = type;
		if (this.type.equals("major")) {
			this.distances = this.major;
		} else if (this.type.equals("minor")) {
			this.distances = this.minor;
		} else if (this.type.equals("harmonic minor")) {
			this.distances = this.hMinor;
		} else if (this.type.equals("melodic minor")) {
			this.distances = this.mMinor;
		}
	}

	public String getTonic() {
		return tonic;
	}

	public void setTonic(String tonic) {
		this.tonic = tonic;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int[] getDistances() {
		return distances;
	}

	public void setDistances(int[] distances) {
		this.distances = distances;
	}

	public TwelveTone getTwelveTone() {
		return twelveTone;
	}

	public Scale getScale() {
		return scale;
	}

	public Synthesizer getSynth() {
		return synthesizer;
	}
	
	public Chord newChord() {
		Chord chord = new Chord(this.synthesizer.getBpm(), this.synthesizer.getBeats());
		chord.clear();
		return chord;
	}
	
	public Chord getNote(int degree, int octave) {
		Chord result = newChord();
		int midiNum = this.twelveTone.getNoteToMidi().get(this.tonic + octave);
		String note = this.twelveTone.getMidiToNote().get(midiNum+this.distances[degree-1]);
		result.addNote(note);
		return result;
	}
	
	public Chord triad(int degree, int octave) {
		Chord result = newChord();
		int midiNum = this.twelveTone.getNoteToMidi().get(this.tonic + octave);
		String note1 = this.twelveTone.getMidiToNote().get(midiNum+this.distances[degree-1]);
		String note2 = this.twelveTone.getMidiToNote().get(midiNum+this.distances[degree+1]);
		String note3 = this.twelveTone.getMidiToNote().get(midiNum+this.distances[degree+3]);
		result.addNote(note1);
		result.addNote(note2);
		result.addNote(note3);
		return result;
	}
	
	public void scaleUp(int octave) {
		Chord chord = newChord();
		int midiNum = this.twelveTone.getNoteToMidi().get(this.tonic + octave);
		for (int i = 0; i < 8; i++) {
			chord.clear();
			String note = this.twelveTone.getMidiToNote().get(midiNum+this.distances[i]);
			chord.addNote(note);
			chord.playNotes();
		}
	}
	
	public void scaleDown(int octave) {
		Chord chord = newChord();
		int midiNum = this.twelveTone.getNoteToMidi().get(this.tonic + octave);
		for (int i = 7; i >= 0; i--) {
			chord.clear();
			String note = this.twelveTone.getMidiToNote().get(midiNum+this.distances[i]);
			chord.addNote(note);
			chord.playNotes();
		}
	}
	
}
