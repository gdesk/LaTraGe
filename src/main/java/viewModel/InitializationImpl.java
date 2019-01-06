package viewModel;

import model.*;

/**
 * This class manages the initial configuration to the computing the entire LTS.
 */
public class InitializationImpl implements Initialization {
    private LabelTransitionSystemImpl labelTransitionSystem;
    private StateImpl root;
    private TransitionState rootTransition;
    private LTSComputingImpl ltsComputingImpl;

    public InitializationImpl(){
        labelTransitionSystem = LabelTransitionSystemImpl.getInstance();
    }

    @Override
    public void start(final String input) {
        try {
            root = new StateImpl(input);
            rootTransition = new TransitionStateImpl(null, root, "");
            labelTransitionSystem.addState(root);
            labelTransitionSystem.addTransitionState(0, rootTransition);
            ltsComputingImpl = new LTSComputingImpl();
            ltsComputingImpl.computeState();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reset() {
        try {
            ltsComputingImpl.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
