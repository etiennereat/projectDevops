package com.panda.dataframe;

import com.panda.datacol.DataCol;

import java.util.ArrayList;
import java.util.HashMap;

public class DataFrame {

    ArrayList<String> labels;
    ArrayList<String> indexes;
    HashMap<String, DataCol> table;

    public DataFrame() {
        this.labels = new ArrayList<>();
        this.table = new HashMap<>();
        this.indexes = new ArrayList<>();
    }

    public boolean addCol(String label, DataCol dl) {
        if (!table.containsKey(label)) {
            this.labels.add(label);
            this.table.put(label, dl);
            updateIndexes(dl.getIndexes());
            return true;
        }
        return false;
    }

    public boolean addCol(DataCol dl) {
        return addCol(Integer.toString(dl.hashCode()), dl);
    }

    private void updateIndexes(ArrayList<String> indexes) {
        //Todo
    }

    public boolean removeCol(String label) {
        //Todo
        return false;
    }

    public boolean removeRow(String index) {
        //Todo
        return false;
    }

    public int getColsCount() {
        return table.size();
    }

    public ArrayList<String> getIndexes() {
        return new ArrayList<>(indexes);
    }

    public ArrayList<String> getLabels() {
        return new ArrayList<>(labels);
    }

    public void show() {
        //Todo
    }


    public void show(String index) {
        //Todo
    }

}
