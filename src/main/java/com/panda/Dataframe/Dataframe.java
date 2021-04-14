package com.panda.Dataframe;

import com.panda.Dataframe.DataCol;

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