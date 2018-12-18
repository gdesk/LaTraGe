package lbsStructure;

import utils.StateID;
import utils.StateIDImpl;

public class StateImpl implements State {

    private String valueState;
    private String id;

    public StateImpl(String valueState) {
        StateID stateID = StateIDImpl.getInstance();
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



}
