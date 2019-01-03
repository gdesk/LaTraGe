package structure;

import alice.tuprolog.*;
import prologConfiguration.PrologUtils;
import prologConfiguration.PrologUtilsImpl;
import utils.Counter;
import utils.CounterImpl;
import java.io.IOException;
import java.util.List;



public class LTSComputing {

    private final static String PROLOG_PATH = "src/main/prolog/LTSoperator.pl";
    private final static int END = 0;

    private LabelTransitionSystemImpl labelTransitionSystem;
    private PrologUtils prologUtils;
    private Counter level;

    public LTSComputing() throws IOException, InvalidTheoryException {
        reset();
    }

    public void computeState() throws UnknownVarException, NoSolutionException, NoMoreSolutionException {
        if (labelTransitionSystem.getLabelTransitionSystem().size() - 1 <= level.getCounter()) {
            List<TransitionState> listAtLevel = labelTransitionSystem.getTransitionList(level.getCounter());
            if (!(listAtLevel.isEmpty())) {
                for (TransitionState transitionState : listAtLevel) {
                    System.err.println("ENTRAAA ---------------------");
                    String input = "[" + transitionState.getFinalState().getValueState() + "," + END + "]";
                    String goal = "rule(" + input + ", EV, FS).";
                    SolveInfo info = prologUtils.solveGoal(goal);
                    if (info.isSuccess()) {
                        System.out.print("info -- " + info.getSolution() + "\n");
                        String event = info.getTerm("EV").toString();
                        String finalState = info.getTerm("FS").toString();
                        computeNewState(transitionState, event, finalState);
                        while (prologUtils.getEngine().hasOpenAlternatives()) {
                            SolveInfo recursiveInfo = prologUtils.getEngine().solveNext();
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

    public void reset() throws IOException, InvalidTheoryException{
        labelTransitionSystem = LabelTransitionSystemImpl.getInstance();
        level = new CounterImpl(0);
        prologUtils = new PrologUtilsImpl(PROLOG_PATH);
    }

}
