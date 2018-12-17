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

    private final static String PROLOG_PATH = "src/main/prolog/LTSoperator.pl";
    private final static int END = 0;

    private LabelTransitionSystemImpl labelTransitionSystem;
    private PrologUtils prologUtils;
    private Counter level;
    private Converter converter;
    boolean isFirst = true;

    public LTSComputing() throws IOException, InvalidTheoryException {
        level = new CounterImpl();
        prologUtils = new PrologUtilsImpl(PROLOG_PATH);
        converter = new ConverterImpl();
        labelTransitionSystem = LabelTransitionSystemImpl.getInstance();
    }

    public void computeState() throws UnknownVarException, NoSolutionException, NoMoreSolutionException, InterruptedException {
        if(labelTransitionSystem.getLabelTransitionSystem().size()-1 <= level.getCounter()) {
            List<TransitionState> listAtLevel = labelTransitionSystem.getLabelTransitionSystem();
            if(!(listAtLevel.isEmpty())) {
                //for (TransitionState transitionState : listAtLevel) {
               TransitionState transitionState = getTransitionValue();
                    int index = 0;
                    String input = "["+transitionState.getFinalState().getValueState()+","+ END +"]";
                    String goal ="rule("+ input + ", EV, FS).";
                    SolveInfo info = prologUtils.solveGoal(goal);

                    if (info.isSuccess()) {
                        System.out.print("info -- " +info.getSolution()+"\n");
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
                //level.increment();
               //computeState();
            }
        }
    //}

    private void computeNewState(final SolveInfo info, final TransitionState transitionState, final String event,
                                 final String finalState, final int index) throws UnknownVarException, NoSolutionException, NoMoreSolutionException {
        if (!event.equals("0")) {
            //converter.getInputList().set(index, finalState);
            String output = converter.outputConverter(finalState);
            State equalState = checkEqualState(output);
            TransitionState transitionState1 = null;
            if (event.contains("'>'")) {
                if (isFirst) {
                    transitionState1= transitionState;
                    isFirst = false;
                } else {
                   //int i = labelTransitionSystem.getLabelTransitionSystem().size() -1;
                    transitionState1 = labelTransitionSystem.getLabelTransitionSystem().get(level.getCounter()+1);

                }
            }else {
                isFirst = true;
                transitionState1 = transitionState;
            }
            if (equalState != null) {
                labelTransitionSystem.addTransitionState(level.getCounter() + 1, new TransitionStateImpl(
                        transitionState1.getFinalState(), equalState, event));
            } else {
                StateImpl newState = new StateImpl(output, level.getCounter() + 1);
                labelTransitionSystem.addTransitionState(level.getCounter() + 1, new TransitionStateImpl(
                        transitionState1.getFinalState(), newState, event));
                labelTransitionSystem.addState(newState);
                System.out.println("id: " + newState.getId() + "    value: " + newState.getValueState() );
            }
        }
        // converter.reInitialization(index);
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

    private TransitionState getTransitionValue() {
        for (Iterator<TransitionState> it = labelTransitionSystem.getLabelTransitionSystem().iterator(); it.hasNext();){
            TransitionState transitionState = it.next();
            if(!transitionState.getFinalState().getValueState().isEmpty()){
                return transitionState;
            }
        }
        return null;
    }
}
