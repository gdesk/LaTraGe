package utils;

import lbsStructure.LabelTransitionSystem;
import lbsStructure.LabelTransitionSystemImpl;
import lbsStructure.State;
import lbsStructure.TransitionState;

import java.util.List;
import java.util.Map;

public class PlantUMLInterpreterImpl implements PlantUMLInterpreter {

    private LabelTransitionSystem ltsStructures = LabelTransitionSystemImpl.getInstance();
    private StringBuilder plantUML;

    public PlantUMLInterpreterImpl(){
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
        boolean isFirst = true;
        final List<TransitionState> transictions = ltsStructures.getLabelTransitionSystem();
        transictions.forEach((states) ->{
            if(isFirst){
                plantUML = plantUML.append("[*] --> "+ states.getFinalState().getId());
            }else {
                plantUML =plantUML.append("\n");
                transictions.forEach(state -> {
                    if(!state.getFinalState().getValueState().isEmpty())
                    plantUML =plantUML.append(state.getInitialState().getId()+" --> "+state.getFinalState().getId()+": "+ state.getEvent()+"\n");
                });
            }

        });
        plantUML =plantUML.append("\n@enduml");
    }
}
