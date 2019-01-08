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

    public PlantUMLInterpreterImpl() {
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
    public void reset() {
        plantUML = new StringBuilder();
        ltsStructures.reset();
    }

    private void startingConfiguration() {
        plantUML.append("@startuml\n" +
                "skinparam DefaultFontSize 20\n" +
                "skinparam StateFontStyle italics\n" +
                "skinparam DefaultFontName Courier\n" +
                "hide empty description\n" +
                "\n");
    }

    private void appendAllState() {
        final List<State> allState = ltsStructures.getAllStates();
        allState.forEach(state -> {
            if (!state.getValueState().isEmpty())
                plantUML.append(state.getId())
                        .append(" : ")
                        .append(state.getValueState())
                        .append("\n");
        });
        plantUML.append("\n");
    }

    private void appendTransition() {
        final List<List<String>> listPlanUML = ltsStructures.getPlantUMLList();
        listPlanUML.forEach(list -> {
            if (list.get(0).equals("")) {
                plantUML.append("[*] --> ")
                        .append(list.get(1))
                        .append("\n");
            } else {
                plantUML.append(list.get(0))
                        .append(" --> ")
                        .append(list.get(1))
                        .append(": ")
                        .append(getEvent(list.get(2)))
                        .append("\n");

            }
        });
        plantUML.append("\n@enduml");
    }

    private String getEvent(String event) {
        String eventValue = event;
        if (event.contains(">")) {
            eventValue = eventValue.replace("'>'", "")
                    .replace("[", "")
                    .replace("]", "")
                    .replace(",", "");
        }
        return eventValue;
    }

}
