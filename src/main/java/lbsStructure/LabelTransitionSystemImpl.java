package lbsStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabelTransitionSystemImpl implements LabelTransitionSystem {

    private static LabelTransitionSystemImpl instance = null;

    private Map<Integer, List<TransitionState>> labelTransitionSystem = new HashMap<>();
    private List<State> allStates = new ArrayList<>();

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
}
