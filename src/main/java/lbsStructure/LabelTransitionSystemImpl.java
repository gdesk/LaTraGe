package lbsStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabelTransitionSystemImpl implements LabelTransitionSystem {

    private static LabelTransitionSystemImpl instance = null;

    private List<TransitionState> labelTransitionSystem = new ArrayList<>();
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
    public List<TransitionState> getLabelTransitionSystem() {
        return labelTransitionSystem;
    }

    @Override
    public void addTransitionState(final int level, final TransitionState transition) {
        labelTransitionSystem.add(transition);
    }

    @Override
    public void removeTransitionState(TransitionState transition) {
        labelTransitionSystem.remove(transition);
    }

}
