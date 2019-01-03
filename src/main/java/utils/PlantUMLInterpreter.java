package utils;

/**
 * This interface is utils for managed plantUML creation
 */

public interface PlantUMLInterpreter {

    /**
     * Create a platUML
     * @return a sequence builder
     */
    StringBuilder createPlantUML();
    void reset();
}
