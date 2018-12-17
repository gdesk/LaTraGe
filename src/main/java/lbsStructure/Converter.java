package lbsStructure;

import alice.tuprolog.NoMoreSolutionException;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.UnknownVarException;

import java.util.List;

/**
 * This interface is utils to convert input sequence operator process into a list and reverse
 */

public interface Converter {

    /**
     * Convert input sequence operator in a list
     * @param input input sequence operator
     * @param inputCompute is recursive compute output
     * @return a list
     * @throws UnknownVarException manages prolog UnknownVarException
     * @throws NoSolutionException manages prolog NoSolutionException
     * @throws NoMoreSolutionException manages prolog NoMoreSolutionException
     */
    String inputConverter(String input, String inputCompute) throws UnknownVarException, NoSolutionException, NoMoreSolutionException;

    /**
     * Get input list for process  input rule
     * @return input list
     */
    List<String> getInputList();

    /**
     * Convert input list in a sequence operator
     * @param prologOutput a list
     * @return a sequence operator
     * @throws UnknownVarException manages prolog UnknownVarException
     * @throws NoSolutionException manages prolog NoSolutionException
     * @throws NoMoreSolutionException manages prolog NoMoreSolutionException
     */
    String outputConverter(String prologOutput) throws UnknownVarException, NoSolutionException, NoMoreSolutionException;

    /**
     * Utils for manage diagram state
     * @param index state number
     */
    void reInitialization(int index);
}
