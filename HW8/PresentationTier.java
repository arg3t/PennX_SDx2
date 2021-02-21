/*
 * SD2x Homework #8
 * This class represents the Presentation Tier in the three-tier architecture.
 * Implement the appropriate methods for this tier below. 
 * Also implement the start method as described in the assignment description.
 */

import java.util.Scanner;
import java.util.List;

public class PresentationTier {

	private LogicTier logicTier; // link to the Logic Tier
	private final Scanner input;

	public PresentationTier(LogicTier logicTier) {
        this.input = new Scanner(System.in);
		this.logicTier = logicTier;
	}
	
	public void start() {
        String cmd = "";
        while(!cmd.equals("exit")){
            System.out.print(">>> ");
            cmd = input.nextLine();
            switch(cmd){
                case "author":
                    showBookTitlesByAuthor();
                    break;
                case "year":
                    showNumberOfBooksInYear();
                    break;
                default:
                    System.out.println("Command not found!");
            }

        }
	}

    private void showBookTitlesByAuthor(){
        System.out.print("Author Name: ");
        String author = input.nextLine();
        List<String> titles = logicTier.findBookTitlesByAuthor(author);
        for(String t:titles)
            System.out.println("- " + t);
    }

    private void showNumberOfBooksInYear(){

    }
	

}
