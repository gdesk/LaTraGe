import view.View;

/**
 * System main class
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
        viewModel.LTSComputing ltsComputing = new viewModel.LTSComputing();
        ltsComputing.computeState();
        PlantUMLutils plantUMLutils = new PlantUMLutilsImpl();
        plantUMLutils.generateImage();*/
        new View();
    }
}