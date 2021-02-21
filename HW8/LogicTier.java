/*
 * SD2x Homework #8
 * This class represents the Logic Tier in the three-tier architecture.
 * Implement the appropriate methods for this tier below.
 */
import java.util.List;
import java.util.LinkedList;

import java.util.Set;

public class LogicTier {
	
	private DataTier dataTier; // link to the Data Tier
	
	public LogicTier(DataTier dataTier) {
		this.dataTier = dataTier;
	}
    private boolean includes(String author, String search){
        for(int i=0; i <= author.length() - search.length(); i++){
            if(author.substring(i, i + search.length()).equals(search)) return true;
        }
        return false;
    }	

    public List<String> findBookTitlesByAuthor(String author){
        Set<Book> books = dataTier.getAllBooks();
        List<String> matchingTitles = new LinkedList<String>();
        for(Book b : books){
            if(includes(b.getAuthor(), author)) matchingTitles.add(b.getTitle());
        }
        return matchingTitles;
    }

    public long findNumberOfBooksInYear(int year){
        int bookNum = 0;
        Set<Book> books = dataTier.getAllBooks();
        for(Book b : books){
            if(b.getPublicationYear() == year) bookNum += 1;
        }
        return bookNum;
    }

}
