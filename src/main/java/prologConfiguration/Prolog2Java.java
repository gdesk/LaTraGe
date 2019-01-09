package prologConfiguration;

import alice.tuprolog.InvalidTheoryException;

import java.io.IOException;

/**
 * The interface manages the interaction beetween Prolog and Java
 */
public interface Prolog2Java {

    /**
     * This methods useful to modify the theory from java application, in particular add theory to prolog file.
     */
    void addTheory( String theoryToAdd) throws InvalidTheoryException;

    /**
     * This method set the right file prolog to modidy theory
     */
    void setTheory(String newFileTheory) throws InvalidTheoryException, IOException;
}
