package prologConfiguration;

import alice.tuprolog.MalformedGoalException;
import alice.tuprolog.SolveInfo;

public interface PrologUtils {
    SolveInfo solveGoal(String goal);
    boolean isSuccess(String goal);
}
