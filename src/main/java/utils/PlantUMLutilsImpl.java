package utils;

import net.sourceforge.plantuml.SourceStringReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PlantUMLutilsImpl implements PlantUMLutils {

    private PlantUMLInterpreter plantUMLinterpreter  = new PlantUMLInterpreterImpl();

    @Override
    public void generateImage() throws IOException {
        StringBuilder plantUmlSource = plantUMLinterpreter.createPlantUML();
        System.out.println("PlantUML: " + plantUmlSource.toString());
        SourceStringReader reader = new SourceStringReader(plantUmlSource.toString());
        FileOutputStream output = new FileOutputStream(new File("LTSimage.png"));
        reader.generateImage(output);
        plantUMLinterpreter.reset();
    }
}
