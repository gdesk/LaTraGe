package prologConfiguration;

import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;

/**
 * This interface is utils to managed Prolog file
 */
public interface Java2Prolog {

    /**
     * Get Prolog engine
     * @return Prolog engine
     */
    Prolog getEngine();

    /**
     * Solve Prolog goal and value all solution
     * @param goal input goal
     * @return all solution
     */
    SolveInfo solveGoal(String goal);
}
