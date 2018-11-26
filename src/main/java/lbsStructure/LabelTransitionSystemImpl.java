package lbsStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabelTransitionSystemImpl implements LabelTransitionSystem {

    private Map<Integer, List<TransitionState>> labelTransitionSystem;
    private List<State> allStates;

    private boolean isFirst = true;

    public LabelTransitionSystemImpl(){
        if(isFirst){
            this.labelTransitionSystem = new HashMap<>();
            this.allStates = new ArrayList<>();
        }
        isFirst = false;

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
            System.out.println("In LTS ---> up " + level );
            labelTransitionSystem.get(level).add(transition);
        }else{
            System.out.println("In LTS ---> down" );
            List<TransitionState> listAtLevel = new ArrayList<>();
            listAtLevel.add(transition);
            System.out.println("transitions: " + transition.getFinalState().getValueState());
            labelTransitionSystem.put(level, listAtLevel);
            System.out.println("In LTS ---> upup" + labelTransitionSystem.toString() + " list " + listAtLevel.get(0).getFinalState().getValueState() );
        }
    }

    @Override
    public void removeTransitionState(TransitionState transition) {
        labelTransitionSystem.remove(transition.getLevel(), transition);
    }

}
