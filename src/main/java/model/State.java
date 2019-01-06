package model;

/**
 * This interface manages the specific state object
 */

public interface State {

    /**
     * Get state identifier
     *
     * @return state identifier
     */
    String getId();

    /**
     * Get state value
     *
     * @return state value
     */
    String getValueState();

    /**
     * Reset state counter
     */
    void reset();

}
