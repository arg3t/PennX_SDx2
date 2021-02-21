/*
 * SD2x Homework #5
 * Implement the method below according to the specification in the assignment description.
 * Please be sure not to change the method signature!
 */

import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class MovieRatingsParser {

	public static TreeMap<String, PriorityQueue<Integer>> parseMovieRatings(List<UserMovieRating> allUsersRatings) {
        TreeMap<String, PriorityQueue<Integer>> movieRatings = new TreeMap<String, PriorityQueue<Integer>>();
        if(allUsersRatings == null) return movieRatings;

        for(UserMovieRating m : allUsersRatings){

            if(m == null) continue;
            if(m.getMovie() == null) continue;
            if(m.getMovie().isEmpty()) continue;
            if(m.getUserRating() < 0) continue;
            if(!movieRatings.containsKey(m.getMovie().toLowerCase())){
                PriorityQueue<Integer> ratingHeap = new PriorityQueue<Integer>();
                ratingHeap.add(m.getUserRating());
                movieRatings.put(m.getMovie().toLowerCase(), ratingHeap);
                continue;
            }
            movieRatings.get(m.getMovie().toLowerCase()).add(m.getUserRating());

        }	
        return movieRatings;
	}

}
