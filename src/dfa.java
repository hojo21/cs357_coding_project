
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Gabe Marcial
 * @author Jordan Ho
 * dfa class
 **/
public class dfa {
    //Initialize start variables.
    private ArrayList<String> states;
    private ArrayList<String> alphabet;
    private String[][] delta;
    private String start;
    private ArrayList<String> acceptStates;
    private ArrayList<String> storeDeltaFromFile;
    private String[][] stateTable;

    //Default constructor
    public dfa(){
    }
    //Parameter constructor constructor
    public dfa(ArrayList<String> setOfStates, ArrayList<Character> sigma, String[][] transTable, String startState,
               ArrayList<String> setOfAccept){
        states = setOfStates;

    }
    //file constructor
    public dfa(File dfa) throws FileNotFoundException {
        /*Allocating instance variables*/
        states = new ArrayList<String>();
        alphabet = new ArrayList<String>();
        acceptStates = new ArrayList<String>();
        storeDeltaFromFile = new ArrayList<String>();

        /*Processing information from the file*/
        Scanner sc = new Scanner(dfa);
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            if(line.contentEquals("Q:")){
                //System.out.print("Set of states.\n");
                while(sc.hasNextLine()){
                    String state = sc.nextLine();
                    if(state.contentEquals("end")) break;
                    else states.add(state);
                }
            }
            else if(line.contentEquals("Sigma:")){
                //System.out.print("Sigma\n");
                while(sc.hasNextLine()){
                    String letter = sc.nextLine();
                    if(letter.contentEquals("end")) break;
                    else alphabet.add(letter);
                }
            }
            else if(line.contentEquals("Delta:")){
                //System.out.print("Delta\n");
                while(sc.hasNextLine()){
                    String transition = sc.nextLine();
                    if(transition.contentEquals("end")) break;
                    else storeDeltaFromFile.add(transition);
                }
            }
            else if(line.contentEquals("Start:")){
                //System.out.print("start state is \n");
                while(sc.hasNextLine()){
                    String startState = sc.nextLine();
                    if(startState.contentEquals("end")) break;
                    else start = startState;
                }
            }
            else if(line.contentEquals("F:")){
                //System.out.print("Accept States are\n");
                while(sc.hasNextLine()){
                    String acceptState = sc.nextLine();
                    if(acceptState.contentEquals("end")) break;
                    else acceptStates.add(acceptState);
                }
            }
        }
    }

    /*Function definitions*/
    //processDelta
    //We know if its an odd row or even row because of the format we are using
    //in the text file.
    void processDelta(){
        int state = 0;
        int letter;
        delta = new String[this.getStates().size()][this.getAlphabet().size()];
        for(String transition : this.getStoreDeltaFromFile()){
            //we are odd row in table
            if(transition.contains("a")){
                letter = 0;
                delta[state][letter] = transition.substring(transition.lastIndexOf("a,") + 3);
            }
            //even row on table
            else if(transition.contains("b")){
                letter  = 1;
                delta[state][letter] = transition.substring(transition.lastIndexOf("b,") + 3);
                state += 1;  //increment the state
                //we only increment state here because it changes every other line.
            }
        }
    }

    //this will be for the transformation algorithm.
    void processStateTable(){
        int numStates = this.getStates().size();
        int alphabetSize = this.getAlphabet().size();
        stateTable = new String[numStates+2][numStates+2];

        for(String transition : this.getStoreDeltaFromFile()){
            String[] line;
            line = transition.split(", ");
            String stateFrom = line[0].substring(1);
            int stateF = Integer.parseInt(stateFrom);
            String letter = line[1];
            String stateTo = line[2].substring(1);
            int stateT = Integer.parseInt(stateTo);

            //check if we need a union
            if(stateF == stateT && stateTable[stateF][stateT] != null){
                stateTable[stateF][stateT] += "U(" + letter + ")";
            }
            //check if start state
            //no need for for loop since there is only one accept state, but must still
            //do the normal table filling. THus, the nested if-else.
            else if(line[2].contentEquals(this.getStart())){
                stateTable[numStates][stateT] = "e";
                if(stateF == stateT && stateTable[stateF][stateT] != null){
                    stateTable[stateF][stateT] += "U(" + letter + ")";
                }
                else {
                    stateTable[stateF][stateT] = "(" + letter + ")";
                }
            }
            else {
                stateTable[stateF][stateT] = "(" + letter + ")";
            }

            //check if accept state.
            //need to use for loop since it can be a collection.
            for(String accept : this.getAcceptStates()){
                if(line[0].contentEquals(accept)){
                    stateTable[stateF][numStates+1] = "e";
                }
            }
            //System.out.println(stateFrom + " " + stateF + " " +  stateTo + " " + stateT);
        }

        //for debugging.
        /*
        for(int i = 0; i<numStates+2; i++){
            for(int j = 0; j<numStates+2; j++){
                System.out.println(" " + stateTable[i][j]);
            }
        }
         */
    }

    /*Our algorithm*/
    public String transformDfaToRegex() {
        //create a state table.
        processStateTable();
        int stateTableSize = this.getStates().size() + 2;
        ArrayList<String> from = new ArrayList<String>();
        int stateToRip = 0;
        int statesLeft = this.getStates().size();
        int numStates = this.getStates().size();
        String regex = "";

        while (statesLeft != 0) {
            for (int i = 0; i < stateTableSize; i++) {
                for (int j = 0; j < stateTableSize; j++) {
                    //checking first box
                    //since i and j are equal it means that a state
                    //goes to themselves thus we must concatenate the content*
                    if(i == 0 && j == 0){
                        if(this.getStateTable()[i][j] != null){
                            regex = regex + this.getStateTable()[i][j] + "*";
                        }
                    }
                    //checking the first row
                    else if(i == 0 && j !=0){
                        if(this.getStateTable()[i][j] != null){
                            regex = regex + this.getStateTable()[i][j];
                        }
                    }
                    //checking the first column
                    else if(j == 0 && i != 0){
                        if(this.getStateTable()[i][j] != null) {
                            regex = regex + this.getStateTable()[i][j];
                        }
                    }
                    else{
                        //just breaking the inner loop.
                        break;
                    }


                    //check the row f'
                    /*
                    if(i == numStates-1){
                        if(this.getStateTable()[i][j] != null){
                            //add to string
                            regex = regex.concat(this.getStateTable()[i][j]);
                        }
                    }
                    else if(j == numStates-1){
                        if(this.getStateTable()[i][j] != null){
                            regex = regex.concat(this.getStateTable()[i][j]);
                        }
                    }
                    else if(i == numStates-1 && j == numStates){
                        if(this.getStateTable()[i][j] != null){
                            regex = regex.concat(this.getStateTable()[i][j] + "*");
                        }
                     */
                }
            }
            System.out.println(regex);
            //this.ripState();
            statesLeft -= 1;
        }
        //System.out.println("The regex: " + regex);
        return "hi";
    }
        //for now return this bullshit

    // rips state from 2d array data table by getting rid of the 0 row and column
    public String[][] ripState(String[][] stateTable){
        int stateTableSize = this.getStates().size() - 1;
        String[][] newTable = new String[stateTableSize][stateTableSize];
        String stateString = new String("");
        String newString = new String("");
        String unionString = new String("");

        // copies data from stateTable into newTable
        /**
        for(int i=0; i<stateTableSize; i++){
            for(int j=0; j<stateTableSize; j++){
                if(stateTable[i][j] != null){
                    newTable[i][j] = stateTable[i][j];
                }
                System.out.println(newTable[i][j]);
            }
        }
         **/

        for(int i=0; i<stateTableSize; i++){
            for(int j=0; j<stateTableSize; j++){
                // copying information from the larger state table into the small starting from the first row and column
                // only copies data into the table if the opening is null, meaning it does not have to union
                /**
                if(stateTable[i][j] != null){
                    newTable[i][j] = stateTable[i][j];
                }**/

                // copies data from stateTable row 1 into newTable row 1 if there is no necessary union
                if(stateTable[0][j] != null && stateTable[0][j+1] == null){
                    newTable[0][j] = stateTable[0][j];
                }
                // unions row 0 with row 1
                if(stateTable[0][j] != null && stateTable[1][j+1] != null){
                    stateString = stateTable[0][j];
                    newString = stateTable[1][j+1];
                    unionString = stateString + "U" + newString;
                    //System.out.println(stateString);
                    //System.out.println(newString);
                    //System.out.println(unionString);
                    newTable[0][j] = newString;
                }
                // creates star loop if transition goes back to the same state

                // copies data from the first column into the first column of the new table and unions if necessary
                if(i >= 1 && j == 0){
                    if(stateTable[i][0] != null && stateTable[i+1][0] == null){
                        newTable[i][0] = stateTable[i][0];
                    }
                    if(stateTable[i][0] != null && stateTable[i+1][0] != null){
                        stateString = stateTable[i][0];
                        newString = stateTable[i+1][0];
                        unionString = stateString + "U" + newString;
                        newTable[i][0] = unionString;
                    }
                }
                //System.out.println(newTable[i][j]);
            }
        }


        //TODO: fix this return statement
        return new String[0][];
    }

    /*Getters and Setters*/
    public ArrayList<String> getStates(){
        return this.states;
    }

    public ArrayList<String> getAlphabet(){
        return this.alphabet;
    }

    public String getStart(){
        return this.start;
    }

    public ArrayList<String> getAcceptStates(){
        return this.acceptStates;
    }

    public ArrayList<String> getStoreDeltaFromFile(){
        return this.storeDeltaFromFile;
    }

    public String[][] getDelta(){
        return this.delta;
    }

    public String[][] getStateTable(){
        return this.stateTable;
    }

}
