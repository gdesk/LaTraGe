package prologConfiguration;

import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;

/**
 * This interface is useful to manage Prolog file
 */
public interface Java2Prolog {

    /**
     * Get Prolog Engine.
     *
     * @return the engine of entire system.
     */
    Prolog getEngine();

    /**
     * Solve Prolog goal and value all solution
     *
     * @param goal input goal
     * @return all solution
     */
    SolveInfo solveGoal(String goal);
}
