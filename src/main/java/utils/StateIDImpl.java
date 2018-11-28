package utils;

public class StateIDImpl implements StateID {

    private static StateIDImpl istance=null; //riferimento all' istanza

    private int counter = 0;


    public StateIDImpl(){}

    public static StateID getIstance() {
        if(istance==null)
            istance = new StateIDImpl();
        return istance;
    }

    @Override
    public String createStateID(){
        counter++;
        return "s"+ counter;
    }
}
