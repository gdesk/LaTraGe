package lbsStructure;

import utils.Counter;
import utils.CounterImpl;
import utils.StateID;
import utils.StateIDImpl;

public class StateImpl implements State {

    private String valueState;
    private String id;
    private Counter numberId =  new CounterImpl();
    private int level;
    private StateID stateID;

    public StateImpl(String valueState, int level) {
        this.stateID = StateIDImpl.getIstance();
        this.valueState = valueState;
        this.level = level;
        this.id = stateID.createStateID();
    }

    @Override
    public String getId() {
        System.out.println("id   " + id);
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
