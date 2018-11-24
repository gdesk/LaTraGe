package lbsStructure;

public interface TransitionState {
    State getInitialState();

    State getFinalState();

    String getEvent();

    int getLevel();
}
