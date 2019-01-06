package model;

/**
 * This interface manages the specific transition rule.
 */

public interface TransitionState {

    /**
     * Get initial state of the current transition state
     *
     * @return actual initial state
     */
    State getInitialState();

    /**
     * Get final state of the current transition state, that is an output produced  starting from initial state
     *
     * @return all final states
     */
    State getFinalState();

    /**
     * Get event of the current transition, consumed from initial state
     *
     * @return consume event
     */
    String getEvent();

}
