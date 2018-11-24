package lbsStructure;

public class TransitionStateImpl implements TransitionState {

    private State initialState;
    private State finalState;
    private String event;

    public TransitionStateImpl(final State initialState, final State finalState, final String event) {
        this.initialState = initialState;
        this.finalState = finalState;
        this.event = event;
    }

    @Override
    public State getInitialState() {
        return initialState;
    }

    @Override
    public State getFinalState() {
        return finalState;
    }

    @Override
    public String getEvent() {
        return event;
    }

    @Override
    public int getLevel(){
        return finalState.getLevel();
    }
}
