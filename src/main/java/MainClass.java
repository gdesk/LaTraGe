import prologConfiguration.PrologConfig;
import view.View;

/**
 * The main class of the system.
 */
public class MainClass {
    private final static String PROLOG_PATH = "src/main/prolog/LTSOperators.pl";

    public static void main(String [ ] args) {
        try {
            new PrologConfig(PROLOG_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new View();
    }
}