package com.panda.dataframe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataFrame {

    ArrayList<String> labels;
    ArrayList<String> indexes;
    HashMap<String, DataCol> table;

    public DataFrame() {
        this.labels = new ArrayList<String>();
        this.table = new HashMap<String, DataCol>();
        this.indexes = new ArrayList<String>();

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
        for (String index : indexes) {
            if (!this.indexes.contains(index)) {
                this.indexes.add(index);
            }
        }
    }

    public boolean removeCol(String label) {
        if (labels.contains(label)) {
            labels.remove(label);
            table.remove(label);
            return true;
        }
        return false;
    }

    public boolean removeRow(String index) {
        if (indexes.contains(index)) {
            for (Map.Entry<String, DataCol> entre : table.entrySet()) {
                entre.getValue().remove(index);
            }
            indexes.remove(index);
            return true;
        }
        return false;
    }

    public int getColsCount() {
        return table.size();
    }

    public ArrayList<String> getIndexes() {
        return (ArrayList<String>) indexes.clone();
    }

    public ArrayList<String> getLabels() {
        return (ArrayList<String>) labels.clone();
    }

    public void show() {
        for (Map.Entry<String, DataCol> entre : table.entrySet()) {
            entre.getValue().show();
        }
    }

    public void show(String index) {
        for (Map.Entry<String, DataCol> entre : table.entrySet()) {
            entre.getValue().show(index);
        }
    }

}
