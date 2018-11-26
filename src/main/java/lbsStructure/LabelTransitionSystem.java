package lbsStructure;

import java.util.List;
import java.util.Map;

public interface LabelTransitionSystem {
    List<State> getAllStates();

    void addState(State state);

    Map<Integer, List<TransitionState>> getLabelTransitionSystem();

    void addTransitionState(int level, TransitionState transition);

    void removeTransitionState(TransitionState transition);
}
