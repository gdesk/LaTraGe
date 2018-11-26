package lbsStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabelTransitionSystemImpl implements LabelTransitionSystem {

    private Map<Integer, List<TransitionState>> labelTransitionSystem;
    private List<State> allStates;

    public LabelTransitionSystemImpl(){
        this.labelTransitionSystem = new HashMap<>();
        this.allStates = new ArrayList<>();
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
    public void addTransitionState(final int level, final TransitionState transition) {
        if(labelTransitionSystem.containsKey(level)){
            labelTransitionSystem.get(level).add(transition);
        }else{
            List<TransitionState> listAtLevel = new ArrayList<>();
            listAtLevel.add(transition);
            labelTransitionSystem.put(level, listAtLevel);
        }
    }

    @Override
    public void removeTransitionState(TransitionState transition) {
        labelTransitionSystem.remove(transition.getLevel(), transition);
    }

}
