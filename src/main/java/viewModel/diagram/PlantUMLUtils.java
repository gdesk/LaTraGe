package viewModel.diagram;

import java.io.IOException;

public interface PlantUMLUtils {

    String generateImage() throws IOException;

    String getHttpURL() ;

}
