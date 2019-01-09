package prologConfiguration;

import alice.tuprolog.*;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * This class implements Java2Prolog
 */
public class Java2PrologImpl implements Java2Prolog {

    private final static String MALFORMED_GOAL_MESSAGE = "ERROR - Malformed goal /n";
    private Prolog engine;

    public Java2PrologImpl(){
        engine = PrologConfig.engine;
    }

    @Override
    public Prolog getEngine(){
        return engine;
    }

    @Override
    public SolveInfo solveGoal(final String goal) {
        SolveInfo solution = null;
        try {
            solution =  engine.solve(goal);
        } catch (MalformedGoalException e) {
            System.out.println(MALFORMED_GOAL_MESSAGE);
        }
        return solution;
    }
}