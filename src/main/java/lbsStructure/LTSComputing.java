package lbsStructure;

import alice.tuprolog.*;
import prologConfiguration.PrologUtils;
import prologConfiguration.PrologUtilsImpl;
import utils.Counter;
import utils.CounterImpl;
import java.io.IOException;
import java.util.ArrayList;
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
        level = new CounterImpl(0);
        prologUtils = new PrologUtilsImpl(PROLOG_PATH);
        converter = new ConverterImpl();
        labelTransitionSystem = LabelTransitionSystemImpl.getInstance();
    }

    public void computeState() throws UnknownVarException, NoSolutionException, NoMoreSolutionException, InterruptedException {
        if (labelTransitionSystem.getLabelTransitionSystem().size() - 1 <= level.getCounter()) {

            List<TransitionState> listAtLevel = labelTransitionSystem.getTransitionList(level.getCounter());
            if (!(listAtLevel.isEmpty())) {
                for(Iterator<TransitionState> it = listAtLevel.iterator(); it.hasNext();) {
                    TransitionState transitionState = it.next();
                    System.err.println("ENTRAAA");
                    int index = 0;
                    String input = "[" + transitionState.getFinalState().getValueState() + "," + END + "]";
                    String goal = "rule(" + input + ", EV, FS).";
                    SolveInfo info = prologUtils.solveGoal(goal);

                    if (info.isSuccess()) {
                        System.out.print("info -- " + info.getSolution() + "\n");
                        String event = info.getTerm("EV").toString();
                        String finalState = info.getTerm("FS").toString();
                        computeNewState(transitionState, event, finalState, level.getCounter());
                        index++;
                        while (prologUtils.getEngine().hasOpenAlternatives()) {
                            SolveInfo recursiveInfo = prologUtils.getEngine().solveNext();
                            if (recursiveInfo.isSuccess()) {
                                String recursiveEvent = recursiveInfo.getTerm("EV").toString();
                                String recursiveFinalState = recursiveInfo.getTerm("FS").toString();
                                computeNewState(transitionState, recursiveEvent, recursiveFinalState, level.getCounter());
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

    private void computeNewState(TransitionState transitionState, String event, String finalState, int counter) throws NoMoreSolutionException, UnknownVarException, NoSolutionException {
        if (!event.equals("0")) {
            //converter.getInputList().set(index, finalState);
            String output = converter.outputConverter(finalState);

            State equalState = checkEqualState(output);
            TransitionState transitionState1;
            if (event.contains("'>'")) {
                if (isFirst) {
                    transitionState1= transitionState;
                    System.err.println("ENTRA IN CONTAINS PRIMA");
                    isFirst = false;
                } else {
                    System.err.println("ENTRA IN CONTAINS DOPO");
                   int i = labelTransitionSystem.getLabelTransitionSystem().get(counter+1).size();

                    transitionState1 = labelTransitionSystem.getLabelTransitionSystem().get(level.getCounter()+1).get(i-1);

                }
            }else {
                isFirst = true;
                transitionState1 = transitionState;
            }
            if (equalState != null) {
                System.err.println("  "+ (counter+1));
                labelTransitionSystem.addTransitionState((counter+1), new TransitionStateImpl(
                        transitionState1.getFinalState(), equalState, event));
            } else {
                System.err.println("  "+ (counter+1));
                StateImpl newState = new StateImpl(output);
                labelTransitionSystem.addTransitionState((counter+1), new TransitionStateImpl(
                        transitionState1.getFinalState(), newState, event));
                labelTransitionSystem.addState(newState);
                System.out.println("id: " + newState.getId() + "    value: " + newState.getValueState()+ "   initialState id "+ transitionState1.getFinalState().getId());
            }
        }
System.err.println("MAPPAAA   "+labelTransitionSystem.getLabelTransitionSystem().get(1).size());


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


}
