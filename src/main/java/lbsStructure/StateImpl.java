package lbsStructure;

import utils.Counter;
import utils.CounterImpl;
import utils.StateID;
import utils.StateIDImpl;

public class StateImpl implements State {

    private String valueState;
    private String id;
    private int level;

    public StateImpl(String valueState, int level) {
        StateID stateID = StateIDImpl.getIstance();
        this.valueState = valueState;
        this.level = level;
        this.id = stateID.createStateID();
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
