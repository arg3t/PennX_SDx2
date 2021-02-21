import java.util.Queue;
import java.util.Stack;

/*
 * SD2x Homework #2
 * Implement the method below according to the specification in the assignment description.
 * Please be sure not to change the method signature!
 */

public class HtmlValidator {
	public static Stack<HtmlTag> isValidHtml(Queue<HtmlTag> tags) {
        Stack<HtmlTag> matchedTags = new Stack<HtmlTag>();
        while(!tags.isEmpty()){
            HtmlTag tag = tags.remove();
            if(tag.isSelfClosing()){
                continue;
            }
            if(tag.isOpenTag()){
               matchedTags.push(tag); 
            }else{
                if(matchedTags.isEmpty()){ return null; }
                if(tag.matches(matchedTags.peek())){
                    matchedTags.pop();
                }else{
                    if( matchedTags.isEmpty() ){ return null; }
                    break;
                }
            }
        }
        return matchedTags;
	}

}

