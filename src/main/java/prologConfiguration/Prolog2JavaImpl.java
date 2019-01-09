package prologConfiguration;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Prolog;
import alice.tuprolog.Theory;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * This class manages the interaction beetween Prolog and Java
 */
public class Prolog2JavaImpl implements Prolog2Java {

    private Theory basicTheory;
    private Prolog engine;

    public  Prolog2JavaImpl(final String filepath) throws IOException {
        engine = PrologConfig.engine;
        basicTheory = new Theory(new FileInputStream(filepath));

    }

    @Override
    public void addTheory(final String theoryToAdd) throws InvalidTheoryException {
        Theory newTheory = new Theory(theoryToAdd);
        System.out.println(engine);
        engine.clearTheory();
        basicTheory.append(newTheory);
        engine.setTheory(basicTheory);
        System.out.println(engine.getTheory());

    }

    @Override
    public void setTheory(final String newFileTheory) throws InvalidTheoryException, IOException {
        Theory theory = new Theory(new FileInputStream(newFileTheory));
        engine.setTheory(theory);
    }
}
