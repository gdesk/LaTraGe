package utils;

import lbsStructure.LabelTransitionSystem;
import lbsStructure.LabelTransitionSystemImpl;
import lbsStructure.State;
import lbsStructure.TransitionState;

import java.util.List;
import java.util.Map;

public class PlantUMLinterpreterImpl implements PlantUMLinterpreter {

    private LabelTransitionSystem ltsStructures = LabelTransitionSystemImpl.getInstance();
    private StringBuilder plantUML;

    public PlantUMLinterpreterImpl(){
        this.plantUML = new StringBuilder();
        startingConfiguration();

    }

    @Override
    public StringBuilder createPlantUML() {
        appendAllState();
        appendTransition();
        return plantUML;
    }

    private void startingConfiguration(){
        plantUML.append("@startuml\n "+
                "skinparam DefaultFontSize 20\n" +
                "skinparam StateFontStyle italics\n" +
                "skinparam DefaultFontName Courier\n" +
                "hide empty description\n" +
                "\n");
    }

    private void appendAllState(){
        final List<State> allState = ltsStructures.getAllStates();
        allState.forEach(state -> {
            if(!state.getValueState().isEmpty()) plantUML = plantUML.append(state.getId()+" : "+state.getValueState()+"\n");
        });
        plantUML = plantUML.append("\n");
    }

    private void appendTransition(){
        final Map<Integer, List<TransitionState>> transictions = ltsStructures.getLabelTransitionSystem();
        transictions.forEach((level, states) ->{
            if(level == 0){
                plantUML = plantUML.append("[*] --> "+ states.get(0).getFinalState().getId());
            }else {
                plantUML =plantUML.append("\n");
                states.forEach(transition -> {
                    if(!transition.getFinalState().getValueState().isEmpty())
                    plantUML =plantUML.append(transition.getInitialState().getId()+" --> "+transition.getFinalState().getId()+": "+ transition.getEvent()+"\n");
                });
            }

        });
        plantUML =plantUML.append("\n@enduml");
    }
}
