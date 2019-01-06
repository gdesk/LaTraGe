package viewModel.diagram;

import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.code.Transcoder;
import net.sourceforge.plantuml.code.TranscoderUtil;
import java.io.FileOutputStream;
import java.io.IOException;

public class PlantUMLUtilsImpl implements PlantUMLUtils {

    private PlantUMLInterpreter plantUMLinterpreter  = new PlantUMLInterpreterImpl();
    private static String httpURL;

    @Override
    public String generateImage() throws IOException {
        StringBuilder plantUmlSource = plantUMLinterpreter.createPlantUML();
        System.out.println("PlantUML: " + plantUmlSource.toString());
        SourceStringReader reader = new SourceStringReader(plantUmlSource.toString());
        FileOutputStream output = new FileOutputStream("LTSImage.png");
        reader.generateImage(output);
        Transcoder t = TranscoderUtil.getDefaultTranscoder();
        String url = t.encode(plantUmlSource.toString());
        httpURL = "";
        plantUMLinterpreter.reset();
        return "http://www.plantuml.com/plantuml/uml/" + url;
    }

    @Override
    public String getHttpURL() {
        return  httpURL;
    }
}
