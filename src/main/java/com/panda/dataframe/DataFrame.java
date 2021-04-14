package com.panda.dataframe;

import java.util.ArrayList;
import java.util.HashMap;

public class DataFrame {

    ArrayList<String> labels;
    ArrayList<String> indexes;
    HashMap<String,DataCol> table;

    public DataFrame(){
        this.labels = new ArrayList<String>();
        this.table = new HashMap<String,DataCol>();
        this.indexes = new ArrayList<String>();

    }

    public boolean addCol(String label,DataCol dl){
        if(!table.containsKey(label)){
            this.labels.add(label);
            this.table.put(label,dl);
            updateIndexes(dl.getIndexes());
            return true;
        }
        return false;
    }

    public boolean addCol(DataCol dl){
        return addCol(Integer.toString(dl.hashCode()),dl);
    }

    private void updateIndexes(ArrayList<String> indexes){
        //Todo
    }

    public boolean removeCol(String label){
        //Todo
        return false;
    }

    public boolean removeRow(String index){
        //Todo
        return false;
    }

    public DataCol getColsCount(String label){
        //Todo
        return null;
    }

    public int getColsCount(){
        return table.size();
    }

    public ArrayList<String> getIndexes(){
        //Todo
        return null;
    }

    public ArrayList<String> getLabels(){
        return (ArrayList<String>) labels.clone();
    }

    public void showAll(){
        //Todo
    }

    public void showCol(String label){
        //Todo
    }

    public void showRow(String index){
        //Todo
    }
}