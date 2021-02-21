/*
 * SD2x Homework #8
 * This class represents the Data Tier in the three-tier architecture.
 * Implement the appropriate methods for this tier below.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.util.Set;
import java.util.HashSet;

public class DataTier {
	
	private String fileName; // the name of the file to read
	
	public DataTier(String inputSource) {
		fileName = inputSource;
	}

    private boolean isSanitized(String str){
        if(str == null) return false;
        if(str.isEmpty()) return false;
        char start = str.charAt(0);
        char end = str.charAt(str.length() - 1);
        return end != '"' && end != '#' && start != '"'; 
    }

    private String sanitize(String str){
        if(str.isEmpty()) return "";
        String sanitized = str;
        char start = str.charAt(0);
        char end = str.charAt(str.length() - 1);
        if(start == '"') sanitized = str.substring(1,str.length());
        if(end == '#' || end == '"') sanitized = str.substring(0,str.length() - 1);
        if(!isSanitized(sanitized)) sanitized = sanitize(sanitized);
        return sanitized;

    }

    private Book parseBook(String line){
        String[] items = line.split("\t");
        return new Book(sanitize(items[0]), sanitize(items[1]), Integer.valueOf(items[2])); 
    }

    public Set<Book> getAllBooks(){
        Path file = Paths.get(fileName);
        Set<Book> books = new HashSet<Book>();
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if(line.isEmpty()) continue;
                books.add(parseBook(line));
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        return books;
    }

}
