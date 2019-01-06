import view.View;

/**
 * The main class of the system.
 */
public class MainClass {

    public static void main(String [ ] args) {
        //TODO: ricordati di pulire il main a fine progetto. Teniamo solo per comodita in fase di test
        /*final String input = args[0];
        final LabelTransitionSystemImpl labelTransitionSystem = LabelTransitionSystemImpl.getInstance();
        final StateImpl root = new StateImpl(input);
        final TransitionState rootTransition = new TransitionStateImpl(null, root, "");
        labelTransitionSystem.addState(root);
        labelTransitionSystem.addTransitionState(0, rootTransition );
        viewModel.LTSComputingImpl ltsComputing = new viewModel.LTSComputingImpl();
        ltsComputing.computeState();
        PlantUMLUtils plantUMLutils = new PlantUMLUtilsImpl();
        plantUMLutils.generateImage();*/
        new View();
    }
}