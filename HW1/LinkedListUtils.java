

import java.util.LinkedList;
import java.util.ArrayList;

/*
 * SD2x Homework #1
 * Implement the methods below according to the specification in the assignment description.
 * Please be sure not to change the signature of any of the methods!
 */

public class LinkedListUtils {
	public static void insertSorted(LinkedList<Integer> list, int value) {

        if( list == null ) { return; } // Terminate if the list is null
        if(list.size() == 0){
            list.add(value);
            return;
        }

        int pointer = 0;

        for(int i : list){
            if( i > value ){ 
                break;
            } 
            pointer++;
        }
        if(pointer == list.size()){
            list.addLast(value);
        }else{
            list.add(pointer, value);
        }
    }
	
    public static int calcMinimum(ArrayList<String> list){ // Return the index of the minimum element on the Array
        int i, j ;
        for(i = 0; i < list.size(); i++){
            for(j = 0; j < list.size(); j++){
                if(j == i){
                    continue;
                }
                if(list.get(i).compareTo(list.get(j)) > 0){
                    break;
                }
            }
            if( j == list.size()){
                return i; 
            }
        }
        return -1;
    }

	public static void removeMaximumValues(LinkedList<String> list, int N) {
        
        if( list == null ){ return; }
        if( N < 1 ) { return; } 
        if( list.size() <= N ){ list.clear(); return; }

        ArrayList<String> maxElements = new ArrayList<String>(N); 

        for(int i = 0; i < N; i++){
            maxElements.add(list.get(i));
        } 

        int maxVal, diff;
        String str;

        for(int i = N; i < list.size(); i++){
            maxVal = 0;
            str = list.get(i);
            for(int j = 0; j < N; j++){
                diff = str.compareTo(maxElements.get(j));
                if(diff == 0){ // Break if string collision is detected
                    maxVal = 0;
                    break;
                }
                maxVal = diff > maxVal ? diff : maxVal; 
            }
            if(maxVal > 0){
                maxElements.set(calcMinimum(maxElements), str);
            }
        }
        list.removeAll(maxElements); // Remove all occurences of the elements in array from LL
    }
	
	public static boolean containsSubsequence(LinkedList<Integer> one, LinkedList<Integer> two) {
        if( one == null || two == null ){ return false; }
        if( one.size() < two.size() || one.size() == 0 || two.size() == 0){ return false; }
        int j;
        for(int i = 0; i <= one.size() - two.size(); i++){
            if(one.get(i) != two.getFirst()){
                continue;
            }           
            for(j = 1; j < two.size(); j++){
                if(one.get(i + j) != two.get(j)){
                    break;
                }
            }
            if(j == two.size()){
                return true;
            }
        }
        return false;
	}
}
