package utils;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PlantUMLutilsImpl implements PlantUMLutils {

    private PlantUMLinterpreter plantUMLinterpreter  = new PlantUMLinterpreterImpl();

    @Override
    public void generateImage() throws IOException {
        StringBuilder plantUmlSource = plantUMLinterpreter.createPlantUML();
        System.out.println("PlantUML: " + plantUmlSource.toString());
        SourceStringReader reader = new SourceStringReader(plantUmlSource.toString());
        FileOutputStream output = new FileOutputStream(new File("LTSimage.png"));
        reader.generateImage(output);

    }
}
