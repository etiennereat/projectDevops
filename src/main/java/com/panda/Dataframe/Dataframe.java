package main.java.com.panda.Dataframe;

import java.util.ArrayList;

public class Dataframe {

    ArrayList<DataCol> dataList;

    public Dataframe(){
        this.dataList = new ArrayList<DataCol>();
    }

    public void addCol(DataCol dl){
        this.dataList.add(dl);
    }

    //TODO : affichage
}