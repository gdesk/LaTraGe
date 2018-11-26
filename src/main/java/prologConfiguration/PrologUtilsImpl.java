package prologConfiguration;

import alice.tuprolog.*;

import java.io.FileInputStream;
import java.io.IOException;

public class PrologUtilsImpl implements PrologUtils {

    private final Theory theory;
    private Prolog engine;

    public PrologUtilsImpl(final String fileName) throws InvalidTheoryException, IOException {
        this.theory = new Theory(new FileInputStream(fileName));
        this.engine = new Prolog();
        this.engine.setTheory(theory);
    }

    @Override
    public Prolog getEngine() {
        return engine;
    }

    @Override
    public SolveInfo solveGoal(final String goal) {

        SolveInfo solution = null;
        try {
            solution =  engine.solve(goal);
        } catch (MalformedGoalException e) {
            System.out.println("ERROR - Malformed goal");
        }
        return solution;
    }

    @Override
    public boolean isSuccess(final String goal){

        Boolean isSuccess = false;
        try {
             isSuccess = engine.solve(goal).isSuccess();
        } catch (MalformedGoalException e) {
            System.out.println("ERROR - Malformed goal");
        }
        return isSuccess;
    }
}