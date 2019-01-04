package utils;

import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.code.Transcoder;
import net.sourceforge.plantuml.code.TranscoderUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PlantUMLutilsImpl implements PlantUMLutils {

    private PlantUMLInterpreter plantUMLinterpreter  = new PlantUMLInterpreterImpl();
    private static String httpURL;

    @Override
    public void generateImage() throws IOException {
        StringBuilder plantUmlSource = plantUMLinterpreter.createPlantUML();
        System.out.println("PlantUML: " + plantUmlSource.toString());
        SourceStringReader reader = new SourceStringReader(plantUmlSource.toString());
        FileOutputStream output = new FileOutputStream(new File("LTSimage.png"));
        reader.generateImage(output);
        Transcoder t = TranscoderUtil.getDefaultTranscoder();
        String url = t.encode(plantUmlSource.toString());
        httpURL = "http://www.plantuml.com/plantuml/uml/" + url;
        plantUMLinterpreter.reset();
    }

    @Override
    public String getHttpURL() {
        return  httpURL;
    }
}
