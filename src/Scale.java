/** Scale.java
 * Created by Adam Steinberger
 * GNU General Public License v3, February 2011
 */

import java.util.*;

public class Scale {
	
	private Synthesizer synthesizer;
	private ArrayList<Double> freq = new ArrayList<Double>();
	private double tones = 12.0;
	private double start = 27.5;
	private double octaves = 7.0;
	
	public Scale() {
		this.synthesizer = new Synthesizer(100.0, 1.0, "sine");
		this.tones = 12.0;
		this.start = 27.5;
		this.octaves = 7.0;
		double ratio = Math.pow(2.0, 1.0/tones);
		this.freq.add(start);
		for (int i = 0; i < octaves*tones; i++) {
			double freq2 = this.freq.get(i)*ratio;
			freq.add(freq2);
		} // end for
	} // end Scale() constructor
	
	public Scale(double tones,
			double start, double octaves, double bpm, double beats) {
		this.synthesizer = new Synthesizer(bpm, beats, "sine");
		this.tones = tones;
		this.start = start;
		this.octaves = octaves;
		double ratio = Math.pow(2.0, 1.0/tones);
		this.freq.add(start);
		for (int i = 0; i < octaves*tones; i++) {
//			System.out.println("Scale: " + i);
			double freq2 = this.freq.get(i)*ratio;
			freq.add(freq2);
		} // end for
	} // end Scale() constructor
	
	public Synthesizer getSynth() {
		return synthesizer;
	} // end getSynth()
	
	public void setSynth(Synthesizer synthesizer) {
		this.synthesizer = synthesizer;
	} // end setSynth()
	
	public ArrayList<Double> getFreq() {
		return freq;
	} // end getFreq()
	
	public void setFreq(ArrayList<Double> freq) {
		this.freq = freq;
	} // end setFreq()
	
	public double getTones() {
		return tones;
	} // end getTones()
	
	public void setTones(double tones) {
		this.tones = tones;
	} // end setTones()
	
	public double getStart() {
		return start;
	} // end getStart()
	
	public void setStart(double start) {
		this.start = start;
	} // end setStart()
	
	public double getOctaves() {
		return octaves;
	} // end getOctaves()
	
	public void setOctaves(double octaves) {
		this.octaves = octaves;
	} // end setOctaves()
	
	public void playNotes(int chord[]) {
		double[] freqs = new double [chord.length];
		for (int index = 0; index < chord.length; index++) {
			freqs[index] = this.freq.get(chord[index]);
		} // end for
		this.synthesizer.tones(freqs);
		this.synthesizer.play();
	} // end playNotes()
	
	@Override
	public String toString() {
		return "Scale [freq=" + freq + "]";
	}

	public void loopNotes() {
		for (int index = 0; index < this.freq.size(); index++) {
			int[] chord = new int [1];
			chord[0] = index;
			playNotes(chord);
		} // end for
	} // end playNotes()
	
} // end Scale class
