import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
* @author Gabe Marcial
* @author Jordan Ho
* CS 357
* Coding Project
* Converting DFA to Regular Expression.
 */
public class main {
    // instance variables
    private String formalDescription;
    private String states;
    private String alphabet;
    private String caseDiagram;
    private String startingState;
    private String acceptingStates;

    public static void main(String args[]) throws FileNotFoundException {
        System.out.println("Hello World");
        File myFile = new File("C:\\Users\\go4ma\\IdeaProjects\\cs357_coding_project\\src\\dfa.txt");
        dfa myDFA = new dfa(myFile);
        /*For debugging purposes*/
        System.out.println("Set of states:");
        for(String state : myDFA.getStates()){
            System.out.println(state);
        }
        System.out.println("Alphabet:");
        for(String letter : myDFA.getAlphabet()){
            System.out.println(letter);
        }
        System.out.println("Start state: " + myDFA.getStart());
        System.out.println("Delta table");
        System.out.println("Accept States:");
        for(String acceptState : myDFA.getAcceptStates()){
            System.out.println(acceptState);
        }

    }

    // reads the input file of the formal description and sorts data
    public String readFile(String formalDescription){
        return formalDescription;
    }
}