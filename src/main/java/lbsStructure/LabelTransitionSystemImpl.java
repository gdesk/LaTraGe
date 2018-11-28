package lbsStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabelTransitionSystemImpl implements LabelTransitionSystem {

    private static LabelTransitionSystemImpl istance=null; //riferimento all' istanza

    private Map<Integer, List<TransitionState>> labelTransitionSystem = new HashMap<>();;
    private List<State> allStates = new ArrayList<>();

    public LabelTransitionSystemImpl(){}

    public static LabelTransitionSystemImpl getIstance() {
        if(istance==null)
            istance = new LabelTransitionSystemImpl();
        return istance;
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
            System.out.println("In LTS ---> già  creata la lista" + level );
            labelTransitionSystem.get(level).add(transition);
        }else{
            System.out.println("In LTS ---> creo nuova lista" );
            List<TransitionState> listAtLevel = new ArrayList<>();
            listAtLevel.add(transition);
            System.out.println("transitions: " + transition.getFinalState().getId());
            labelTransitionSystem.put(level, listAtLevel);
        }
    }

    @Override
    public void removeTransitionState(TransitionState transition) {
        labelTransitionSystem.remove(transition.getLevel(), transition);
    }

}
