package lbsStructure;

import alice.tuprolog.*;
import prologConfiguration.PrologUtils;
import prologConfiguration.PrologUtilsImpl;
import utils.Counter;
import utils.CounterImpl;
import java.io.IOException;
import java.util.Iterator;
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
                    String goal ="rule("+ input + ", EV, FS).";
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
            converter.getInputList().set(index, finalState);
            String valueState = converter.outputConverter(converter.getInputList().toString());

                State valuateState = checkEqualState(valueState);
                if(valuateState!= null){
                    labelTransitionSystem.addTransitionState(level.getCounter() + 1, new TransitionStateImpl(
                            transitionState.getFinalState(), valuateState, event));
                }else {
                    StateImpl newState = new StateImpl(valueState , level.getCounter() + 1);
                    labelTransitionSystem.addTransitionState(level.getCounter() + 1, new TransitionStateImpl(
                            transitionState.getFinalState(), newState, event));
                    labelTransitionSystem.addState(newState);
                    System.out.println("id: " +newState.getId() + "    value: "+newState.getValueState());
                }
            }

            converter.reInitialization(index);
    }

    private State checkEqualState(String valueState){
        for(Iterator<State> it = labelTransitionSystem.getAllStates().iterator(); it.hasNext();){
            State state = it.next();
            if(state.getValueState().equals(valueState)){
               return state;
            }
        }
        return null;
    }



}
