import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import lbsStructure.*;
import utils.PlantUMLinterpreter;
import utils.PlantUMLinterpreterImpl;
import utils.PlantUMLutils;
import utils.PlantUMLutilsImpl;

import javax.sound.midi.SysexMessage;


public class prova {

    public static void main(String [ ] args) throws Exception {

        final String input = args[0];


        final LabelTransitionSystemImpl labelTransitionSystem = LabelTransitionSystemImpl.getIstance();
        final StateImpl root = new StateImpl(input, 0);
        final TransitionState rootTransition = new TransitionStateImpl(null, root, "");

        labelTransitionSystem.addState(root);
        labelTransitionSystem.addTransitionState(0, rootTransition );

        if(input.contains("par")){

        }else{

        }
        LTSComputing ltsComputing = new LTSComputing();
        ltsComputing.computeState();


        PlantUMLutils plantUMLutils = new PlantUMLutilsImpl();
        plantUMLutils.generateImage();
    }
}