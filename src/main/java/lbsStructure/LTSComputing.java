package lbsStructure;

import alice.tuprolog.*;
import prologConfiguration.PrologUtils;
import prologConfiguration.PrologUtilsImpl;
import utils.Counter;
import utils.CounterImpl;

import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public class LTSComputing {

    private int level;
    private LabelTransitionSystemImpl labelTransitionSystem;
    private PrologUtils prologUtils;
    private Counter level1;

    public LTSComputing() throws IOException, InvalidTheoryException {
        level1 = new CounterImpl();
        level = 0;
        prologUtils = new PrologUtilsImpl("src/main/prolog/LTSoperators.pl");
        labelTransitionSystem = LabelTransitionSystemImpl.getIstance();
    }

    /* RICORDAAAAAA : la transition della radice è messa solo in final state*/
    public void computeState() throws UnknownVarException, NoSolutionException, NoMoreSolutionException {
        if(labelTransitionSystem.getLabelTransitionSystem().size()-1 <= level1.getCounter()) {
            List<TransitionState> listAtLevel = labelTransitionSystem.getLabelTransitionSystem().get(level1.getCounter());
            System.out.println("  list at level  "+ labelTransitionSystem.getLabelTransitionSystem().get(level1.getCounter()).isEmpty());
            System.out.println("listtttt iterao" + listAtLevel.iterator() );
            Iterator<TransitionState> iterator = listAtLevel.iterator();
            while (iterator.hasNext()) {
                TransitionState s = iterator.next();
                System.out.println("ENTRA IN ITERATOR");
                SolveInfo info = prologUtils.solveGoal("rule_parallel(" + s.getFinalState().getValueState() +", EV, FS).");
                System.out.println("ENTRA IN PROLOG with info  --- " + info);
                if (info.isSuccess()) {
                    System.out.println("ENTRA IN WHILE PER SI.");
                    System.out.println("info  "+ info);
                    StateImpl state = new StateImpl(info.getTerm("FS").toString(), level1.getCounter() + 1);
                    labelTransitionSystem.addState(state);
                    labelTransitionSystem.addTransitionState(level1.getCounter() + 1, new TransitionStateImpl(s.getFinalState(), state, info.getTerm("EV").toString()));
                    while (prologUtils.getEngine().hasOpenAlternatives()) {
                        SolveInfo info1 = prologUtils.getEngine().solveNext();
                        if(info1.isSuccess()){
                        System.out.println("info  "+ info1);
                        StateImpl state1 = new StateImpl(info1.getTerm("FS").toString(), level1.getCounter()+1);
                        System.out.println("info  "+ info1.getSolution());
                        labelTransitionSystem.addState(state1);
                        labelTransitionSystem.addTransitionState(level1.getCounter() + 1, new TransitionStateImpl(s.getFinalState(), state1, info1.getTerm("EV").toString()));
                        }
                    }
                }
            }
            level1.increment();
            System.out.print("count inc " + level1.getCounter());
            computeState();
        }
    }
}
