package main.java.com.panda.Dataframe;

import java.util.ArrayList;
import java.util.HashMap;

public class DataCol <T> {
    String label;
    HashMap<String, T> content;

    //initialisation avec indices
    public DataCol(String lbl, ArrayList<T> val, ArrayList<String> ind){
        this.label = lbl;
        //map <- (ind, val)
    }

    //initialisation sans indices
    public DataCol(String lbl, ArrayList<T> val){
        this.label = lbl;
        //map <- (ind, val)
    }

//======================================
//          Getter and Setter
// ======================================

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}