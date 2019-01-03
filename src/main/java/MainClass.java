import lbsStructure.*;
import utils.PlantUMLutils;
import utils.PlantUMLutilsImpl;
import view.PikaView;

public class MainClass {

    public static void main(String [ ] args) throws Exception {

        /*final String input = args[0];

        final LabelTransitionSystemImpl labelTransitionSystem = LabelTransitionSystemImpl.getInstance();
        final StateImpl root = new StateImpl(input);
        final TransitionState rootTransition = new TransitionStateImpl(null, root, "");

        labelTransitionSystem.addState(root);
        labelTransitionSystem.addTransitionState(0, rootTransition );
        LTSComputing ltsComputing = new LTSComputing();
        ltsComputing.computeState();*/
        new PikaView();
        /*PlantUMLutils plantUMLutils = new PlantUMLutilsImpl();
        plantUMLutils.generateImage();*/
    }
}