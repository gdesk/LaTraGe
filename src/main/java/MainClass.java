import alice.tuprolog.InvalidTheoryException;
import prologConfiguration.PrologConfig;
import view.View;

import java.io.IOException;

/**
 * The main class of the system.
 */
public class MainClass {
    private final static String PROLOG_PATH = "src/main/prolog/LTSOperators.pl";

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
        try {
            PrologConfig prologConfig = new PrologConfig(PROLOG_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new View();
    }
}