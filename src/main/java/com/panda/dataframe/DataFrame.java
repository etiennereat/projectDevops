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
