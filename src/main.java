import java.io.File;
import java.io.FileNotFoundException;

/**
* @author Gabe Marcial
* @author Jordan Ho
* CS 357
* Coding Project
* Converting DFA to Regular Expression.
 **/

//TODO: "/Users/jordanho/Documents/cs357_coding_project/src/dfa.txt"
    //C:\Users\go4ma\IdeaProjects\cs357_coding_project\src\dfa.txt

public class main {
    /**
     * main method 
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String args[]) throws FileNotFoundException {
        File myFile;
        myFile = new File("out/production/coding_project/dfa.txt");
        dfa myDFA = new dfa(myFile);
        System.out.println("\n\n");
        myDFA.errorChecking();
        myDFA.processDelta();
        myDFA.processStateTable();
        String myRegularExpression = myDFA.transformDfaToRegex();
        myRegularExpression = myDFA.cleanRegex(myRegularExpression);
        System.out.println("Regular Expression: "+ myRegularExpression);
    }
}