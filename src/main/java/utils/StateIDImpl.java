package utils;

public class StateIDImpl implements StateID {

    private static StateIDImpl instance =null;

    private int counter = -1;

    public static StateID getInstance() {
        if(instance ==null)
            instance = new StateIDImpl();
        return instance;
    }

    @Override
    public String createStateID(){
        counter++;
        return "s"+ counter;
    }
}
