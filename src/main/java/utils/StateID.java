package utils;

/**
 * This interface manages the state identifier
 */
public interface StateID {

    /**
     * Create new identifier automatically
     *
     * @return state identifier
     */
    String createStateID();

    /**
     * Reset StateID to another computing
     */
    void reset();
}
