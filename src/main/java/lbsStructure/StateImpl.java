package lbsStructure;

import java.util.ArrayList;
import java.util.List;

public class StateImpl implements State {

    private List<State> allStates;
    private String id;
    private String valueState;
    private int level;

    public StateImpl(String id, String valueState, int level) {
        this.id = id;
        this.valueState = valueState;
        this.level = level;
        this.allStates = new ArrayList<>();
        allStates.add(new StateImpl(id, valueState,level));
    }

    @Override
    public List<State> getAllStates(){
        return allStates;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getValueState() {
        return valueState;
    }

    @Override
    public int getLevel() {
        return level;
    }

}
