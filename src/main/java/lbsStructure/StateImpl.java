package lbsStructure;

public class StateImpl implements State {

    private String valueState;
    private int numberId;
    private int level;

    public StateImpl(String valueState, int level) {
        this.valueState = valueState;
        this.level = level;
        this.numberId=0;
    }

    @Override
    public String getId() {
        String id=  "s"+numberId;
        numberId++;
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
