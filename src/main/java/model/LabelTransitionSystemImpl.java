package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class manages the structures of the system.
 * The core is a Label Transition System(LTS), output state's diagram of this application
 */
public class LabelTransitionSystemImpl implements LabelTransitionSystem {

    private static LabelTransitionSystemImpl instance;

    private Map<Integer, List<TransitionState>> labelTransitionSystem = new HashMap<>();
    private List<State> allStates = new ArrayList<>();
    private List<List<String>> listPlantUML = new ArrayList<>();

    public LabelTransitionSystemImpl() {
        reset();
    }

    public static LabelTransitionSystemImpl getInstance() {
        if(instance == null)
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
        labelTransitionSystem.computeIfAbsent(computingTurn, k -> new ArrayList<>());
        labelTransitionSystem.get(computingTurn).add(transition);
    }

    @Override
    public List<TransitionState> getTransitionList(final int key){

        if(!labelTransitionSystem.containsKey(key)){
            labelTransitionSystem.put(key, new ArrayList<>());
        }
        return labelTransitionSystem.get(key);
    }

    @Override
    public void addPlantUML(String initialState, String finalState, String event){
        List<String> currentState = new ArrayList<String>();
        currentState.add(initialState);
        currentState.add(finalState);
        currentState.add(event);
        if(!(listPlantUML.contains(currentState))){
            listPlantUML.add(currentState);
        }
    }

    @Override
    public List<List<String>> getPlantUMLList() {
        return listPlantUML;
    }

    @Override
    public void reset(){
        listPlantUML.clear();
        labelTransitionSystem.clear();
        for (State s :allStates) {
            s.reset();
        }
        allStates.clear();
        instance = null;
    }
}
