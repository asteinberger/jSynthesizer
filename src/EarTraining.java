/**
 * An application for training your ears to hear musical intervals.
 * GNU General Public License v3, February 2011.
 * @author Adam Steinberger
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

public class EarTraining extends JFrame {

	private static final long serialVersionUID = 8816685150457621036L;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu options = new JMenu("Options");
	private ButtonGroup playType;
	private JRadioButtonMenuItem melodicUp = new JRadioButtonMenuItem("Melodic Up");
	private JRadioButtonMenuItem melodicDown = new JRadioButtonMenuItem("Melodic Down");
	private JRadioButtonMenuItem harmonic = new JRadioButtonMenuItem("Harmonic");
	private JCheckBoxMenuItem interval01 = new JCheckBoxMenuItem("Minor 2nd");
	private JCheckBoxMenuItem interval02 = new JCheckBoxMenuItem("Major 2nd");
	private JCheckBoxMenuItem interval03 = new JCheckBoxMenuItem("Minor 3rd");
	private JCheckBoxMenuItem interval04 = new JCheckBoxMenuItem("Major 3rd");
	private JCheckBoxMenuItem interval05 = new JCheckBoxMenuItem("Perfect 4th");
	private JCheckBoxMenuItem interval06 = new JCheckBoxMenuItem("Tritone");
	private JCheckBoxMenuItem interval07 = new JCheckBoxMenuItem("Perfect 5th");
	private JCheckBoxMenuItem interval08 = new JCheckBoxMenuItem("Minor 6th");
	private JCheckBoxMenuItem interval09 = new JCheckBoxMenuItem("Major 6th");
	private JCheckBoxMenuItem interval10 = new JCheckBoxMenuItem("Minor 7th");
	private JCheckBoxMenuItem interval11 = new JCheckBoxMenuItem("Major 7th");
	private JPanel panel = new JPanel();
	private JLabel label = new JLabel("Guess the Interval!", JLabel.CENTER);
	private JButton play = new JButton("New Chord");
	private JButton repeat = new JButton("Repeat Chord");
	private String[] intervals = {"unison", "minor 2nd", "major 2nd", "minor 3rd",
			"major 3rd", "perfect 4th", "tritone", "perfect 5th", "minor 6th",
			"major 6th", "minor 7th", "major 7th", "octave"};
	private JComboBox comboInts = new JComboBox(this.intervals);
	private static HashMap<String,Boolean> playInts = new HashMap<String,Boolean>();
	private Chord chord;
	private int type = 0;
	
	public EarTraining(String title) {
		
		this.chord = new Chord(100.0,1.0);
		this.setTitle(title);
		this.playType  = new ButtonGroup();
		this.melodicUp.setSelected(true);
		this.melodicUp.setMnemonic(KeyEvent.VK_U);
		this.playType.add(this.melodicUp);
		this.options.add(this.melodicUp);
		this.melodicDown.setSelected(false);
		this.melodicDown.setMnemonic(KeyEvent.VK_D);
		this.playType.add(this.melodicDown);
		this.options.add(this.melodicDown);
		this.harmonic.setSelected(false);
		this.harmonic.setMnemonic(KeyEvent.VK_H);
		this.playType.add(this.harmonic);
		this.options.add(this.harmonic);
		this.options.addSeparator();
		
		CheckBoxListener listenCheck = new CheckBoxListener();
		this.interval01.setSelected(true);
		this.interval02.setSelected(true);
		this.interval03.setSelected(true);
		this.interval04.setSelected(true);
		this.interval05.setSelected(true);
		this.interval06.setSelected(true);
		this.interval07.setSelected(true);
		this.interval08.setSelected(true);
		this.interval09.setSelected(true);
		this.interval10.setSelected(true);
		this.interval11.setSelected(true);
		playInts.put("unison", true);
		playInts.put("minor 2nd", true);
		playInts.put("major 2nd", true);
		playInts.put("minor 3rd", true);
		playInts.put("major 3rd", true);
		playInts.put("perfect 4th", true);
		playInts.put("tritone", true);
		playInts.put("perfect 5th", true);
		playInts.put("minor 6th", true);
		playInts.put("major 6th", true);
		playInts.put("minor 7th", true);
		playInts.put("major 7th", true);
		playInts.put("octave", true);
		this.interval01.addItemListener(listenCheck);
		this.interval02.addItemListener(listenCheck);
		this.interval03.addItemListener(listenCheck);
		this.interval04.addItemListener(listenCheck);
		this.interval05.addItemListener(listenCheck);
		this.interval06.addItemListener(listenCheck);
		this.interval07.addItemListener(listenCheck);
		this.interval08.addItemListener(listenCheck);
		this.interval09.addItemListener(listenCheck);
		this.interval10.addItemListener(listenCheck);
		this.interval11.addItemListener(listenCheck);
		this.options.add(this.interval01);
		this.options.add(this.interval02);
		this.options.add(this.interval03);
		this.options.add(this.interval04);
		this.options.add(this.interval05);
		this.options.add(this.interval06);
		this.options.add(this.interval07);
		this.options.add(this.interval08);
		this.options.add(this.interval09);
		this.options.add(this.interval10);
		this.options.add(this.interval11);
		
		RadioListener listenRadio = new RadioListener();
		this.melodicUp.setActionCommand("melodic up");
		this.melodicDown.setActionCommand("melodic down");
		this.harmonic.setActionCommand("harmonic");
		this.melodicUp.addActionListener(listenRadio);
		this.melodicDown.addActionListener(listenRadio);
		this.harmonic.addActionListener(listenRadio);
		
		this.menuBar.add(this.options);
		this.options.setMnemonic(KeyEvent.VK_O);
		this.setJMenuBar(this.menuBar);
		
		this.add(this.panel, BorderLayout.CENTER);
		this.panel.add(this.label);
		this.panel.add(this.comboInts);
		this.panel.add(this.play);
		this.panel.add(this.repeat);
		
		this.setSize(300,100);
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
			
			if (random[i-1]+13 < midiNum.length-12) {
				ceiling = random[i-1]+13;
			} else {
				ceiling = midiNum.length-12;
			} // end if
			
			random[i] = generator.nextInt(ceiling-random[i-1])+random[i-1];
			note[i] = midiToNote.get(random[i]);
			
			Chord test = new Chord(note,100.0,1.0);
			String testInt = test.intervals("medium").get(0);
			
			if (playInts.get(testInt)) {
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
	
	private class RadioListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String t = e.getActionCommand();
			if (t.equals("melodic up")) {
				type = 0;
			} else if (t.equals("melodic down")) {
				type = 1;
			} else {
				type = 2;
			} // end if
		} // end actionPerformed()
	} // end RadioListener class
	
	private class CheckBoxListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			Object source = e.getItemSelectable();
			boolean isSelected;
			if (source == interval01) {
				isSelected = playInts.get("minor 2nd");
				playInts.put("minor 2nd", !isSelected);
			} else if (source == interval02) {
				isSelected = playInts.get("major 2nd");
				playInts.put("major 2nd", !isSelected);
			} else if (source == interval03) {
				isSelected = playInts.get("minor 3rd");
				playInts.put("minor 3rd", !isSelected);
			} else if (source == interval04) {
				isSelected = playInts.get("major 3rd");
				playInts.put("major 3rd", !isSelected);
			} else if (source == interval05) {
				isSelected = playInts.get("perfect 4th");
				playInts.put("perfect 4th", !isSelected);
			} else if (source == interval06) {
				isSelected = playInts.get("tritone");
				playInts.put("tritone", !isSelected);
			} else if (source == interval07) {
				isSelected = playInts.get("perfect 5th");
				playInts.put("perfect 5th", !isSelected);
			} else if (source == interval08) {
				isSelected = playInts.get("minor 6th");
				playInts.put("minor 6th", !isSelected);
			} else if (source == interval09) {
				isSelected = playInts.get("major 6th");
				playInts.put("major 6th", !isSelected);
			} else if (source == interval10) {
				isSelected = playInts.get("minor 7th");
				playInts.put("minor 7th", !isSelected);
			} else if (source == interval11) {
				isSelected = playInts.get("major 7th");
				playInts.put("major 7th", !isSelected);
			} // end if
		} // end itemStateChanged()
	} // end CheckBoxListener class
	
	private class PlayListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			chord.clear();
			label.setText("Guess the Interval!");
			HashMap<Integer,String> midiToNote = chord.getTwelveTone().getMidiToNote();
			int[] midiNum = chord.getTwelveTone().getMidiNum();
			chord = randChord(chord,midiNum,midiToNote);
			if (type == 0) {
				chord.playMelodic(true);
			} else if (type == 1) {
				chord.playMelodic(false);
			} else {
				chord.playNotes();
			} // end if
		} // end actionPerformed()
		
	} // end PlayListener class
	
	private class RepeatListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if (type == 0) {
				chord.playMelodic(true);
			} else if (type == 1) {
				chord.playMelodic(false);
			} else {
				chord.playNotes();
			} // end if
		} // end actionPerformed()
		
	} // end RepeatListener class

} // end EarTraining class
