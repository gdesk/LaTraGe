package view;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.NoMoreSolutionException;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.UnknownVarException;
import structure.*;

import java.io.IOException;

public class Computing {
    private LabelTransitionSystemImpl labelTransitionSystem;
    private StateImpl root;
    private TransitionState rootTransition;
    private LTSComputing ltsComputing;

    public Computing(){
        labelTransitionSystem = LabelTransitionSystemImpl.getInstance();
    }


    public void initialization(final String input) throws IOException, InvalidTheoryException, InterruptedException,
            UnknownVarException, NoMoreSolutionException, NoSolutionException {
        root = new StateImpl(input);
        rootTransition = new TransitionStateImpl(null, root, "");
        labelTransitionSystem.addState(root);
        labelTransitionSystem.addTransitionState(0, rootTransition);
        ltsComputing = new LTSComputing();
        ltsComputing.computeState();
    }

    public void reset() throws IOException, InvalidTheoryException {
        ltsComputing.reset();
    }
}
