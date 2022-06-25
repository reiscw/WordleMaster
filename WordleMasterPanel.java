import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

public class WordleMasterPanel extends JPanel {
   
    private JComboBox[] solutions;
    private JToggleButton[] candidateButtons;
    private JToggleButton[] nonCandidateButtons;
	private JLabel solutionsLabel;
	private JLabel includedLettersLabel;
	private JLabel excludedLettersLabel;
	private JLabel wordCount;
	private JLabel wordCountLabel;
	private JLabel note;
	private JLabel candidateLabel;
    private JButton updateButton;
    private JButton resetButton;
    private JButton quitButton;
    private JTextArea wordList;
    private JLabel[] excludedByPositionLabels;
	private JTextField[] excludedByPositionFields;

    public WordleMasterPanel() {
	
		// construct dropdown boxes for solution

		solutions = new JComboBox[5];
		String[] choices = {"?", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
		for (int i = 0; i < solutions.length; i++) {
			solutions[i] = new JComboBox(choices);
			add(solutions[i]);
		}

		// construct labels

        solutionsLabel = new JLabel ("Solution");
        includedLettersLabel = new JLabel ("Included Letters");
        excludedLettersLabel = new JLabel ("Excluded Letters");
        wordCountLabel = new JLabel ("Current Word Count: ");
        wordCount = new JLabel ("12972");
        note = new JLabel("Note only the first 25 candidates are listed.");
        candidateLabel = new JLabel("Candidates:");
        
        add(solutionsLabel);
        add(includedLettersLabel);
        add(excludedLettersLabel);
        add(wordCountLabel);
        add(wordCount);
        add(note);
        add(candidateLabel);

        // construct candidateButtons
        
        String[] qwerty = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"};
        
        candidateButtons = new JToggleButton[26];
        for (int i = 0; i < 26; i++) {
			candidateButtons[i] = new JToggleButton(qwerty[i], false);
			add(candidateButtons[i]);
		}
        
        // construct nonCandidateButtons
        
        nonCandidateButtons = new JToggleButton[26];
        for (int i = 0; i < 26; i++) {
			nonCandidateButtons[i] = new JToggleButton(qwerty[i], false);
			add(nonCandidateButtons[i]);
		}
		
		// construct word list and update button
		updateButton = new JButton ("Update");
		resetButton = new JButton ("Reset");
		quitButton = new JButton ("Quit");
		wordList = new JTextArea (5, 5);
		add(updateButton);
		add(resetButton);
		add(quitButton);
        add(wordList);

		// construct excluded by position fields and labels
		excludedByPositionLabels = new JLabel[5];
		excludedByPositionLabels[0] = new JLabel("Exclude from 1st letter: ");
		excludedByPositionLabels[1] = new JLabel("Exclude from 2nd letter: ");
		excludedByPositionLabels[2] = new JLabel("Exclude from 3rd letter: ");
		excludedByPositionLabels[3] = new JLabel("Exclude from 4th letter: ");
		excludedByPositionLabels[4] = new JLabel("Exclude from 5th letter: ");
		excludedByPositionFields = new JTextField[5];
		for (int i = 0; i < 5; i++) {
			excludedByPositionFields[i] = new JTextField();
			add(excludedByPositionFields[i]);
			add(excludedByPositionLabels[i]);
		}
		
        // adjust size and set layout
        setPreferredSize(new Dimension (950, 570));
        setLayout(null);

        // set component bounds 
	
		// solutions
        for (int i = 0, col = 35; i < 5; i++, col = col + 65) {
			solutions[i].setBounds(col, 40, 60, 25);
		}

		// row 1 of candidate/noncandidate buttons
		for (int i = 0, col = 35; i < 10; col = col + 55, i++) {
			candidateButtons[i].setBounds(col, 110, 50, 25);
			nonCandidateButtons[i].setBounds(col, 250, 50, 25);
		}
		
		// row 2 of candidate/noncandidate buttons
		for (int i = 10, col = 35; i < 19; col = col + 55, i++) {
			candidateButtons[i].setBounds(col, 140, 50, 25);
			nonCandidateButtons[i].setBounds(col, 280, 50, 25);
		}
		
		// row 3 of candidate/noncandidate buttons
		for (int i = 19, col = 35; i < 26; col = col + 55, i++) {
			candidateButtons[i].setBounds(col, 170, 50, 25);
			nonCandidateButtons[i].setBounds(col, 310, 50, 25);
		}
		
		// labels and text fields
		solutionsLabel.setBounds(35, 10, 100, 25);
        includedLettersLabel.setBounds(35, 80, 150, 25);
        excludedLettersLabel.setBounds(35, 220, 150, 25);
        wordCountLabel.setBounds(635, 40, 160, 25);
        wordCount.setBounds(835, 40, 130, 25);
        note.setBounds(35, 500, 500, 25);
        updateButton.setBounds(360, 40, 80, 25);
        resetButton.setBounds(445, 40, 80, 25);
        quitButton.setBounds(530, 40, 80, 25);
        candidateLabel.setBounds(635, 80, 265, 25);
        wordList.setBounds(635, 110, 265, 410);
        
        for (int i = 0, row = 350; i < 5; i++, row = row + 30) {
			excludedByPositionLabels[i].setBounds(35, row, 200, 25);
			excludedByPositionFields[i].setBounds(225, row, 250, 25);
		}

		// action listeners for update, quit, and reset buttons
        
        updateButton.addActionListener(e -> {
			try {
				update();
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		});
		
		quitButton.addActionListener(e -> {
			try {
				System.exit(0);
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		});
		
		resetButton.addActionListener(e -> {
			try {
				reset(); update();
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		});
		
    }
    
    public void update() throws FileNotFoundException {
		Wordle puzzle = new Wordle();
		// add solutions
		for (int i = 0; i < 5; i++) {
			char letter = ((String)(solutions[i].getSelectedItem())).toLowerCase().charAt(0);
			if (letter != '?') {
				puzzle.setSolution(i, letter);
			}
		}
		// add candidates and noncandidates
		for (int i = 0; i < 26; i++) {
			if (candidateButtons[i].isSelected()) {
				char letter = candidateButtons[i].getText().toLowerCase().charAt(0);
				puzzle.addCandidate(letter);
			}
			if (nonCandidateButtons[i].isSelected()) {
				char letter = nonCandidateButtons[i].getText().toLowerCase().charAt(0);
				puzzle.addNonCandidate(letter);
			}
		}
		
		// add specific exclusions
		for (int i = 0; i < 5; i++) {
			String temp = excludedByPositionFields[i].getText();
			if (!temp.equals("")) {
				puzzle.addSpecificExclusions(i, temp);
			}
		}
		
		// get possible words
		ArrayList<String> possibles = puzzle.getPossibleWords();
		
		// set text display
		String text = "";
		for (int i = 0; i < possibles.size() && i < 25; i++) {
			text = text + possibles.get(i) + "\n";
		}
		wordList.setText(text);
		
		// update word count
		wordCount.setText("" + possibles.size());
		
	}
	
	public void reset() {
		for (int i = 0; i < 5; i++) {
			solutions[i].setSelectedIndex(0);
			excludedByPositionFields[i].setText("");
		}
		for (int i = 0; i < 26; i++) {
			candidateButtons[i].setSelected(false);
			nonCandidateButtons[i].setSelected(false);
		}
	}
}
