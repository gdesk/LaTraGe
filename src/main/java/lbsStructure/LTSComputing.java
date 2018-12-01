package lbsStructure;

import alice.tuprolog.*;
import com.sun.corba.se.impl.orbutil.concurrent.CondVar;
import prologConfiguration.PrologUtils;
import prologConfiguration.PrologUtilsImpl;
import utils.Counter;
import utils.CounterImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class LTSComputing {

    private final static String PROLOG_PATH = "src/main/prolog/LTSoperators.pl";
    private final static String RULE = "rule_parallel(";
    private final static String EVENT = "EV";
    private final static String FINAL_STATE = "FS";
    private final static String COMMA = ", ";
    private final static String DOT = ".";
    private final static String CLOSE_ROUND = ")";

    private LabelTransitionSystemImpl labelTransitionSystem;
    private PrologUtils prologUtils;
    private Counter level;
    private String inputCompute;
    private Converter converter;

    public LTSComputing() throws IOException, InvalidTheoryException {
        level = new CounterImpl();
        prologUtils = new PrologUtilsImpl(PROLOG_PATH);
        converter = new Converter();
        labelTransitionSystem = LabelTransitionSystemImpl.getIstance();
        inputCompute="[";
    }

    /* RICORDAAAAAA : la transition della radice è messa solo in final state*/
    public void computeState() throws UnknownVarException, NoSolutionException, NoMoreSolutionException, InterruptedException {
        if(labelTransitionSystem.getLabelTransitionSystem().size()-1 <= level.getCounter()) {
            List<TransitionState> listAtLevel = getListAtLevel(level.getCounter());
            if(!(listAtLevel == null)) {
                for (TransitionState s : listAtLevel) {
                    System.out.println("input DIOSANTO :  " + converter.getinputList());
                    String input = converter.inputConverter(s.getFinalState().getValueState(), inputCompute);
                    System.out.println("input :  " + input);
                    String goal = RULE + input + COMMA + EVENT + COMMA + FINAL_STATE + CLOSE_ROUND + DOT;

                    //System.out.println("GOAL" + goal);
                    //System.out.println("listaaaa  ---------------------- " + converter.getinputList());
                    SolveInfo info = prologUtils.solveGoal(goal);
                    System.out.println("info " + info);
                    if (info.isSuccess()) {
                        String event = info.getTerm("EV").toString();
                        String finalState = info.getTerm("FS").toString();
                        if (!event.equals("0")) {
                            converter.getinputList().set(0, finalState);
                            String valueState = converter.outputConverter(converter.getinputList().toString());
                            System.out.println("listaaaa  ---------------------- " + converter.getinputList());
                            System.out.println("valueState   -> " + valueState);
                            StateImpl state = createNewState(valueState);
                            System.out.println("NEW STATE 0   " + state.getId() + " - " + state.getValueState());
                            labelTransitionSystem.addState(state);
                            addTransitionInLTS(s, state, info, event);
                            converter.ReInit(0);
                        }
                        //  System.out.println("RE INIT -------------" + converter.getinputList().size());
                        int index = 1;
                        while (prologUtils.getEngine().hasOpenAlternatives()) {
                            SolveInfo recursiveInfo = prologUtils.getEngine().solveNext();
                            System.out.println("solveinfo alternativ   " + recursiveInfo);
                            if (recursiveInfo.isSuccess()) {
                                String event1 = recursiveInfo.getTerm("EV").toString();
                                String finalState1 = recursiveInfo.getTerm("FS").toString();
                                if (!event1.equals("0")) {
                                    System.out.println("listaaaa PRIMA ---------------------- " + converter.getinputList());
                                    converter.getinputList().set(index, finalState1);
                                    System.out.println("listaaaa  ---------------------- " + converter.getinputList());
                                    String valueState1 = converter.outputConverter(converter.getinputList().toString());
                                    System.out.println(" recursive valueState" + valueState1);
                                    StateImpl recursiveStates = createNewState(valueState1);
                                    System.out.println("NEW STATE" + index + " " + recursiveStates.getId() + " - " + recursiveStates.getValueState());
                                    labelTransitionSystem.addState(recursiveStates);
                                    addTransitionInLTS(s, recursiveStates, recursiveInfo, event1);
                                    converter.ReInit(index);
                                }
                                //    System.out.println("RE INIT -------------" + converter.getinputList().toString());

                            }


                            index++;
                        }
                    }
                }
                level.increment();
                //System.out.print("count inc " + level.getCounter());
                computeState();
            }
        }
    }

    private boolean isPrologGoalSuccess(final SolveInfo info){
        return info.isSuccess();
    }

    private List<TransitionState> getListAtLevel(int level) {
        return labelTransitionSystem.getLabelTransitionSystem().get(level);
    }

    private void addTransitionInLTS(final TransitionState s, final StateImpl state, final SolveInfo info, final String event)
            throws UnknownVarException, NoSolutionException {
        labelTransitionSystem.addTransitionState(level.getCounter() + 1, new TransitionStateImpl(
                s.getFinalState(), state, event));
    }

    private StateImpl createNewState(final String info) throws UnknownVarException, NoSolutionException {
        return new StateImpl(info , level.getCounter() + 1);
    }
}
