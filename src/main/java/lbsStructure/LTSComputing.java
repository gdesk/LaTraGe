package lbsStructure;

import alice.tuprolog.*;
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

    public LTSComputing() throws IOException, InvalidTheoryException {
        level = new CounterImpl();
        prologUtils = new PrologUtilsImpl(PROLOG_PATH);
        labelTransitionSystem = LabelTransitionSystemImpl.getIstance();
        inputCompute="[";
    }

    /* RICORDAAAAAA : la transition della radice è messa solo in final state*/
    public void computeState() throws UnknownVarException, NoSolutionException, NoMoreSolutionException {
        if(labelTransitionSystem.getLabelTransitionSystem().size()-1 <= level.getCounter()) {
            List<TransitionState> listAtLevel = getListAtLevel();
            for (TransitionState s : listAtLevel) {
                SolveInfo info = prologUtils.solveGoal(RULE + Converter.inputConverter(s.getFinalState().
                        getValueState(), inputCompute, prologUtils) + COMMA + EVENT + COMMA + FINAL_STATE + CLOSE_ROUND + DOT);
                System.out.println("ENTRA IN ITERATOR   "+ inputCompute);
                System.out.println("ENTRA IN PROLOG with info  --- " + info);
                if (isPrologGoalSuccess(info)) {
                    System.out.println("ENTRA IN WHILE PER SI.");
                    System.out.println("info  "+ info);
                    StateImpl state = createNewState(info);
                    labelTransitionSystem.addState(state);
                    addTransitionInLTS(s, state, info);
                    while (prologUtils.getEngine().hasOpenAlternatives()) {
                        SolveInfo recursiveInfo = prologUtils.getEngine().solveNext();
                        if (isPrologGoalSuccess(recursiveInfo)) {
                            System.out.println("info  "+ recursiveInfo);
                            StateImpl recursiveStates = createNewState(recursiveInfo);
                            System.out.println("info  "+ recursiveInfo.getSolution());
                            labelTransitionSystem.addState(recursiveStates);
                            addTransitionInLTS(s, recursiveStates, recursiveInfo);
                        }
                    }
                }
            }
            level.increment();
            System.out.print("count inc " + level.getCounter());
            computeState();
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

    private StateImpl createNewState(final SolveInfo info) throws UnknownVarException, NoSolutionException {
        return new StateImpl(info.getTerm("FS").toString(), level.getCounter() + 1);
    }
}
