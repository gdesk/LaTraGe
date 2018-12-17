package lbsStructure;

import alice.tuprolog.*;
import prologConfiguration.PrologUtils;
import prologConfiguration.PrologUtilsImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConverterImpl implements Converter {

    private static String FILE_NAME = "src/main/prolog/LTSoperators.pl";

    private List<String> inputList = new ArrayList<String>();
    private List<String> tempInputList = new ArrayList<String>();
    private PrologUtils prologUtils = new PrologUtilsImpl(FILE_NAME);

    public ConverterImpl() throws IOException, InvalidTheoryException {
    }

    @Override
    public String inputConverter(final String input, String inputCompute)
                throws UnknownVarException, NoSolutionException, NoMoreSolutionException {
        inputList.clear();
        tempInputList.clear();

        String listPar = "";
        SolveInfo par2listInfo = prologUtils.solveGoal("par2list(" + input + ",L).");
        if (par2listInfo.isSuccess()) {
            listPar = par2listInfo.getTerm("L").toString();
        }
        SolveInfo dot2listInfo = prologUtils.solveGoal("member(X," + listPar + "), dot2list(X,K).");
        if (dot2listInfo.isSuccess()) {
            String process = dot2listInfo.getTerm("K").toString();
            inputCompute = inputCompute.concat( process+ ",");

            inputList.add(process);

            while (prologUtils.getEngine().hasOpenAlternatives()) {
                SolveInfo recursiveInfo = prologUtils.getEngine().solveNext();
                if (recursiveInfo.isSuccess()) {
                    String recursiveProcess = recursiveInfo.getTerm("K").toString();
                    inputCompute = inputCompute.concat( recursiveProcess+ ",");

                    inputList.add(recursiveProcess);
                }
            }
        }
        inputCompute = inputCompute.substring(0, inputCompute.length() - 1).concat("]");
        tempInputList.addAll(inputList);

        return inputCompute;
    }

    @Override
    public List<String> getInputList(){
        return inputList;
    }

    @Override
    public String outputConverter(final String prologOutput) throws UnknownVarException, NoSolutionException, NoMoreSolutionException {
        String parSequence = "";
        String valueState="[";

        SolveInfo list2dotInfo = prologUtils.solveGoal("member(X," + prologOutput + "), list2dot(X,K).");
        if (list2dotInfo.isSuccess()) {
            valueState = valueState.concat(list2dotInfo.getTerm("K").toString() + ",");

            while (prologUtils.getEngine().hasOpenAlternatives()) {
                SolveInfo info2 = prologUtils.getEngine().solveNext();
                if (info2.isSuccess()) {

                    valueState = valueState.concat(info2.getTerm("K").toString() + ",");
                }
            }

        }

        if (!valueState.equals("[")) {
            valueState = valueState.substring(0, valueState.length() - 1).concat("]");
            SolveInfo list2parInfo = prologUtils.solveGoal("list2par(" + valueState + ", L).");
            if (list2parInfo.isSuccess()) {
                parSequence = list2parInfo.getTerm("L").toString();
            }
        }

        return parSequence;
    }

    @Override
    public void reInitialization(int index){
        inputList.set(index, tempInputList.get(index));
    }


}
