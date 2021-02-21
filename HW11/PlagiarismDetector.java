

import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/*
 * SD2x Homework #11
 * Improve the efficiency of the code below according to the guidelines in the assignment description.
 * Please be sure not to change the signature of the detectPlagiarism method!
 * However, you may modify the signatures of any of the other methods as needed.
 */

public class PlagiarismDetector {

	public static Map<String, Integer> detectPlagiarism(String dirName, int windowSize, int threshold) {
		File dirFile = new File(dirName);
		String[] files = dirFile.list();
		Map<String, Set<String>> filePhrases= new HashMap<String, Set<String>>();

		Map<String, Integer> numberOfMatches = new HashMap<String, Integer>();

		for(int i = 0; i < files.length; i++){
			filePhrases.put(files[i], createPhrases(dirName + "/" + files[i], windowSize));
		}

		for (int i = 0; i < files.length; i++) {
			String file1 = files[i];
			Set<String> file1Phrases = filePhrases.get(file1); 

			for (int j = i; j < files.length; j++) { 
				String file2 = files[j];
				if (numberOfMatches.containsKey(file2 + "-" + file1) || file1.equals(file2)) {
					continue;
				}

				Set<String> file2Phrases = filePhrases.get(file2); 

				if (file1Phrases == null || file2Phrases == null)
					return null;

				int matches = findMatches(file1Phrases, file2Phrases);

				if (matches > threshold) {
					String key = file1 + "-" + file2;
					numberOfMatches.put(key,matches);	
				}
			}

		}

		return sortResults(numberOfMatches);
	}

	
	/*
	 * This method reads the given file and then converts it into a Collection of Strings.
	 * It does not include punctuation and converts all words in the file to uppercase.
	 */
	protected static List<String> readFile(String filename) {
		if (filename == null) return null;
		
		List<String> words = new LinkedList<String>();
		
		try {
			Scanner in = new Scanner(new File(filename));
			while (in.hasNext()) {
				words.add(in.next().replaceAll("[^a-zA-Z]", "").toUpperCase());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return words;
	}

	
	/*
	 * This method reads a file and converts it into a Set/List of distinct phrases,
	 * each of size "window". The Strings in each phrase are whitespace-separated.
	 */
	protected static Set<String> createPhrases(String filename, int window) {
		if (filename == null || window < 1) return null;
				
		List<String> words = readFile(filename);
		
		Set<String> phrases = new HashSet<String>();
		
		for (int i = 0; i < words.size() - window + 1; i++) {
			String phrase = "";
			for (int j = 0; j < window; j++) {
				phrase += words.get(i+j) + " ";
			}

			phrases.add(phrase);
		}
		
		return phrases;		
	}

	/*
	 * Returns a Set of Strings that occur in both of the Set parameters.
	 * However, the comparison is case-insensitive.
	 */
	protected static int findMatches(Set<String> myPhrases, Set<String> yourPhrases) {

		int matches = 0;
		Set<String> smallText = myPhrases.size() < yourPhrases.size() ? myPhrases : yourPhrases;
		Set<String> bigText = myPhrases.size() > yourPhrases.size() ? myPhrases : yourPhrases;
		String[] smallTextOrdered = smallText.toArray(new String[smallText.size()]);
		String[] bigTextOrdered = bigText.toArray(new String[bigText.size()]);
		if (myPhrases != null && yourPhrases != null) {

			for (int i = 0; i < smallText.size(); i++) {
				for (int j = 0; j < bigText.size(); j++) {
					if (smallTextOrdered[i].equalsIgnoreCase(bigTextOrdered[j])) {
						matches++;
					}
				}
			}
		}
		return matches;
	}
	
	/*
	 * Returns a LinkedHashMap in which the elements of the Map parameter
	 * are sorted according to the value of the Integer, in non-ascending order.
	 */
	protected static LinkedHashMap<String, Integer> sortResults(Map<String, Integer> possibleMatches) {
		
		// Because this approach modifies the Map as a side effect of printing 
		// the results, it is necessary to make a copy of the original Map
		Map<String, Integer> copy = new HashMap<String, Integer>();

		for (String key : possibleMatches.keySet()) {
			copy.put(key, possibleMatches.get(key));
		}	
		
		LinkedHashMap<String, Integer> list = new LinkedHashMap<String, Integer>();

		for (int i = 0; i < copy.size(); i++) {
			int maxValue = 0;
			String maxKey = null;
			for (String key : copy.keySet()) {
				if (copy.get(key) > maxValue) {
					maxValue = copy.get(key);
					maxKey = key;
				}
			}
			
			list.put(maxKey, maxValue);
			
			copy.put(maxKey, -1);
		}

		return list;
	}
	
	/*
	 * This method is here to help you measure the execution time and get the output of the program.
	 * You do not need to consider it for improving the efficiency of the detectPlagiarism method.
	 */
    public static void main(String[] args) {
    	if (args.length == 0) {
    		System.out.println("Please specify the name of the directory containing the corpus.");
    		System.exit(0);
    	}
    	String directory = args[0];
    	long start = System.currentTimeMillis();
    	Map<String, Integer> map = PlagiarismDetector.detectPlagiarism(directory, 4, 5);
    	long end = System.currentTimeMillis();
    	double timeInSeconds = (end - start) / (double)1000;
    	System.out.println("Execution time (wall clock): " + timeInSeconds + " seconds");
    	Set<Map.Entry<String, Integer>> entries = map.entrySet();
    	for (Map.Entry<String, Integer> entry : entries) {
    		System.out.println(entry.getKey() + ": " + entry.getValue());
    	}
    }

}
