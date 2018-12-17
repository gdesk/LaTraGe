package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */

//TODO: evaluate if needed
public class ListManagement {

    /**
     * Convert the scala list to prolog list.
     * @param javaList list written in scala
     *
     * @return list in prolog, as a string to pass as param in the goal.
     */
    private String javaToPrologList(List<String> javaList){
        String outputList = "[";
        for (String elem : javaList){
            outputList = outputList.concat(elem+",");
        }
        outputList = outputList.substring(0, outputList.length()-2);
        outputList = outputList.concat("]");
        return outputList;
    }

    /**
     * Convert the prolog list to scala list.
     *
     * @param prologList list written in prolog
     * @return list written in scala.
     */
    private List<String> prologToJavaList(final String prologList){
        List<String> javaList = new ArrayList<>();
        String temp = prologList.replace("[","").replace("]","");
        Collections.addAll( javaList, temp.split("dot"));
        return javaList;
    }
}
