package lbsStructure;

import alice.tuprolog.NoMoreSolutionException;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.UnknownVarException;

import java.util.List;

public interface Converter {
    String inputConverter(String input, String inputCompute) throws UnknownVarException, NoSolutionException, NoMoreSolutionException;

    List<String> getinputList();

    String outputConverter(String prologoutput) throws UnknownVarException, NoSolutionException, NoMoreSolutionException;

    void ReInit(int index);
}
