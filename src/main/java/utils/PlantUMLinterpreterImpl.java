package utils;

import lbsStructure.LabelTransitionSystem;
import lbsStructure.LabelTransitionSystemImpl;
import lbsStructure.State;
import lbsStructure.TransitionState;

import java.util.List;
import java.util.Map;

public class PlantUMLinterpreterImpl implements PlantUMLinterpreter {

    private LabelTransitionSystem ltsStructures = new LabelTransitionSystemImpl();
    private String plantUML;

    @Override
    public String createPlantUML() {
        plantUML.concat("@startuml\n");
        startingConfiguration();
        appendAllState();
        appendTransition();
        plantUML.concat("\n@enduml");
        return plantUML;
    }

    private void startingConfiguration(){
        plantUML.concat("skinparam DefaultFontSize 20\n" +
                "skinparam StateFontStyle italics\n" +
                "skinparam DefaultFontName Courier\n" +
                "hide empty description\n" +
                "\n" +
                "skinparam state {\n" +
                "  BackgroundColor<<Ending>> olive\n" +
                "  BackgroundColor<<Unexplored>> yellow\n" +
                "}\n\n");
    }

    private void appendAllState(){
        final List<State> allState = ltsStructures.getAllStates();
        allState.forEach(state -> {
            plantUML.concat("state "+ state.getId()+": "+state.getValueState()+"\n");
        });
    }

    private void appendTransition(){
        final Map<Integer, List<TransitionState>> transictions = ltsStructures.getLabelTransitionSystem();
        transictions.forEach((level, states) ->{
            if(level == 0){
                plantUML.concat("[*] --> "+ states.get(0).getFinalState());
            }else {
                plantUML.concat("\n");
                states.forEach(transition -> {
                    plantUML.concat(transition.getInitialState().getId()+" --> "+transition.getFinalState().getId()+": "+ transition.getEvent());
                });
            }

        });
    }
}
