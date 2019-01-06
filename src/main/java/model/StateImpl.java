package model;

import utils.StateID;
import utils.StateIDImpl;

/**
 * This class manages the specific state object.
 */
public class StateImpl implements State {

    private String valueState;
    private String id;
    StateID stateID;

    public StateImpl(String valueState) {
        stateID = StateIDImpl.getInstance();
        this.valueState = valueState;
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
    public void reset(){
        stateID.reset();
    }
}
