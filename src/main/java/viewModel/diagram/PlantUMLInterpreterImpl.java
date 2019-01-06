package viewModel.diagram;

import model.LabelTransitionSystem;
import model.LabelTransitionSystemImpl;
import model.State;

import java.util.List;

/**
 * This class manages the creation of file in format plantUML to make a diagram
 */
public class PlantUMLInterpreterImpl implements PlantUMLInterpreter {
    private LabelTransitionSystem ltsStructures;
    private StringBuilder plantUML;

    public PlantUMLInterpreterImpl(){
        ltsStructures = LabelTransitionSystemImpl.getInstance();
        this.plantUML = new StringBuilder();
        startingConfiguration();

    }

    @Override
    public StringBuilder createPlantUML() {
        appendAllState();
        appendTransition();
        return plantUML;
    }

    @Override
    public void reset(){
        plantUML =  new StringBuilder();
        ltsStructures.reset();
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
        final List<List<String>> listPlanUML = ltsStructures.getPlantUMLList();
        listPlanUML.forEach(list->{
            if(list.get(0).equals("")){
                plantUML = plantUML.append("[*] --> "+ list.get(1));
                plantUML =plantUML.append("\n");
            }else {
                plantUML =plantUML.append(list.get(0)+" --> "+list.get(1)+": "+ getEvent(list.get(2))+"\n");

            }
        });
        plantUML = plantUML.append("\n@enduml");
    }

    private String getEvent(String event){
        String eventValue = event;
        if(event.contains(">")){
            eventValue =  eventValue.replace("'>'","")
                    .replace("[","")
                    .replace("]", "")
                    .replace(",", "");
        }
        return eventValue;
    }

}
