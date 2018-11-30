package lbsStructure;

import alice.tuprolog.*;
import com.sun.corba.se.impl.orbutil.concurrent.CondVar;
import prologConfiguration.PrologUtils;
import prologConfiguration.PrologUtilsImpl;
import utils.Counter;
import utils.CounterImpl;
import java.io.IOException;
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
    public void computeState() throws UnknownVarException, NoSolutionException, NoMoreSolutionException {
        if(labelTransitionSystem.getLabelTransitionSystem().size()-1 <= level.getCounter()) {
            List<TransitionState> listAtLevel = getListAtLevel();
            for (TransitionState s : listAtLevel) {
                String goal = RULE + converter.inputConverter(s.getFinalState().getValueState(), inputCompute) + COMMA + EVENT + COMMA + FINAL_STATE + CLOSE_ROUND + DOT;
                //System.out.println("GOAL" + goal);
                //System.out.println("listaaaa  ---------------------- " + converter.getinputList());
                SolveInfo info = prologUtils.solveGoal(goal);
                //System.out.println("info "+ info);
                if (info.isSuccess()) {
                    converter.getinputList().set(0, info.getTerm("FS").toString());
                    String valueState =converter.outputConverter(converter.getinputList().toString());
                    //System.out.println("listaaaa  ---------------------- " + converter.getinputList());
                    //System.out.println("valueState" + valueState);
                    StateImpl state = createNewState(valueState);
                    //System.out.println("NEW STATE 0   " + state.getId() + " - " + state.getValueState());
                    labelTransitionSystem.addState(state);
                    addTransitionInLTS(s, state, info);
                    int index = 1;
                    while (prologUtils.getEngine().hasOpenAlternatives()) {
                        SolveInfo recursiveInfo = prologUtils.getEngine().solveNext();
                        //System.out.println("solveinfo alternativ   " + recursiveInfo);
                        if (recursiveInfo.isSuccess()) {
                            converter.getinputList().set(index, recursiveInfo.getTerm("FS").toString());
                            //System.out.println("listaaaa  ---------------------- " + converter.getinputList());
                            String valueState1 =converter.outputConverter(converter.getinputList().toString());
                            //System.out.println("valueState" + valueState1);
                            StateImpl recursiveStates = createNewState(valueState1);
                            //System.out.println("NEW STATE" + index + " " + recursiveStates.getId() + " - " + recursiveStates.getValueState());
                            labelTransitionSystem.addState(recursiveStates);
                            addTransitionInLTS(s, recursiveStates, recursiveInfo);
                        }
                        index++;
                    }
                }
            }
            level.increment();
            //System.out.print("count inc " + level.getCounter());
          //computeState();
        }
    }

    private boolean isPrologGoalSuccess(final SolveInfo info){
        return info.isSuccess();
    }

    private List<TransitionState> getListAtLevel() {
        return labelTransitionSystem.getLabelTransitionSystem().get(level.getCounter());
    }

    private void addTransitionInLTS(final TransitionState s, final StateImpl state, final SolveInfo info)
            throws UnknownVarException, NoSolutionException {
        labelTransitionSystem.addTransitionState(level.getCounter() + 1, new TransitionStateImpl(
                s.getFinalState(), state, info.getTerm("EV").toString()));
    }

    private StateImpl createNewState(final String info) throws UnknownVarException, NoSolutionException {
        return new StateImpl(info , level.getCounter() + 1);
    }
}
