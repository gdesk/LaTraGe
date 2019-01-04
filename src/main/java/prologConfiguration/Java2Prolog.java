package prologConfiguration;

import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;

/**
 * This interface is utils to managed prolog file
 */

public interface Java2Prolog {

    /**
     * Get prolog engine
     * @return prolog engine
     */
    Prolog getEngine();

    /**
     * Solve prolog goal and value all solution
     * @param goal input goal
     * @return all solution
     */
    SolveInfo solveGoal(String goal);
}
