package lbsStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabelTransitionSystemImpl implements LabelTransitionSystem {

    private Map<Integer, List<TransitionState>> labelTransitionSystem;

    public LabelTransitionSystemImpl(){
        this.labelTransitionSystem = new HashMap<>();
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
