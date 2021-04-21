package com.panda.dataframe;

import com.panda.datacol.DataCol;
import com.panda.datacol.StringDataCol;
import com.panda.datacol.DoubleDataCol;
import com.panda.datacol.IntegerDataCol;
import com.panda.datacol.BooleanDataCol;


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
        for(String label : getLabels()){
            System.out.print(label + " ");
        }
        System.out.println();
        for(String index : getIndexes()){
            show(index);
        }
    }


    public void show(String index) {
        System.out.print(index + " : " + format(index) + "\n");
    }

    private String format(String index){
        String line = "";
        for(String label : labels){
            switch (table.get(label).getType()){
                case STRING :
                    line = line + String.valueOf(((StringDataCol)(table.get(label))).get(index)) + " ";
                    break;
                case DOUBLE :
                    line = line + String.valueOf(((DoubleDataCol)(table.get(label))).get(index)) + " ";
                    break;
                case INTEGER :
                    line = line + String.valueOf(((IntegerDataCol)(table.get(label))).get(index)) + " ";
                    break;
                case BOOLEAN :
                    line = line + String.valueOf(((BooleanDataCol)(table.get(label))).get(index)) + " ";
                    break;
                default :
                    break;
            }
        }
        return line;
    }

}
