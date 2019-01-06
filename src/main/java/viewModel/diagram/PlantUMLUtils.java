package viewModel.diagram;

import java.io.IOException;

/**
 * The interface produces the image of entire LTS diagram.
 *
 */
public interface PlantUMLUtils {

    /**
     *  Generates the image that represents the graphical visualization of images and the link to PlantUML online editor.
     *
     * @return URL of PlantUML online editor for the specific diagram
     *
     * @throws IOException file exception
     */
    String generateImage() throws IOException;

}
