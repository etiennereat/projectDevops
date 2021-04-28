package com.panda.dataframe;

import com.panda.datacol.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.panda.utils.CSVFileManager.openCSV;
import static com.panda.utils.PaternMatcher.*;
import static java.util.Arrays.copyOfRange;


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

    public DataFrame(ArrayList<String> labels, ArrayList<DataCol> cols) {
        assert (labels.size() == cols.size());
        this.labels = new ArrayList<>();
        this.table = new HashMap<>();
        this.indexes = new ArrayList<>();
        for (int i = 0; i < labels.size(); i++) {
            addCol(labels.get(i), cols.get(i));
        }
    }

    public DataFrame(DataFrame otherframe) {
        this.labels = new ArrayList<>();
        this.table = new HashMap<>();
        this.indexes = new ArrayList<>();
        for (String label : otherframe.getLabels()) {
            addCol(label, otherframe.col(label));
        }
    }

    public DataFrame(String fileName) {
        this.labels = new ArrayList<>();
        this.table = new HashMap<>();
        this.indexes = new ArrayList<>();
        getDataFromFile(fileName);
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
     * Returns a new DataFrame composed of the columns between the
     * specified from and to labels inclusive.
     * <p>
     * If anyone of the two labels does not exist - returns empty DF. If from is placed
     * at right of column with label to - returns empty DF. Otherwise, returns the
     * DataFrame containing the required selection of columns.
     *
     * @param from - starting label
     * @param to   - ending label
     * @return the new DataFrame
     */
    public DataFrame selectCols(String from, String to) {
        int indexOfFrom = labels.indexOf(from);
        int indexOfTo = labels.indexOf(to);

        DataFrame newDataFrame = new DataFrame();

        if (indexOfFrom < 0 || indexOfTo < 0 || indexOfTo < indexOfFrom) {
            return newDataFrame;
        }

        for (int i = indexOfFrom; i <= indexOfTo; i++) {
            newDataFrame.addCol(labels.get(i), table.get(labels.get(i)));
        }

        return newDataFrame;
    }


    /**
     * Returns a new DataFrame composed of the columns associated to
     * the specified labels. Returns an empty DF for non-existent, null
     * labels.
     *
     * @param labels - list of labels
     * @return the new DataFrame
     */
    public DataFrame selectCols(ArrayList<String> labels) {
        DataFrame newDataFrame = new DataFrame();

        if (labels == null || labels.size() == 0) {
            return newDataFrame;
        }

        for (String label : labels) {
            if (table.containsKey(label)) {
                newDataFrame.addCol(label, table.get(label));
            }
        }

        return newDataFrame;
    }

    /**
     * Returns a new DataFrame composed from the rows between the
     * specified from and to indexes inclusive.
     * <p>
     * If one of the two indexes does not exist - returns empty DF. If from is placed
     * below the index to - returns empty DF. Otherwise, return the
     * DataFrame containing the required selection of rows.
     *
     * @param from - starting index
     * @param to   - ending index
     * @return the new DataFrame
     */
    public DataFrame selectRows(String from, String to) {
        DataFrame newDataFrame = new DataFrame();

        int indexOfFrom = indexes.indexOf(from);
        int indexOfTo = indexes.indexOf(to);

        if (indexOfFrom < 0 || indexOfFrom > indexOfTo) {
            return newDataFrame;
        }

        for (String label : labels) {
            newDataFrame.addCol(label, table.get(label).selectRows(from, to));
        }

        return newDataFrame;
    }


    /**
     * Returns a new DataFrame composed from the rows associated to
     * the specified indexes. If indexes' list is not valid - returns
     * an empty DataFrame.
     *
     * @param indexes - list of indexes
     * @return the new DataFrame
     */
    public DataFrame selectRows(ArrayList<String> indexes) {
        DataFrame newDataFrame = new DataFrame();

        if (indexes == null || indexes.size() == 0) {
            return newDataFrame;
        }

        for (String label : labels) {
            if (table.containsKey(label)) {
                newDataFrame.addCol(label, table.get(label).selectRows(indexes));
            }
        }

        return newDataFrame;
    }

    /**
     * Return the first n colums of the DataFrame (soft copy of the column)
     * If n <= 0 or n >= nb_column
     * return null;
     *
     * @param n - Integer
     * @return df - A new DataFrame with n the first column of this
     */
    public DataFrame head(int n){
        if (n <= 0 || n >= getColsCount()){return null;}
        DataFrame df = new DataFrame();
        int i = 0;
        String l;
        while(i < n){
            l = this.labels.get(i);
            df.addCol(l,table.get(l));
            i++;
        }
        return df;
    }

    /**
     * Return the last n colums of the DataFrame (soft copy of the column)
     * If n <= 0 or n >= nb_column
     * return null;
     *
     * @param n - Integer
     * @return df - A new DataFrame with n the last column of this
     */
    public DataFrame tail(int n){
        if (n <= 0 || n >= getColsCount()){return null;}
        DataFrame df = new DataFrame();
        int i = n;
        String l;
        while(i < getColsCount()){
            System.out.println(i);
            l = this.labels.get(i);
            df.addCol(l,table.get(l));
            i++;
        }
        return df;
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

    /**
     * Load data from CSV file
     *
     * @param path path of CSV file to build data frame
     */
    private void getDataFromFile(String path) {

        File fileCSV = openCSV(path);
        HashMap<String, String[]> data = new HashMap<>();
        ArrayList<ColType> listeType = new ArrayList<>();
        try {
            Scanner reader = new Scanner(fileCSV);
            String line = reader.nextLine();

            //sauvegarde des labels
            ArrayList<String> saveLabels = new ArrayList<>();
            for (String label : line.split(",")) {
                saveLabels.add(label);
                listeType.add(null);
            }

            //lecture des data  et determine types:
            while (reader.hasNextLine()) {
                line = reader.nextLine();
                String parser[] = line.split(",");
                String cells[] = new String[listeType.size()];
                for (int i = 0; i < listeType.size(); i++) {
                    if (i >= parser.length - 1) {
                        cells[i] = "Nan";
                    } else {
                        cells[i] = parser[i + 1];
                    }

                    //try to determine the type
                    ColType check_type = listeType.get(i);
                    if (check_type == null) {
                        if (isInteger(cells[i])) {
                            listeType.set(i, ColType.INTEGER);
                        } else if (isDouble(cells[i])) {
                            listeType.set(i, ColType.DOUBLE);
                        } else if (isBoolean(cells[i])) {
                            listeType.set(i, ColType.BOOLEAN);
                        } else if (!isEmptyCase(cells[i])) {
                            listeType.set(i, ColType.STRING);
                        }
                    } else switch (check_type) {
                        case INTEGER:
                            if (!isEmptyCase(cells[i])) {
                                if (!isInteger(cells[i])) {
                                    if (!isDouble(cells[i])) {
                                        listeType.set(i, ColType.STRING);
                                    } else {
                                        listeType.set(i, ColType.DOUBLE);
                                    }
                                }
                            }
                            break;
                        case DOUBLE:
                            if (!isEmptyCase(cells[i]) && !isDouble(cells[i])) {
                                listeType.set(i, ColType.STRING);
                            }
                            break;
                        case BOOLEAN:
                            if (!isEmptyCase(cells[i]) && !isBoolean(cells[i])) {
                                listeType.set(i, ColType.STRING);
                            }
                            break;
                        case STRING:
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + check_type);
                    }
                }
                data.put(parser[0], cells);
            }
            reader.close();
            AbstractDataCol col;
            for (int i = 0; i < listeType.size(); i++) {
                ColType type = listeType.get(i);
                if (type == null) {
                    listeType.set(i, ColType.STRING);
                    type = ColType.STRING;
                }
                switch (type) {
                    case INTEGER:
                        col = new IntegerDataCol();
                        break;
                    case DOUBLE:
                        col = new DoubleDataCol();
                        break;
                    case BOOLEAN:
                        col = new BooleanDataCol();
                        break;
                    case STRING:
                        col = new StringDataCol();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + type);
                }
                for (Map.Entry<String, String[]> row : data.entrySet()) {
                    col.add(row.getValue()[i], row.getKey());
                }
                addCol(saveLabels.get(i), col);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
