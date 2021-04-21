package com.panda.dataframe;

import com.panda.datacol.DataCol;
import com.panda.datacol.DoubleDataCol;
import com.panda.datacol.IntegerDataCol;
import com.panda.datacol.BooleanDataCol;
import com.panda.datacol.StringDataCol;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Core data structure, grouping DataCols.
 * <p>
 * Supports basic operations over rows and columns.
 *
 * @todo several more complex constructors
 * @todo show, head, tail methods
 * @todo other interesting features to come...
 */
public class DataFrame {

    protected ArrayList<String> labels;
    protected ArrayList<String> indexes;
    protected HashMap<String, DataCol> table;

    public DataFrame() {
        this.labels = new ArrayList<>();
        this.table = new HashMap<>();
        this.indexes = new ArrayList<>();
    }

    /**
     * Adds a column if label is not already taken.
     *
     * @param label  - new column's name
     * @param column - column's data
     * @return status of add; true - column added; false - otherwise.
     */
    public boolean addCol(String label, DataCol column) {
        if (!table.containsKey(label)) {
            this.labels.add(label);
            this.table.put(label, column);
            addNewIndexes(column.getIndexes());
            return true;
        }
        return false;
    }

    /**
     * Adds a column with a automatically generated label.
     *
     * @param column - column's data
     * @return status of add; true - column added; false - otherwise.
     */
    public boolean addCol(DataCol column) {
        return addCol(Integer.toString(column.hashCode()), column);
    }

    /**
     * For all indexes in the argument, if it's a new index - adds it to the object's
     * indexes attribute. Update indexes in all the columns.
     *
     * @param indexes - list of indexes to be added if not already present
     */
    protected void addNewIndexes(ArrayList<String> indexes) {
        ArrayList<String> newIndexes = new ArrayList<>();

        for (String index : indexes) {
            if (!this.indexes.contains(index)) {
                newIndexes.add(index);
            }
        }

        this.indexes.addAll(newIndexes);

        for (String label : labels) {
            table.get(label).updateIndexes(this.indexes);
        }
    }

    /**
     * Removes column under provided label. Both label and data is removed.
     *
     * @param label - label of the column to be removed.
     * @return status of removal.
     */
    public boolean removeCol(String label) {
        if (labels.contains(label)) {
            labels.remove(label);
            table.remove(label);
            return true;
        }
        return false;
    }

    /**
     * Removes a row from all DataCols of the DataFrame. Both indexes and
     * associated data are removed.
     *
     * @param index - index of the row to be removed.
     * @return status of removal.
     */
    public boolean removeRow(String index) {
        if (indexes.contains(index)) {
            for (String label : table.keySet()) {
                table.get(label).removeIndex(index);
            }
            indexes.remove(index);
            return true;
        }
        return false;
    }

    /**
     * Get the number of columns.
     *
     * @return column's count
     */
    public int getColsCount() {
        return table.size();
    }

    /**
     * Get the indexes of the DataFrame.
     *
     * @return array of indexes
     */
    public ArrayList<String> getIndexes() {
        return new ArrayList<>(indexes);
    }

    /**
     * Get the labels of DataFrame's columns.
     *
     * @return array of labels
     */
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
     * Prints the entire DataFrame
     */
    public void show() {
        for(String label : getLabels()){
            System.out.print(label + " ");
        }
        System.out.println();
        for(String index : getIndexes()){
            show(index);
        }
    }

    /**
     * Prints the first n colums of the DataFrame
     *
     * @param n - Integer
     */
    public void head(int n){
        //Todo
    }

    /**
     * Prints the last n colums of the DataFrame
     *
     * @param n - Integer
     */
    public void tail(int n){
        //Todo
    }

    /**
     * Prints the values of each colums for the index
     *
     * @param index - String in indexes to be printed
     */
    public void show(String index) {
        System.out.print(index + " : " + format(index) + "\n");
    }

    /**
     * Format the line to print
     *
     * @param index - String in indexes to be formated
     * @return line - Formated String
     */
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
