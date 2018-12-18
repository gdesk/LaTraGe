package lbsStructure;

import java.util.List;
import java.util.Map;

/**
 * This interface management a Label Transition System (LTS)
 */

public interface LabelTransitionSystem {

    /**
     * Get all state in a LTS
     * @return all state in system
     */
    List<State> getAllStates();

    /**
     * Add new state into a LTS
     * @param state to add
     */
    void addState(State state);

    /**
     * Get all transitions rule that start from the initial state in particular level ad arrive
     * in another state by a event
     * @return all LTS
     */
    Map<Integer, List<TransitionState>> getLabelTransitionSystem();

    /**
     * Add new LTS in a particular level
     *
     * @param transition transition state to add
     */
    void addTransitionState(int computingTurn, TransitionState transition);

    /**
     * Remove a transition
     * @param transition transition state to remove
     */
    //TODO: evaluate if needed
    void removeTransitionState(TransitionState transition);
}
