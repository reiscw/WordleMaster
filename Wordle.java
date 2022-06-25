// Note that the words.txt file used by this program is the version used by the original Wordle game
// (as of January 31st, 2022. I am not aware of the licensing of this file, due to it being available widely on the internet
// in a variety of public repositories. If there are concerns with the licensing of this file, please feel free to 
// contact me.

import java.util.*;
import java.io.*;

public class Wordle {
	
	private char[] solution = {' ', ' ', ' ', ' ', ' '};
	private ArrayList<Character> candidates;
	private ArrayList<Character> nonCandidates;
	private ArrayList<String> words;
	private String[] specificExclusions;
	
	public Wordle() throws FileNotFoundException {
		candidates = new ArrayList<Character>();
		nonCandidates = new ArrayList<Character>();
		specificExclusions = new String[5];
		for (int i = 0; i < 5; i++) {
			specificExclusions[i] = "";
		}
		
		// load list of 5-letter words
		Scanner in = new Scanner(new File("words.txt"));
		words = new ArrayList<String>();
		while (in.hasNext()) {
			String word = in.next();
			if (word.length() == 5) {
				words.add(word);
			}
		}
	}
	
	public void addCandidate(char letter) {
		candidates.add(letter);
	}
	
	public void addNonCandidate(char letter) {
		nonCandidates.add(letter);
	}
	
	public void setSolution(int position, char letter) {
		solution[position] = letter;
	}
	
	public void addSpecificExclusions(int position, String letters) {
		specificExclusions[position] = letters.toLowerCase();
	}
	
	public ArrayList<String> getPossibleWords() {
		ArrayList<String> possibles = new ArrayList<String>();
		for (String word : words) {
			boolean done = false;
			// check solution
			for (int i = 0; !done && i < 5; i++) {
				if (solution[i] != ' ' && solution[i] != word.charAt(i)) {
					done = true;
				}
			}
			// check candidates
			for (int i = 0; !done && i < candidates.size(); i++) {
				if (!word.contains("" + candidates.get(i))) {
					done = true;
				}
			}
			// check non-candidates
			for (int i = 0; !done && i < nonCandidates.size(); i++) {
				if (word.contains("" + nonCandidates.get(i))) {
					done = true;
				}
			}
			
			// check specific exclusions
			for (int i = 0; !done && i < 5; i++) {
				if (!specificExclusions[i].equals("")) {
					char[] letters = specificExclusions[i].toCharArray();
					for (int j = 0; !done && j < letters.length; j++) {
						if (word.charAt(i) == letters[j]) {
							done = true;
						}
					}
				}
			}
			
			if (!done) {
				possibles.add(word);
			}
		}
		return possibles;
	}
}
