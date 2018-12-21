package lbsStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabelTransitionSystemImpl implements LabelTransitionSystem {

    private static LabelTransitionSystemImpl instance = null;

    private Map<Integer, List<TransitionState>> labelTransitionSystem = new HashMap<>();
    private List<State> allStates = new ArrayList<>();
    List<List<String>> listPlantUML = new ArrayList<>();

    public LabelTransitionSystemImpl(){}

    public static LabelTransitionSystemImpl getInstance() {
        if(instance ==null)
            instance = new LabelTransitionSystemImpl();
        return instance;
    }

    @Override
    public List<State> getAllStates() {
        return allStates;
    }

    @Override
    public void addState(State state){
        allStates.add(state);
    }

    @Override
    public Map<Integer, List<TransitionState>> getLabelTransitionSystem() {
        return labelTransitionSystem;
    }

    @Override
    public void addTransitionState(final int computingTurn, final TransitionState transition) {
        if(labelTransitionSystem.get(computingTurn) == null){
            labelTransitionSystem.put(computingTurn, new ArrayList<TransitionState>());
        }
        labelTransitionSystem.get(computingTurn).add(transition);
    }

    @Override
    public void removeTransitionState(TransitionState transition) {
        labelTransitionSystem.remove(transition);
    }

    @Override
    public List<TransitionState> getTransitionList(final int key){

        if(!labelTransitionSystem.containsKey(key)){
            labelTransitionSystem.put(key, new ArrayList<>());
        }
        return labelTransitionSystem.get(key);
    }

    @Override
    public List<List<String>> listToPlantUML() {
        /*List<List<String>> listPlantUML = new ArrayList<>();

        labelTransitionSystem.forEach((level, states) -> {


            if (level == 0) {
                List<String> currentState = new ArrayList<String>();
                currentState.add("");
                currentState.add(states.get(0).getFinalState().getId());
                currentState.add("");
                listPlantUML.add(currentState);
            } else {
                states.forEach(transition -> {
                    if (!transition.getFinalState().getValueState().isEmpty()) {
                        List<String> currentState = new ArrayList<String>();
                        currentState.add(transition.getInitialState().getId());
                        currentState.add(transition.getFinalState().getId());
                        currentState.add(transition.getEvent());
                        if(!(listPlantUML.contains(currentState))){
                            listPlantUML.add(currentState);
                        }
                    }
                });
            }

        });*/
        System.out.println("LIST PLNT -- > " + listPlantUML);
        return listPlantUML;
    }

    public void addPlantUML(String i, String f, String e){
        List<String> currentState = new ArrayList<String>();
        currentState.add(i);
        currentState.add(f);
        currentState.add(getEvent(e));

        if(!(listPlantUML.contains(currentState))){
            listPlantUML.add(currentState);
        }
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
