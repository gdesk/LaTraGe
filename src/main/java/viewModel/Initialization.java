package viewModel;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.NoMoreSolutionException;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.UnknownVarException;

import java.io.IOException;

/**
 * This interface manages the initial configuration to the computing the entire LTS.
 */
public interface Initialization {

    /**
     * Creates the root of state diagram to compute the entire diagram
     *
     * @param input the process algebra to compute
     */
    void start(String input);

    /**
     * reset to compute another process algebra
     */
    void reset();
}
