package viewModel.diagram;

import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.code.Transcoder;
import net.sourceforge.plantuml.code.TranscoderUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The class produces the image of entire LTS diagram.
 */
public class PlantUMLUtilsImpl implements PlantUMLUtils {

    private PlantUMLInterpreter plantUMLinterpreter  = new PlantUMLInterpreterImpl();

    @Override
    public String generateImage(String path) throws IOException {
        StringBuilder plantUmlSource = plantUMLinterpreter.createPlantUML();
        System.out.println("PlantUML: " + plantUmlSource.toString());
        SourceStringReader reader = new SourceStringReader(plantUmlSource.toString());
        FileOutputStream output = new FileOutputStream(new File(path));
        reader.generateImage(output);
        Transcoder t = TranscoderUtil.getDefaultTranscoder();
        String url = t.encode(plantUmlSource.toString());
        plantUMLinterpreter.reset();
        return "http://www.plantuml.com/plantuml/uml/" + url;
    }
}
