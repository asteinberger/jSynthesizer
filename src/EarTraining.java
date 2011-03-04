/** EarTraining.java
 * Created by Adam Steinberger
 * GNU General Public License v3, February 2011
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class EarTraining extends JFrame {

	private static final long serialVersionUID = 8816685150457621036L;
	private JPanel panel = new JPanel();
	private JLabel label = new JLabel("Guess the Interval!", JLabel.CENTER);
	private JButton play = new JButton("New Chord");
	private JButton repeat = new JButton("Repeat");
	private String[] intervals = {"unison", "minor 2nd", "major 2nd", "minor 3rd",
			"major 3rd", "perfect 4th", "tritone", "perfect 5th", "minor 6th",
			"major 6th", "minor 7th", "major 7th", "octave"};
//	private String[] types = {"melodic", "harmonic"};
	private JComboBox comboInts = new JComboBox(this.intervals);
//	private JComboBox comboTypes = new JComboBox(this.types);
	private Chord chord;
	
	public EarTraining(String title) {
		
		this.chord = new Chord(60.0,1.0);
		
		this.setTitle(title);
		this.add(this.panel, BorderLayout.CENTER);
		this.panel.add(this.label);
		this.panel.add(this.label);
		this.panel.add(this.comboInts);
//		this.panel.add(this.comboTypes);
		this.panel.add(this.play);
		this.panel.add(this.repeat);
		
		this.setSize(300,90);
		this.comboInts.setSelectedIndex(0);
		PlayListener listenPlay = new PlayListener();
		this.play.addActionListener(listenPlay);
		RepeatListener listenRepeat = new RepeatListener();
		this.repeat.addActionListener(listenRepeat);
		ComboListener listenCombo = new ComboListener();
		this.comboInts.addActionListener(listenCombo);
		
		this.panel.setLayout(new GridLayout(2,2));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
	} // end EarTraining constructor
	
	public static void main(String[] args) {
		new EarTraining("Ear Training");
	} // end main()
	
	private static Chord randChord(Chord chord, int[] midiNum, HashMap<Integer, String> midiToNote) {
		
		Chord result = chord;
		Random generator = new Random();
		int[] random = new int [2];
		String[] note = new String [2];
		
		int ceiling = midiNum.length-25;
		int floor = 60;
		
		random[0] = generator.nextInt(ceiling-floor)+floor;
		note[0] = midiToNote.get(random[0]);
		result.addNote(note[0]);
		
		for (int i = 1; i < 2; i++) {
			
			if (random[i-1]+12 < midiNum.length-13) {
				ceiling = random[i-1]+12;
			} else {
				ceiling = midiNum.length-13;
			} // end if
			
			random[i] = generator.nextInt(ceiling-random[i-1])+random[i-1];
			note[i] = midiToNote.get(random[i]);
			
			if (!note[i].equals(note[i-1])) {
				result.addNote(note[i]);
			} else {
				i--;
			} // end if
			
		} // end for
		
		return result;
		
	} // end randChord()
	
	private class ComboListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox combo = (JComboBox) e.getSource();
			String interval = (String) combo.getSelectedItem();
			if (interval.equals(chord.intervals("medium").get(0))) {
				label.setText("Nice job!!!");
			} else {
				label.setText("Try again...");
			} // end if
		} // end actionPerformed()
	} // end ComboListener class
	
	private class PlayListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			chord.clear();
			label.setText("Guess the Interval!");
			HashMap<Integer,String> midiToNote = chord.getTwelveTone().getMidiToNote();
			int[] midiNum = chord.getTwelveTone().getMidiNum();
			chord = randChord(chord,midiNum,midiToNote);
			chord.playMelodic();
		} // end actionPerformed()
		
	} // end PlayListener class
	
	private class RepeatListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			chord.playMelodic();
		} // end actionPerformed()
		
	} // end RepeatListener class

} // end EarTraining class
