package lbsStructure;

/**
 * This interface is utils to managements a all transition rule
 */

public interface TransitionState {

    /**
     * Get initial state observed
     * @return actual initial state
     */
    State getInitialState();

    /**
     * Get final state produce starting from observed initial state
     * @return all final states
     */
    State getFinalState();

    /**
     * Get event consume in initial state
     * @return consume event
     */
    String getEvent();

}
