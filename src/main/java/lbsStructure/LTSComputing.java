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

    private LabelTransitionSystemImpl labelTransitionSystem;
    private PrologUtils prologUtils;
    private Counter level;
    private String inputCompute;
    private Converter converter;

    public LTSComputing() throws IOException, InvalidTheoryException {
        level = new CounterImpl();
        prologUtils = new PrologUtilsImpl(PROLOG_PATH);
        converter = new ConverterImpl();
        labelTransitionSystem = LabelTransitionSystemImpl.getInstance();
        inputCompute="[";
    }

    public void computeState() throws UnknownVarException, NoSolutionException, NoMoreSolutionException, InterruptedException {
        if(labelTransitionSystem.getLabelTransitionSystem().size()-1 <= level.getCounter()) {
            List<TransitionState> listAtLevel = labelTransitionSystem.getLabelTransitionSystem().get(level.getCounter());
            if(!(listAtLevel == null)) {

                for (TransitionState transitionState : listAtLevel) {
                    int index = 0;
                    String input = converter.inputConverter(transitionState.getFinalState().getValueState(), inputCompute);
                    String goal ="rule_parallel("+ input + ", EV, FS).";
                    SolveInfo info = prologUtils.solveGoal(goal);

                    if (info.isSuccess()) {
                        String event = info.getTerm("EV").toString();
                        String finalState = info.getTerm("FS").toString();
                        computeNewState(info, transitionState, event, finalState, index);
                        index++;

                        while (prologUtils.getEngine().hasOpenAlternatives()) {
                            SolveInfo recursiveInfo = prologUtils.getEngine().solveNext();
                            if(recursiveInfo.isSuccess()) {
                                String recursiveEvent = recursiveInfo.getTerm("EV").toString();
                                String recursiveFinalState = recursiveInfo.getTerm("FS").toString();
                                computeNewState(recursiveInfo, transitionState, recursiveEvent, recursiveFinalState, index);
                            }
                            index++;
                        }
                    }
                }
                level.increment();
                computeState();
            }
        }
    }

    private void computeNewState(final SolveInfo info, final TransitionState transitionState, final String event,
                                 final String finalState, final int index) throws UnknownVarException, NoSolutionException, NoMoreSolutionException {
        if (!event.equals("0")) {
            converter.getinputList().set(index, finalState);
            String valueState = converter.outputConverter(converter.getinputList().toString());
            StateImpl state = new StateImpl(valueState , level.getCounter() + 1);
            labelTransitionSystem.addState(state);
            labelTransitionSystem.addTransitionState(level.getCounter() + 1, new TransitionStateImpl(
                    transitionState.getFinalState(), state, event));
            converter.ReInit(index);
        }
    }


}
