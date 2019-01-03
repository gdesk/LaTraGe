package structure;

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
     * @param transition transition state to add
     */
    void addTransitionState(int computingTurn, TransitionState transition);

    /**
     * Add new transition state in list. Structure are: initialState, finalState, event
     * @param initialState actual initial state
     * @param finalState actual final state
     * @param event actual event
     */
    void addPlantUML(String initialState, String finalState, String event);

    /**
     * Reset all structures
     */
    void reset();

    /**
     * Get plant structure
     * @return actual LTS
     */
    List<List<String>> getPlantUMLList();

    /**
     * Get all transitions
     * @param key level
     * @return list of actual transitions in LTS
     */
    List<TransitionState> getTransitionList(int key);
}
