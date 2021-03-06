/**
 * A musical chord for playing a series of notes.
 * GNU General Public License v3, February 2011.
 * @author Adam Steinberger
 */

import java.util.ArrayList;

public class Chord {
	
	private TwelveTone twelveTone;
	private Scale scale;
	private Synthesizer synthesizer;
	private ArrayList<String> notes = new ArrayList<String>();
	private String[] typeShort = {"P1", "m2", "M2", "m3", "M3", "P4", "TT", "P5",
			"m6", "M6", "m7", "M7", "P8"};
	private String[] typeMed = {"unison", "minor 2nd", "major 2nd", "minor 3rd",
			"major 3rd", "perfect 4th", "tritone", "perfect 5th", "minor 6th",
			"major 6th", "minor 7th", "major 7th", "octave"};
	private String[] typeLong = {"unison", "minor second", "major second", "minor third",
			"major third", "perfect fourth", "tritone", "perfect fifth", "minor sixth",
			"major sixth", "minor seventh", "major seventh", "octave"};
	
	public Chord(double bpm, double beats) {
		this.twelveTone = new TwelveTone(bpm,beats);
		this.scale = this.twelveTone.getScale();
		this.synthesizer = this.scale.getSynth();
	}
	
	public Chord(String[] notes, double bpm, double beats) {
		this.twelveTone = new TwelveTone(bpm,beats);
		this.scale = this.twelveTone.getScale();
		this.synthesizer = this.scale.getSynth();
		for (int i = 0; i < notes.length; i++) {
			addNote(notes[i]);
		}
	}

	public ArrayList<String> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<String> notes) {
		this.notes = notes;
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
	
	public void addNote(String note) {
		this.notes.add(note);
	}
	
	public boolean clear() {
		boolean result = false;
		this.notes = new ArrayList<String>();
		if (this.notes.isEmpty()) {
			result = true;
		}
		return result;
	}
	
	public ArrayList<Integer> distances() {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < notes.size()-1; i++) {
			int midi1 = this.twelveTone.getNoteToMidi().get(this.notes.get(i));
			int midi2 = this.twelveTone.getNoteToMidi().get(this.notes.get(i+1));
			result.add(Math.abs(midi2-midi1));
		}
		return result;
	}
	
	public ArrayList<String> intervals() {
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<Integer> distances = this.distances();
		String[] types = {"unison", "minor second", "major second", "minor third",
				"major third", "perfect fourth", "tritone", "perfect fifth", "minor sixth",
				"major sixth", "minor seventh", "major seventh", "octave"};
		for (int i = 0; i < distances.size(); i++) {
			int distance = distances.get(i);
			result.add(types[distance]);
		}
		return result;
	}
	
	public ArrayList<String> intervals(String type) {
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<Integer> distances = this.distances();
		String[] types;
		if (type.equals("short")) {
			types = this.typeShort;
		} else if (type.equals("medium")) {
			types = this.typeMed;
		} else {
			types = this.typeLong;
		} // end if
		for (int i = 0; i < distances.size(); i++) {
			int distance = distances.get(i);
			result.add(types[distance]);
		} // end for
		return result;
	} // end intervals()
	
	public boolean playNotes() {
		boolean result = false;
		if (!this.notes.isEmpty()) {
			result = true;
			double[] freqs = new double [this.notes.size()];
			for (int i = 0; i < this.notes.size(); i++) {
				freqs[i] = this.twelveTone.getNoteToFreq().get(this.notes.get(i));
			}
			this.synthesizer.tones(freqs);
//			System.out.println(Arrays.toString(freqs));
			System.out.println(this.notes);
			this.synthesizer.play();
		}
		return result;
	}
	
	public boolean playNotes(double beats) {
		boolean result = false;
		if (!this.notes.isEmpty()) {
			result = true;
			this.getTwelveTone().getScale().getSynth().setBeats(beats);
			double[] freqs = new double [this.notes.size()];
			for (int i = 0; i < this.notes.size(); i++) {
				freqs[i] = this.twelveTone.getNoteToFreq().get(this.notes.get(i));
			}
			this.synthesizer.tones(freqs);
//			System.out.println(Arrays.toString(freqs));
			System.out.println(this.notes);
			this.synthesizer.play();
		}
		return result;
	}

	public boolean playMelodic() {
		boolean result = false;
		if (!this.notes.isEmpty()) {
			result = true;
			double[] freqs = new double [1];
			System.out.println(this.notes);
			for (int i = 0; i < this.notes.size(); i++) {
				freqs[0] = this.twelveTone.getNoteToFreq().get(this.notes.get(i));
				this.synthesizer.tones(freqs);
				this.synthesizer.play();
			}
		}
		return result;
	}
	
	@Override
	public String toString() {
		return "" + notes;
	}
	
	public boolean playMelodic(boolean ascend) {
		boolean result = false;
		if (!this.notes.isEmpty()) {
			result = true;
			System.out.println(this.notes);
			if (ascend) {
				for (int i = 0; i < this.notes.size(); i++) {
					double freq = this.twelveTone.getNoteToFreq().get(this.notes.get(i));
					this.synthesizer.tone(freq);
					this.synthesizer.play();
				}
			} else {
				for (int i = this.notes.size()-1; i >= 0; i--) {
					double freq = this.twelveTone.getNoteToFreq().get(this.notes.get(i));
					this.synthesizer.tone(freq);
					this.synthesizer.play();
				}
			}
		}
		return result;
	}

}
