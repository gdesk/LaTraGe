package prologConfiguration;

import alice.tuprolog.*;

import java.io.FileInputStream;
import java.io.IOException;

public class Java2Java2PrologImpl implements Java2Prolog {

    private final Theory theory;
    private Prolog engine;

    public Java2Java2PrologImpl(final String fileName) throws InvalidTheoryException, IOException {
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
            System.out.println("ERROR - Malformed goal /n");
        }
        return solution;
    }
}