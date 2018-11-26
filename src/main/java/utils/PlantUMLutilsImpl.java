package utils;

import net.sourceforge.plantuml.SourceStringReader;

import java.io.File;
import java.io.IOException;

public class PlantUMLutilsImpl implements PlantUMLutils {

    private PlantUMLinterpreter plantUMLinterpreter  = new PlantUMLinterpreterImpl();

    @Override
    public void generateImage(){
        File png = new File("LTSimage.png");
        String source = plantUMLinterpreter.createPlantUML() ;

        SourceStringReader reader = new SourceStringReader(source);
        try {
            reader.generateImage(png);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
