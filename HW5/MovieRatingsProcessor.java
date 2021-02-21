/*
 * SD2x Homework #5
 * Implement the methods below according to the specification in the assignment description.
 * Please be sure not to change the method signatures!
 */

import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import java.util.LinkedList;

public class MovieRatingsProcessor {

	public static List<String> getAlphabeticalMovies(TreeMap<String, PriorityQueue<Integer>> movieRatings) {
        if(movieRatings == null) return new LinkedList<String>();
		return new LinkedList<String>(movieRatings.keySet()); // Normally sets aren't ordered but the docs say the returned set is ordered and it works sooo. \_(._.)_/ 
    }

	public static List<String> getAlphabeticalMoviesAboveRating(TreeMap<String, PriorityQueue<Integer>> movieRatings, int rating) {
        List<String> movies = new LinkedList<String>();
        if(movieRatings == null) return movies;
        Set<Entry<String, PriorityQueue<Integer>>> entries = movieRatings.entrySet();

        for(Entry<String, PriorityQueue<Integer>> e : entries){
           if(e.getValue().peek() > rating) movies.add(e.getKey()); 
        }
        return movies;
	}
	
    public static TreeMap<String, Integer> removeAllRatingsBelow(TreeMap<String, PriorityQueue<Integer>> movieRatings, int rating) {

        TreeMap<String, Integer> removed = new TreeMap<String, Integer>();
        if(movieRatings == null)  return removed; 
        int count;
        PriorityQueue<Integer> value;
        LinkedList<String> deleted = new LinkedList<String>();
        Integer[] foo = new Integer[1];
        for(String n : movieRatings.keySet()){
            value = movieRatings.get(n);
            if(value.peek() >= rating){
                continue;
            }
            Integer[] ratings = value.toArray(foo);
            count = 0;
            for(int i = 0; i < ratings.length; i++){
                if(ratings[i] < rating){
                    value.remove(ratings[i]);
                    count++;
                }             
            }
            removed.put(n, count);
            if(count == ratings.length){
                deleted.add(n);
            }
        }       
        for(String n : deleted)
            movieRatings.remove(n);
        return removed;

    }
}
