import lbsStructure.*;
import utils.PlantUMLinterpreter;


public class prova {

    public static void main(String [ ] args) throws Exception {

        final String input = args[0];

        StateImpl root = new StateImpl(input, 0);
        LabelTransitionSystem labelTransitionSystem = new LabelTransitionSystemImpl();
        labelTransitionSystem.addState(root);
        labelTransitionSystem.addTransitionState(0, new TransitionStateImpl(null, root, null));
        System.out.println("LTS " +labelTransitionSystem.getLabelTransitionSystem().get(0));
        LTSComputing ltsComputing = new LTSComputing();
        ltsComputing.computeState();


        System.out.println("PlantUmlInterpreter: \n " + PlantUMLinterpreter.class);
    }
}