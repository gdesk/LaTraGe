package utils;

/**
 * This interface create singleton for managed a Counter object
 */

public interface Counter {

    /**
     * Get counter value
     * @return current value
     */
    int getCounter();

    /**
     * Increment counter value
     */
    void increment();
}
