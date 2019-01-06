package utils;

/**
 * This interface manages a simple counter
 */
public interface Counter {

    /**
     * Get current counter value
     *
     * @return current value
     */
    int getCounter();

    /**
     * Increment counter value
     */
    void increment();
}
