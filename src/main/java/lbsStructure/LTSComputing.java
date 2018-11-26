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
        System.out.println("\n"+level1.getCounter());
        if(labelTransitionSystem.getLabelTransitionSystem().size() < level1.getCounter()){
            List<TransitionState> listAtLevel = labelTransitionSystem.getLabelTransitionSystem().get(level1.getCounter());
            Iterator<TransitionState> iterator = listAtLevel.iterator();
            while (iterator.hasNext()) {
                TransitionState s = iterator.next();
                SolveInfo info = prologUtils.solveGoal("rule_dot(" + s.getFinalState().getValueState() +", EV, FS).");
                while (info.isSuccess()) {
                    StateImpl state = new StateImpl(info.getTerm("FS").toString(), level1.getCounter());
                    labelTransitionSystem.addState(state);
                    labelTransitionSystem.addTransitionState(level1.getCounter(), new TransitionStateImpl(s.getFinalState(), state, info.getTerm("EV").toString()));
                    if (prologUtils.getEngine().hasOpenAlternatives()) {
                        info = prologUtils.getEngine().solveNext();
                    } else {
                        break;
                    }
                }
            }
            level1.increment();
            System.out.print("count inc " + level1.getCounter());
            computeState();
        }
    }
}
