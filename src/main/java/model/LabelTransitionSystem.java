package model;

import java.util.List;
import java.util.Map;

/**
 * This interface manages the structures of the system.
 * The core is a Label Transition System(LTS), output state's diagram of this application
 */

public interface LabelTransitionSystem {

    /**
     * Get all state into the diagram
     *
     * @return list with all state
     */
    List<State> getAllStates();

    /**
     * Add new state into the diagram
     *
     * @param state to add
     */
    void addState(State state);

    /**
     * Get all transitions that start from the initial state in particular computing's turn to arrive
     * in another state by a event
     *
     * @return the entire LTS diagram
     */
    Map<Integer, List<TransitionState>> getLabelTransitionSystem();

    /**
     * Add new transition into the LTS diagram in a particular computing's turn
     *
     * @param transition transition state to add
     * @param computingTurn turn of current computing
     */
    void addTransitionState(int computingTurn, TransitionState transition);

    /**
     * Get all transitions
     *
     * @param key computing level
     *
     * @return list of transitions state in the specific computing level
     */
    List<TransitionState> getTransitionList(int key);

    /**
     * Add new list into PlantUML list, used to create the entire diagram.
     * The PlantUML list contains all transitions state: in each position there is a list
     * with initialState, finalState, event in this order.
     *
     * @param initialState initial state of a specific transition
     * @param finalState final state of a specific transition
     * @param event event of a specific transition
     */
    void addPlantUML(String initialState, String finalState, String event);

    /**
     * Get list of all the transition of the entire diagram.
     *
     * @return the plantUML list
     */
    List<List<String>> getPlantUMLList();


    /**
     * Reset all structures for a new computing.
     *
     */
    void reset();

}
