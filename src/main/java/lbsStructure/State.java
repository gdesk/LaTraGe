package lbsStructure;

import java.util.List;

public interface State {

    List<State> getAllStates();
    String getId();
    String getValueState();
    int getLevel();
}
