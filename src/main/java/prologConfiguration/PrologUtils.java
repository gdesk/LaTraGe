package prologConfiguration;


import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;

public interface PrologUtils {

    Prolog getEngine();
    SolveInfo solveGoal(String goal);
    boolean isSuccess(String goal);
}
