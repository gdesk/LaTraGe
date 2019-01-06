package viewModel;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.NoMoreSolutionException;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.UnknownVarException;

import java.io.IOException;

public interface LTSComputing {

    /**
     * This method computes the entire LTS diagram for each computing turn.
     * For the computing, we use the prolog rule.
     *
     * @throws UnknownVarException incorrect goal: unknown variable
     * @throws NoSolutionException incorrect goal: no solution
     * @throws NoMoreSolutionException there isn't more solution
     */
    void computeState() throws UnknownVarException, NoSolutionException, NoMoreSolutionException;

    /**
     * reset to compute another process algebra
     *
     * @throws IOException IOException
     * @throws InvalidTheoryException InvalidTheoryException
     */
    void reset() throws IOException, InvalidTheoryException;
}
