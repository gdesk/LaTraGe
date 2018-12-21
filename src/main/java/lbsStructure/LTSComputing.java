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
    boolean isFinish = false;

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
                for (Iterator<TransitionState> it = listAtLevel.iterator(); it.hasNext(); ) {
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
                        System.out.println("EVVV:"+  event);

                        computeNewState(transitionState, event, finalState, level.getCounter());


                        index++;
                        while (prologUtils.getEngine().hasOpenAlternatives()) {
                            SolveInfo recursiveInfo = prologUtils.getEngine().solveNext();
                            if (recursiveInfo.isSuccess()) {
                                String recursiveEvent = recursiveInfo.getTerm("EV").toString();
                                String recursiveFinalState = recursiveInfo.getTerm("FS").toString();
                                System.out.println("EVVV:"+  recursiveEvent);

                                computeNewState(transitionState, recursiveEvent, recursiveFinalState, level.getCounter());

                            }
                            index++;
                        }
                    }


                }

                    isFirst=true;
                    level.increment();
                    computeState();

            }
        }
    }

    private void computeNewState(TransitionState transitionState, String event, String finalState, int counter) throws NoMoreSolutionException, UnknownVarException, NoSolutionException {

            //converter.getInputList().set(index, finalState);
            //String output = converter.outputConverter(finalState);
            State equalState = checkEqualState(finalState);
           // counter++;
            TransitionState transitionState1;
                if(equalState != null){

                    labelTransitionSystem.addPlantUML(transitionState.getFinalState().getId(),equalState.getId(),event);

                    labelTransitionSystem.addTransitionState((level.getCounter()+1),new TransitionStateImpl(transitionState.getFinalState(), equalState, event));

                }else{
                    State newState = new StateImpl(finalState);
                    labelTransitionSystem.addPlantUML(transitionState.getFinalState().getId(),newState.getId(),event);

                    labelTransitionSystem.addTransitionState((level.getCounter()+1),new TransitionStateImpl(transitionState.getFinalState(), newState, event));
                    labelTransitionSystem.addState(newState);
                }

            }




        // converter.reInitialization(index);


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
