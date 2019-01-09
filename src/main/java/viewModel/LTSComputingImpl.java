package viewModel;

import alice.tuprolog.*;
import model.*;
import prologConfiguration.Java2Prolog;
import prologConfiguration.Java2PrologImpl;
import prologConfiguration.PrologConfig;
import utils.Counter;
import utils.CounterImpl;
import java.io.IOException;
import java.util.List;

/**
 * The class manages the computing of entire LTS diagram.
 */
public class LTSComputingImpl implements LTSComputing {

    private final static String PROLOG_PATH = "src/main/prolog/LTSOperators.pl";
    private final static int END = 0;

    private LabelTransitionSystemImpl labelTransitionSystem;
    private Java2Prolog java2Prolog;
    private Counter level;

    public LTSComputingImpl() throws IOException, InvalidTheoryException {
        reset();
    }

    public void computeState() throws UnknownVarException, NoSolutionException, NoMoreSolutionException {
        if (labelTransitionSystem.getLabelTransitionSystem().size() - 1 <= level.getCounter()) {
            List<TransitionState> listAtLevel = labelTransitionSystem.getTransitionList(level.getCounter());
            if (!(listAtLevel.isEmpty())) {
                for (TransitionState transitionState : listAtLevel) {
                    String input = "[" + transitionState.getFinalState().getValueState() + "," + END + "]";
                    String goal = "rule(" + input + ", EV, FS).";
                    SolveInfo info = java2Prolog.solveGoal(goal);

                    if (info.isSuccess()) {
                        String event = info.getTerm("EV").toString();
                        String finalState = info.getTerm("FS").toString();
                        computeNewState(transitionState, event, finalState);

                        while (PrologConfig.engine.hasOpenAlternatives()) {
                            SolveInfo recursiveInfo = java2Prolog.getEngine().solveNext();
                            if (recursiveInfo.isSuccess()) {
                                String recursiveEvent = recursiveInfo.getTerm("EV").toString();
                                String recursiveFinalState = recursiveInfo.getTerm("FS").toString();
                                computeNewState(transitionState, recursiveEvent, recursiveFinalState);
                            }
                        }
                    }
                }
                level.increment();
                computeState();
            }
        }
    }

    public void reset() throws IOException, InvalidTheoryException{
        labelTransitionSystem = LabelTransitionSystemImpl.getInstance();
        level = new CounterImpl(0);
        java2Prolog = new Java2PrologImpl();
    }


    private void computeNewState(TransitionState transitionState, String event, String finalState) {
        State equalState = checkEqualState(finalState);
        if(equalState != null){
            labelTransitionSystem.addPlantUML(transitionState.getFinalState().getId(),equalState.getId(),event);
            labelTransitionSystem.addTransitionState((level.getCounter()+1),new TransitionStateImpl(transitionState.getFinalState(), equalState, event));
        }else {
            State newState = new StateImpl(finalState);
            labelTransitionSystem.addPlantUML(transitionState.getFinalState().getId(),newState.getId(),event);
            labelTransitionSystem.addTransitionState((level.getCounter()+1),new TransitionStateImpl(transitionState.getFinalState(), newState, event));
            labelTransitionSystem.addState(newState);
        }
    }

    private State checkEqualState(String valueState){
        for (State state : labelTransitionSystem.getAllStates()) {
            if (state.getValueState().equals(valueState)) {
                return state;
            }
        }
        return null;
    }

}
