import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.LinkedHashSet;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.lang.NumberFormatException;

/*
 * SD2x Homework #3
 * Implement the methods below according to the specification in the assignment description.
 * Please be sure not to change the method signatures!
 */
public class Analyzer {
	
	/*
	 * Implement this method in Part 1
	 */

    public static Sentence parseLine(String line){

        int negative = 1;
        if(line.length() > 0){
            if(line.charAt(0) == '-'){
                negative = -1;
                line = line.substring(1);
            }
        }
        if(line.length() < 3){
            return null;
        }

        int rating;
        try{
            rating = Integer.parseInt(line.substring(0,1));
        }catch(NumberFormatException e){
            return null;
        }
        if(-2 > rating || 2 < rating || line.charAt(1) != ' '){
            return null;
        }

        return new Sentence(rating * negative, line.substring(2));

    }

	public static List<Sentence> readFile(String filename) {
        List<Sentence> sentences = new LinkedList<Sentence>();
        if( filename == null ){ return sentences; }

        List<String> lines;
        try{
            lines = Files.readAllLines(Paths.get(filename));
        }catch(IOException e){
            return sentences;
        }
        for(String line : lines){
            Sentence sentence = parseLine(line);
            if(sentence == null){
                continue;
            }
            sentences.add(sentence);
        }
        return sentences;
	}
	public static Set<Word> allWords(List<Sentence> sentences) {
        Set<Word> words = new LinkedHashSet<Word>(); 

        if(sentences == null){ return words; }
        if(sentences.size() == 0){ return words; }

        for(Sentence s : sentences){
            if(s == null){
                continue;
            }
            String[] elements = s.getText().toLowerCase().split(" ");
            for(int i = 0; i < elements.length; i++){
                if(elements[i] == null){
                    continue;
                }
                if(!Character.isLetter(elements[i].charAt(0))){
                    continue;
                }
                Word word = new Word(elements[i]);
                for(int j = i + 1; j < elements.length; j++){
                    if(elements[j] == null){
                        continue;
                    }
                    if(elements[i].equals(elements[j])){
                        word.count++;
                        elements[j] = null; // Set matching elements to null, should increase performance slightly
                    }
                }
                words.add(word);
            }
        }
        return words;
	}
	
	/*
	 * Implement this method in Part 3
	 */
	public static Map<String, Double> calculateScores(Set<Word> words) {
        
        Map<String, Double> scores = new HashMap<String, Double>();
        if(words == null){
            return scores; 
        }
        if(words.isEmpty()){
            return scores;
        }
        for(Word w : words){
            if( w == null ){
                continue;
            }
            scores.put(w.getText(),w.calculateScore());
        }
        return scores;
	}
	
	/*
	 * Implement this method in Part 4
	 */
	public static double calculateSentenceScore(Map<String, Double> wordScores, String sentence) {
        double total = 0;
        int invalidWords = 0;

        if(wordScores == null || sentence == null){
            return total;
        }
        if(wordScores.isEmpty() || sentence.isEmpty()){
            return total;
        }

        String[] elements = sentence.toLowerCase().split(" ");
        for(int i = 0; i < elements.length; i++){
            if(!Character.isLetter(elements[i].charAt(0))){
                invalidWords++;
                continue;
            }
            if(wordScores.containsKey(elements[i])){
                total += wordScores.get(elements[i]);
            }
            
        }
        if(elements.length - invalidWords == 0){ // In case the whole sentence is invalid
            return 0;
        }
		return total / (elements.length - invalidWords); // this line is here only so this code will compile if you don't modify it

	}
	
	/*
	 * This method is here to help you run your program. Y
	 * You may modify it as needed.
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Please specify the name of the input file");
			System.exit(0);
		}
		String filename = args[0];
		System.out.print("Please enter a sentence: ");
		Scanner in = new Scanner(System.in);
		String sentence = in.nextLine();
		in.close();
		List<Sentence> sentences = Analyzer.readFile(filename);
		Set<Word> words = Analyzer.allWords(sentences);
		Map<String, Double> wordScores = Analyzer.calculateScores(words);
		double score = Analyzer.calculateSentenceScore(wordScores, sentence);
		System.out.println("The sentiment score is " + score);
	}
}
