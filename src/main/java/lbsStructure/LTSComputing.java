package lbsStructure;

import alice.tuprolog.*;
import prologConfiguration.PrologUtils;
import prologConfiguration.PrologUtilsImpl;
import utils.Counter;
import utils.CounterImpl;

import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class LTSComputing {

    private int level;
    private LabelTransitionSystemImpl labelTransitionSystem;
    private PrologUtils prologUtils;
    private Counter level1;
    private List<String> javaList;
    private String inputCompute;

    public LTSComputing() throws IOException, InvalidTheoryException {
        level1 = new CounterImpl();
        level = 0;
        prologUtils = new PrologUtilsImpl("src/main/prolog/LTSoperators.pl");
        labelTransitionSystem = LabelTransitionSystemImpl.getIstance();
        inputCompute="[";
    }

    /* RICORDAAAAAA : la transition della radice è messa solo in final state*/
    public void computeState() throws UnknownVarException, NoSolutionException, NoMoreSolutionException {
        if(labelTransitionSystem.getLabelTransitionSystem().size()-1 <= level1.getCounter()) {
            List<TransitionState> listAtLevel = labelTransitionSystem.getLabelTransitionSystem().get(level1.getCounter());
            Iterator<TransitionState> iterator = listAtLevel.iterator();
            while (iterator.hasNext()) {
                TransitionState s = iterator.next();

                SolveInfo info = prologUtils.solveGoal("rule_parallel(" + inputConverter(s.getFinalState().getValueState()) +", EV, FS).");
                System.out.println("ENTRA IN ITERATOR   "+ inputCompute);
                System.out.println("ENTRA IN PROLOG with info  --- " + info);
                if (info.isSuccess()) {
                    System.out.println("ENTRA IN WHILE PER SI.");
                    System.out.println("info  "+ info);
                    StateImpl state = new StateImpl(info.getTerm("FS").toString(), level1.getCounter() + 1);
                    labelTransitionSystem.addState(state);
                    labelTransitionSystem.addTransitionState(level1.getCounter() + 1, new TransitionStateImpl(s.getFinalState(), state, info.getTerm("EV").toString()));
                    while (prologUtils.getEngine().hasOpenAlternatives()) {
                        SolveInfo info1 = prologUtils.getEngine().solveNext();
                        if(info1.isSuccess()){
                        System.out.println("info  "+ info1);
                        StateImpl state1 = new StateImpl(info1.getTerm("FS").toString(), level1.getCounter()+1);
                        System.out.println("info  "+ info1.getSolution());
                        labelTransitionSystem.addState(state1);
                        labelTransitionSystem.addTransitionState(level1.getCounter() + 1, new TransitionStateImpl(s.getFinalState(), state1, info1.getTerm("EV").toString()));
                        }
                    }
                }
            }
            level1.increment();
            System.out.print("count inc " + level1.getCounter());
            computeState();
        }
    }

    private String inputConverter(final String input) throws UnknownVarException, NoSolutionException, NoMoreSolutionException {
        SolveInfo info = prologUtils.solveGoal("par2list("+input+",L).");
        String listPar = "";

        if(info.isSuccess()){
            listPar = info.getTerm("L").toString();
            System.out.println("par2list  "+ listPar);
        }
        SolveInfo info1 = prologUtils.solveGoal("member(X,"+listPar+"), list2dot(X,K).");
        if(info1.isSuccess()) {
            inputCompute = inputCompute.concat(info1.getTerm("K").toString() + ",");

            while (prologUtils.getEngine().hasOpenAlternatives()) {
                SolveInfo info2 = prologUtils.getEngine().solveNext();
                if(info2.isSuccess()){
                    inputCompute = inputCompute.concat(info1.getTerm("K").toString() + ",");
                }
            }
        }
       inputCompute = inputCompute.substring(0, inputCompute.length()-1).concat("]");
        System.out.println("INPUT COMPUTE" + inputCompute);
        return inputCompute;
    }

    /**
     * Convert the scala list to prolog list.
     * @param javaList list written in scala
     *
     * @return list in prolog, as a string to pass as param in the goal.
     */
    private String javaToPrologList(List<String> javaList){
        String outputList = "[";
        for (String elem : javaList){
            outputList = outputList.concat(elem+",");
        }
        outputList = outputList.substring(0, outputList.length()-2);
        outputList = outputList.concat("]");
        return outputList;
    }

    /**
     * Convert the prolog list to scala list.
     *
     * @param prologList list written in prolog
     * @return list written in scala.
     */
    private List<String> prologToJavaList(final String prologList){
        List<String> javaList = new ArrayList<>();
        String temp = prologList.replace("[","").replace("]","");
        Collections.addAll( javaList, temp.split("dot"));
        return javaList;
    }




}
