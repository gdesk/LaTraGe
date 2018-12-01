package lbsStructure;

import alice.tuprolog.*;
import prologConfiguration.PrologUtils;
import prologConfiguration.PrologUtilsImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Converter {

    private List<String> inputList = new ArrayList<String>();
    private List<String> tempInputList = new ArrayList<String>();
    private PrologUtils prologUtils = new PrologUtilsImpl("src/main/prolog/LTSoperators.pl");

    public Converter() throws IOException, InvalidTheoryException {
    }

    public String inputConverter(final String input, String inputCompute) throws UnknownVarException, NoSolutionException, NoMoreSolutionException {

        inputList.clear();
        tempInputList.clear();
        String listPar = "";
        System.out.println("INPUT    ---- "  +input);
        SolveInfo info = prologUtils.solveGoal("par2list(" + input + ",L).");
        if (info.isSuccess()) {
            listPar = info.getTerm("L").toString();
             System.out.println("LISTPAR    ---- "  +listPar);
        }
        SolveInfo info1 = prologUtils.solveGoal("member(X," + listPar + "), dot2list(X,K).");

        if (info1.isSuccess()) {
            String process = info1.getTerm("K").toString();
          //  System.out.println("CONVERTER    PROCESS---- "  +process);
            inputCompute = inputCompute.concat( process+ ",");

            inputList.add(process);
          // System.out.println("CONVERTER    ---- "  +inputList);

            while (prologUtils.getEngine().hasOpenAlternatives()) {
                SolveInfo info2 = prologUtils.getEngine().solveNext();
                if (info2.isSuccess()) {
                    String process2 = info2.getTerm("K").toString();
                 //   System.out.println("CONVERTER    PROCESS---- "  +process2);
                    inputCompute = inputCompute.concat( process2+ ",");

                    inputList.add(process2);
                  //  System.out.println("CONVERTER IN WHILE    ---- "  +inputList);
                }
            }
        }
        inputCompute = inputCompute.substring(0, inputCompute.length() - 1).concat("]");
        tempInputList.addAll(inputList);
      //  System.out.println("CONVERTER FUORI    ---- "  +inputList);
        return inputCompute;
    }

    public List<String> getinputList(){
     return inputList;
    }

    public String outputConverter(final String prologoutput) throws UnknownVarException, NoSolutionException, NoMoreSolutionException {
        String parSequence = "";
        String valueState="[";
       System.out.println("LISTA OUTPUT CONVERTER convertee***********1 - " + inputList);
        SolveInfo info1 = prologUtils.solveGoal("member(X," + prologoutput + "), list2dot(X,K).");
        if (info1.isSuccess()) {
            valueState = valueState.concat(info1.getTerm("K").toString() + ",");
           System.out.println("value state output convertee***********1 - " + valueState);
            while (prologUtils.getEngine().hasOpenAlternatives()) {
                SolveInfo info2 = prologUtils.getEngine().solveNext();
                if (info2.isSuccess()) {
               //     System.out.println(info2.getSolution().toString());
              //      System.out.println("value state output convertee***********1.1 - " + valueState);
                    valueState = valueState.concat(info2.getTerm("K").toString() + ",");
                }
            }
            System.out.println("\n\n\n");
        }

        if (!valueState.equals("[")) {
            valueState = valueState.substring(0, valueState.length() - 1).concat("]");
            SolveInfo info = prologUtils.solveGoal("list2par(" + valueState + ", L).");
            if (info.isSuccess()) {
                parSequence = info.getTerm("L").toString();
            }
        }

        return parSequence;
    }

    public void ReInit(int index){

        inputList.set(index, tempInputList.get(index));
    }


}
