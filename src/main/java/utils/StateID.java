package utils;

/**
 * This interface create singleton for manage state identifier
 */

public interface StateID {

    /**
     * Create new identifier
     * @return state identifier
     */
    String createStateID();

    /**
     * Reset StateID
     */
    void reset();
}
