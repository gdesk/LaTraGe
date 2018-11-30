package lbsStructure;

import alice.tuprolog.NoMoreSolutionException;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.UnknownVarException;
import prologConfiguration.PrologUtils;

public class Converter {

    static String inputConverter(final String input, String inputCompute, PrologUtils prologUtils) throws UnknownVarException, NoSolutionException, NoMoreSolutionException {
        SolveInfo info = prologUtils.solveGoal("par2list(" + input + ",L).");
        String listPar = "";

        if (info.isSuccess()) {
            listPar = info.getTerm("L").toString();
            System.out.println("par2list  " + listPar);
        }
        SolveInfo info1 = prologUtils.solveGoal("member(X," + listPar + "), list2dot(X,K).");
        if (info1.isSuccess()) {
            inputCompute = inputCompute.concat(info1.getTerm("K").toString() + ",");

            while (prologUtils.getEngine().hasOpenAlternatives()) {
                SolveInfo info2 = prologUtils.getEngine().solveNext();
                if (info2.isSuccess()) {
                    inputCompute = inputCompute.concat(info1.getTerm("K").toString() + ",");
                }
            }
        }
        inputCompute = inputCompute.substring(0, inputCompute.length() - 1).concat("]");
        System.out.println("INPUT COMPUTE" + inputCompute);
        return inputCompute;
    }

    public void outputConverter(){

    }
}
