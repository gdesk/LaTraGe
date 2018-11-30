package lbsStructure;

import alice.tuprolog.*;
import prologConfiguration.PrologUtils;
import prologConfiguration.PrologUtilsImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Converter {

    List<String> inputList = new ArrayList<String>();
    PrologUtils prologUtils = new PrologUtilsImpl("src/main/prolog/LTSoperators.pl");

    public Converter() throws IOException, InvalidTheoryException {
    }

    public String inputConverter(final String input, String inputCompute) throws UnknownVarException, NoSolutionException, NoMoreSolutionException {

        String listPar = "";
        SolveInfo info = prologUtils.solveGoal("par2list(" + input + ",L).");
        if (info.isSuccess()) {
            listPar = info.getTerm("L").toString();
        }
        SolveInfo info1 = prologUtils.solveGoal("member(X," + listPar + "), dot2list(X,K).");

        if (info1.isSuccess()) {
            String process = info1.getTerm("K").toString();
            inputCompute = inputCompute.concat( process+ ",");
            inputList.add(process);

            while (prologUtils.getEngine().hasOpenAlternatives()) {
                SolveInfo info2 = prologUtils.getEngine().solveNext();
                if (info2.isSuccess()) {
                    String process2 = info2.getTerm("K").toString();
                    inputCompute = inputCompute.concat( process+ ",");
                    inputList.add(process);
                }
            }
        }
        inputCompute = inputCompute.substring(0, inputCompute.length() - 1).concat("]");
        return inputCompute;
    }

    public List<String> getinputList(){
     return inputList;
    }

    public String outputConverter(final String prologoutput) throws UnknownVarException, NoSolutionException, NoMoreSolutionException {
        String parSequence = "";
        String valueState="[";
        SolveInfo info1 = prologUtils.solveGoal("member(X," + prologoutput + "), list2dot(X,K).");
        if (info1.isSuccess()) {
            System.out.println("CONVERTER CLASS---------------------:" + info1.getSolution());
            valueState = valueState.concat(info1.getTerm("K").toString() + ",");

            while (prologUtils.getEngine().hasOpenAlternatives()) {
                SolveInfo info2 = prologUtils.getEngine().solveNext();
                if (info2.isSuccess()) {
                    valueState = valueState.concat(info1.getTerm("K").toString() + ",");
                }
            }
        }
        valueState = valueState.substring(0, valueState.length() - 1).concat("]");
        SolveInfo info = prologUtils.solveGoal("list2par(" + valueState + ",L).");
        if (info.isSuccess()) {
            parSequence = info.getTerm("L").toString();
            System.out.println("list2par  " + parSequence);
        }
        return parSequence;

    }
}
