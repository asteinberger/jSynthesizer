/** TwelveTone.java
 * Created by Adam Steinberger
 * GNU General Public License v3, February 2011
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class TwelveTone {
	
	private Scale scale;
	private Synthesizer synthesizer;
	private ArrayList<Double> freq;
	private int[] midiNum = new int [100];
	private HashMap<String,Double> noteToFreq = new HashMap<String,Double>();
	private HashMap<String,Integer> noteToMidi = new HashMap<String,Integer>();
	private HashMap<Integer,String> midiToNote = new HashMap<Integer,String>();
	private HashMap<Integer,Double> midiToFreq = new HashMap<Integer,Double>();
	final private String[] names = {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
	
	public TwelveTone(double bpm, double beats) {
//		System.out.println(Arrays.toString(this.names));
		this.scale = new Scale(12.0, 27.5, 7.0, bpm, beats);
		this.synthesizer = this.scale.getSynth();
		this.freq = this.scale.getFreq();
//		System.out.println(this.freq);
		int octave = 0;
		int tab = 21;
		for (int i = 0; i < this.freq.size(); i++) {
//			System.out.println("-----------");
			String name = "";
//			this.midiNum[i] = (int) (69.0 + 12.0*Math.log(this.freq.get(i)/400.0)/Math.log(2.0));
			this.midiNum[i] = i+21;
//			System.out.println("this.midiNum["+ i + "] = " + this.midiNum[i]);
//			System.out.println((this.midiNum[i]-4) % 12);
			if ((this.midiNum[i]-24) % 12 == 0) {
				octave++;
			}
			if ((this.midiNum[i]-tab) % 12 < 1) {
				name = this.names[0];
			} else if ((this.midiNum[i]-tab-1) % 12 < 1) {
				name = this.names[1];
			} else if ((this.midiNum[i]-tab-2) % 12 < 1) {
				name = this.names[2];
			} else if ((this.midiNum[i]-tab-3) % 12 < 1) {
				name = this.names[3];
			} else if ((this.midiNum[i]-tab-4) % 12 < 1) {
				name = this.names[4];
			} else if ((this.midiNum[i]-tab-5) % 12 < 1) {
				name = this.names[5];
			} else if ((this.midiNum[i]-tab-6) % 12 < 1) {
				name = this.names[6];
			} else if ((this.midiNum[i]-tab-7) % 12 < 1) {
				name = this.names[7];
			} else if ((this.midiNum[i]-tab-8) % 12 < 1) {
				name = this.names[8];
			} else if ((this.midiNum[i]-tab-9) % 12 < 1) {
				name = this.names[9];
			} else if ((this.midiNum[i]-tab-10) % 12 < 1) {
				name = this.names[10];
			} else if ((this.midiNum[i]-tab-11) % 12 < 1) {
				name = this.names[11];
			} // end if
			String note = name + octave;
//			System.out.println(note + " = freq.get(" + i + ") = " + this.freq.get(i));
            this.noteToFreq.put(note, this.freq.get(i));
            this.noteToMidi.put(note, midiNum[i]);
            this.midiToNote.put(midiNum[i], note);
            this.midiToFreq.put(midiNum[i], this.freq.get(i));
		} // end for
		
	} // end TwelveTone constructor

	@Override
	public String toString() {
		return "TwelveTone [freq=" + freq + ", midiNum="
				+ Arrays.toString(midiNum) + "]";
	}

	public Scale getScale() {
		return this.scale;
	} // end getScale()

	public void setScale(Scale scale) {
		this.scale = scale;
	} // end setScale()

	public Synthesizer getSynth() {
		return this.synthesizer;
	} // end getSynth()
	
	public void setSynth(Synthesizer synthesizer) {
		this.synthesizer = synthesizer;
	} // end setSynth()

	public ArrayList<Double> getFreq() {
		return this.freq;
	} // end getFreq()

	public void setFreq(ArrayList<Double> freq) {
		this.freq = freq;
	} // end setFreq()

	public int[] getMidiNum() {
		return this.midiNum;
	} // end getMidiNum()

	public HashMap<String, Double> getNoteToFreq() {
		return this.noteToFreq;
	} // end getNoteToFreq()

	public HashMap<String, Integer> getNoteToMidi() {
		return this.noteToMidi;
	} // end getNoteToMidi()

	public HashMap<Integer, String> getMidiToNote() {
		return this.midiToNote;
	} // end getMidiToNote()
	
	public HashMap<Integer, Double> getMidiToFreq() {
		return this.midiToFreq;
	} // end getMidiToFreq()

	public String[] getNames() {
		return names;
	} // end getNames()
	
	public void playNote(String note) {
		this.synthesizer.tone(this.noteToFreq.get(note));
		this.synthesizer.play();
	} // end playNote()
	
} // end TwelveTone class
