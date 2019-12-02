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
    // instance variables
    private String formalDescription;
    private String states;
    private String alphabet;
    private String caseDiagram;
    private String startingState;
    private String acceptingStates;

    public static void main(String args[]) throws FileNotFoundException {
        System.out.println("Hello World");
        File myFile;
        myFile = new File("out/production/coding_project/dfa.txt");
        dfa myDFA = new dfa(myFile);
        myDFA.errorChecking();
        /*For debugging purposes
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
        for(String transition : myDFA.getStoreDeltaFromFile()){
            System.out.println(transition);
        }
        System.out.println("Accept States:");
        for(String acceptState : myDFA.getAcceptStates()){
            System.out.println(acceptState);
        }
         */
        myDFA.processDelta();
        /* checking array list sizes.
        int numberOfstates = myDFA.getStates().size();
        int alphabetSize = myDFA.getAlphabet().size();
        System.out.println("Number of states: " + numberOfstates);
        System.out.println("Number of letters in alphabelt " + alphabetSize);
        */
        /*
        //testing delta 2d array
        String[][] my2dArray = myDFA.getDelta();
        int state;
        for(state = 0; state < myDFA.getStates().size(); state++){
            for(int letter = 0; letter< myDFA.getAlphabet().size(); letter++){
                System.out.println(myDFA.getDelta()[state][letter]);
            }
        }
         */
        myDFA.processStateTable();
        String nun = myDFA.transformDfaToRegex();
        int a = 0;
        /**
        System.out.println(myDFA.getStateTable()[0][0]);
        System.out.println(myDFA.getStateTable()[0][1]);
        System.out.println(myDFA.getStateTable()[0][2]);
        System.out.println(myDFA.getStateTable()[0][3]);
        System.out.println(myDFA.getStateTable()[0][4]);
        System.out.println(myDFA.getStateTable()[0][5]);
        System.out.println(myDFA.getStateTable()[0][6]);
        System.out.println(myDFA.getStateTable()[1][0]);
        System.out.println(myDFA.getStateTable()[1][1]);
        System.out.println(myDFA.getStateTable()[1][2]);
        System.out.println(myDFA.getStateTable()[2][0]);
        System.out.println(myDFA.getStateTable()[2][3]);
        System.out.println(myDFA.getStateTable()[2][5]);
        System.out.println(myDFA.getStateTable()[3][1]);
        System.out.println(myDFA.getStateTable()[3][4]);
        System.out.println(myDFA.getStateTable()[4][4]);
        System.out.println(myDFA.getStateTable()[4][6]);
        System.out.println(myDFA.getStateTable()[5][0]);
        System.out.println(myDFA.getStateTable()[5][1]);
         **/

        //System.out.println("!!!!!!!!!!!!");
        //for(int i = 0; i<myDFA.getStates().size()+2; i++){
          //  for(int j = 0; j<myDFA.getStates().size()+2; j++){
                //System.out.println(myDFA.getStateTable()[i][j]);
            //}
        //}
        //String[][] tempTable = new String[7][7];
        //tempTable = myDFA.ripState(myDFA.getStateTable());
       //String regex = myDFA.transformDfaToRegex();
        System.out.println("\n\n\n");
        for(int i = 0; i<myDFA.getStates().size()+2; i++){
            for(int j = 0; j<myDFA.getStates().size()+2; j++){
                System.out.println(myDFA.getStateTable()[i][j]);
            }
        }
        nun = myDFA.cleanRegex(nun);
        System.out.println("Regular Expression: "+ nun);
        //now truncate everythiing after e.
        /**
        System.out.println("`~~~~~```~~~~~~~~~`");
       System.out.println(myDFA.getStateTable()[0][0]);
       System.out.println(myDFA.getStateTable()[0][1]);
       System.out.println(myDFA.getStateTable()[1][2]);
       System.out.println(myDFA.getStateTable()[1][4]);
        System.out.println(myDFA.getStateTable()[2][0]);
        System.out.println(myDFA.getStateTable()[2][3]);
        System.out.println(myDFA.getStateTable()[3][3]);
        System.out.println(myDFA.getStateTable()[3][5]);
        System.out.println(myDFA.getStateTable()[4][0]);
        **/
    }
}