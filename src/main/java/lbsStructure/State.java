package lbsStructure;

/**
 * This interface management state object
 */

public interface State {

    /**
     * Get state identifier
     * @return state identifier
     */
    String getId();

    /**
     * Get state value
     * @return state value
     */
    String getValueState();

    /**
     * Get state level (in diagram state in output)
     * @return diagram state level
     */
    int getLevel();
}
