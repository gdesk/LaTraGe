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
     * @param path the local path for the to-be-generated image
     * @return URL of PlantUML online editor for the specific diagram
     *
     * @throws IOException file exception
     */
    String generateImage(String path) throws IOException;

}
