
package viewModel.diagram;

/**
 * This interface manages the creation of file in format plantUML to make a diagram
 */
public interface PlantUMLInterpreter {

    /**
     * Create a file in plantUML format.
     *
     * @return a file in plantUML format.
     */
    StringBuilder createPlantUML();

    /**
     * Reset structures PlantUML interpreter.
     */
    void reset();
}
