
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import trieADT.Trie;

/**
 *  LongestWord Calculate the 1st longest word, 2nd longest word and
 *  all words that can be made of other words.
 */

public class LongestWord {
	private static Trie trie = new Trie();
    
	/**
     *  readFile() Read words from a file and store in an array.
     *
     *  @param file the file which we can read words from.
     *  @param wordArray the array which we write words into.
     */
	// Read words from a file and store in an array
	public static void readFile(String file, List<String> wordArray) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line = null; 	
		while ((line = br.readLine()) != null) { 		
			wordArray.add(line);
        }   	
        br.close(); 
	}
    
	/**
     *  isRequiredWord() Check if the word can be constructed by concatenating
     *  copies of shorter words.
     *
     *  @param word the original word needed to be checked.
     *  @param hasword if the word should be deleted.
     *  @return true if a word can be made of other words.
     */
	public static boolean isRequiredWord(String word, boolean hasword) {
		/* Remove the word so that the word is not matched to itself to find the longest word */
		if (hasword) {
			trie.remove(word);
		}
		for(int i=0; i<word.length(); i++) {
			if(trie.search(word.substring(0, i+1))) {
				if(i+1==word.length() || isRequiredWord(word.substring(i+1, word.length()), false)) {
					return true;
				}
			}
		}
		if(hasword) {
			trie.insert(word);
		}
		return false;
	}
	
	/**
     *  LongestWords() Calculate the 1st longest word , 2nd longest word and
     *  all words that can be made of other words.
     *
     *  @param list the array of words that a file contains.
     *  @return an array of words that can be made of other words.
     */
    public static String[] LongestWords(String[] list) {
		List<String> wordList = new ArrayList<String>();
		
		for(String word : list) {
			if(isRequiredWord(word, true)) {
				wordList.add(word);
			}
		}
		return( (String[])wordList.toArray(new String[wordList.size()]) );
	}
	
    /**
     *  main() reads the command-line arguments.
     *  The first command-line argument is the name of the file.
     *
     *  @param args the usual array of command-line argument Strings.
     */
	public static void main(String[] args) throws IOException {		
		List<String> wordArray = new ArrayList<String>();
		String[] longestWords = null;
		String[] sortedWords = null;
		String file = "test.txt";
		
		if(args.length != 0) {
			file = args[0];
		}
		
		readFile(file, wordArray);
		/* Convert array list to an array of string */
		sortedWords = (String[])wordArray.toArray(new String[wordArray.size()]);
		/* Sort the words based on length */
		Arrays.sort(sortedWords, new StringLengthSort());
		/* Store words in Trie Tree */
		for(String word : sortedWords) {
			trie.insert(word);
		}
		longestWords = LongestWords(sortedWords);
		
		/* Print out the desired results */
		System.out.println("The 1st longest word made of other words:   " + longestWords[0]);
		System.out.println("The 2nd longest word made of other words:   " + longestWords[1]);
		System.out.println("Total number of words that can be made of other words :   " + longestWords.length);
	}
	
}
