
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class dfa {
    //Initialize start variables.
    private ArrayList<String> states;
    private ArrayList<String> alphabet;
    private String[][] delta;
    private String start;
    private ArrayList<String> acceptStates;
    private ArrayList<String> storeDeltaFromFile;

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
        String[][] stateTable = new String[numStates+2][numStates+2];

        for(String transition : this.getStoreDeltaFromFile()){
            String[] line;
            line = transition.split(", ");
            String stateFrom = line[0].substring(1);
            int stateF = Integer.parseInt(stateFrom);
            String letter = line[1];
            String stateTo = line[2].substring(1);
            int stateT = Integer.parseInt(stateTo);

            if(stateF == stateT && stateTable[stateF][stateT] != null){
                stateTable[stateF][stateT] += "U(" + letter + ")";
            }
            else {
                stateTable[stateF][stateT] = "(" + letter + ")";
            }
            //System.out.println(stateFrom + " " + stateF + " " +  stateTo + " " + stateT);
        }

        //for debugging.
        for(int i = 0; i<numStates; i++){
            for(int j = 0; j<numStates; j++){
                System.out.println(" " + stateTable[i][j]);
            }
        }
    }

    /*Our algorithm*/
    public String transformDfaToRegex(){
        //create a state table.
        this.processStateTable();
        //for now return this bullshit
        return "hi";
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
        return delta;
    }

}
