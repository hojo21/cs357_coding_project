
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class dfa {
    //Initialize start variables.
    private ArrayList<String> states;
    private ArrayList<String> alphabet;
    private String[][] delta;
    private String start;
    private ArrayList<String> acceptStates;

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

        /*Processing information from the file*/
        Scanner sc = new Scanner(dfa);
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            if(line.contentEquals("Q:")){
                //System.out.print("Set of states.\n");
                while(sc.hasNextLine()){
                    String state = sc.nextLine();
                    if(state.contentEquals("end")){
                        break;
                    }
                    else states.add(state);
                }
            }
            else if(line.contentEquals("Sigma:")){
                //System.out.print("Sigma\n");
                while(sc.hasNextLine()){
                    String letter = sc.nextLine();
                    if(letter.contentEquals("end")){
                        break;
                    }
                    else alphabet.add(letter);
                }
            }
            else if(line.contentEquals("Delta:")){
                //System.out.print("Delta\n");
            }
            else if(line.contentEquals("Start:")){
                //System.out.print("start state is \n");
                while(sc.hasNextLine()){
                    String startState = sc.nextLine();
                    if(startState.contentEquals("end")){
                        break;
                    }
                    else{
                        start = startState;
                    }
                }
            }
            else if(line.contentEquals("F:")){
                //System.out.print("Accept States are\n");
                while(sc.hasNextLine()){
                    String acceptState = sc.nextLine();
                    if(acceptState.contentEquals("end")){
                        break;
                    }
                    else{
                        acceptStates.add(acceptState);
                    }
                }
            }
        }
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

}
