package com.panda.dataframe;

import com.panda.datacol.DataCol;

import java.util.ArrayList;
import java.util.HashMap;

public class DataFrame {

    protected ArrayList<String> labels;
    protected ArrayList<String> indexes;
    protected HashMap<String, DataCol> table;

    public DataFrame() {
        this.labels = new ArrayList<>();
        this.table = new HashMap<>();
        this.indexes = new ArrayList<>();
    }

    public boolean addCol(String label, DataCol column) {
        if (!table.containsKey(label)) {
            this.labels.add(label);
            this.table.put(label, column);
            updateIndexes(column.getIndexes());
            return true;
        }
        return false;
    }

    public boolean addCol(DataCol column) {
        return addCol(Integer.toString(column.hashCode()), column);
    }

    /**
     * @param indexes
     * @todo update indexes / data in all columns
     */
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
            for (String key : table.keySet()) {
                table.get(key).remove(index);
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
        return new ArrayList<>(indexes);
    }

    public ArrayList<String> getLabels() {
        return new ArrayList<>(labels);
    }

    /**
     * Access data frame column by label.
     *
     * @param label - column's label
     * @return reference to the data column object
     */
    public DataCol col(String label) {
        return table.get(label);
    }

    /**
     * @todo
     */
    public void show() {
        System.out.println("Table");
    }

    /**
     * @param index
     * @todo
     */
    public void show(String index) {
        System.out.printf("Table's '%s' row\n", index);
    }

}
