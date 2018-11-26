package lbsStructure;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.UnknownVarException;
import prologConfiguration.PrologUtils;
import prologConfiguration.PrologUtilsImpl;
import java.io.IOException;

public class LTSComputing {

    private int level;
    private LabelTransitionSystem labelTransitionSystem;
    private PrologUtils prologUtils;

    public LTSComputing() throws IOException, InvalidTheoryException {
        level = 0;
        prologUtils = new PrologUtilsImpl("src/main/prolog/LTSoperators.pl");
    }

    /* RICORDAAAAAA : la transition della radice è messa solo in final state*/
    public void computeState() throws UnknownVarException, NoSolutionException {
        System.out.println("input: " + labelTransitionSystem.getAllStates().get(0));
        System.out.println("input: " + labelTransitionSystem.getLabelTransitionSystem());
        for (TransitionState s : labelTransitionSystem.getLabelTransitionSystem().get(level)) {
            SolveInfo solution = prologUtils.solveGoal("rule_dot(" + s.getFinalState().getValueState() +", EV, FS).");
            while(solution.hasOpenAlternatives()){
                StateImpl state = new StateImpl(solution.getTerm("FS").toString(), level);
                labelTransitionSystem.addState(state);
                labelTransitionSystem.addTransitionState(level,new TransitionStateImpl(s.getFinalState(), state, solution.getTerm("EV").toString()));
            }
        }
        level++;
        computeState();
    }
}
