package com.panda.datacol;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * DataColumn of Integers.
 */
public class IntegerDataCol extends DataCol {

    protected HashMap<String, Integer> data;

    /**
     * Default constructor. Initializes data structures and sets the
     * type of the column.
     */
    public IntegerDataCol() {
        type = ColType.INTEGER;
        indexes = new ArrayList<>();
        data = new HashMap<>();
    }

    /**
     * Constructor from value.
     * <p>
     * Uses default indexes from 0 to values.length - 1.
     *
     * @param values - array of cell values
     */
    public IntegerDataCol(int[] values) {
        this();

        // fill in data
        for (int i = 0; i < values.length; i++) {
            add(values[i], String.valueOf(i));
        }
    }


    /**
     * Constructor from values and indexes. The number of
     * elements in the two input arrays must be equal, otherwise
     * the minimum of arrays' lengths items are inserted.
     *
     * @param values  - array of cell values
     * @param indexes - array of indexes
     */
    public IntegerDataCol(int[] values, String[] indexes) {
        this();

        // get the min length
        int elementsCount = Math.min(values.length, indexes.length);

        // fin in data
        for (int i = 0; i < elementsCount; i++) {
            add(values[i], indexes[i]);
        }
    }

    /**
     * Adds a value to an auto-generated index.
     *
     * @param value - value to be added
     */
    public void add(int value) {
        int hashCode = Integer.valueOf(value).hashCode();
        String index = String.valueOf(hashCode);

        if (!data.containsKey(index)) {
            indexes.add(index);
        }

        data.put(index, value);
    }

    /**
     * Adds a value with a specified index.
     * <p>
     * If index is already present in the column, associated value is updated.
     *
     * @param value - value to be added
     * @param index - associated index to the value
     */
    public void add(int value, String index) {
        if (!data.containsKey(index)) {
            indexes.add(index);
        }

        data.put(index, value);
    }

    /**
     * Returns a value at a specific index. Returns null if no index found.
     *
     * @param index - cell's index
     * @return cell's value
     */
    public Integer get(String index) {
        if (data.containsKey(index)) {
            return data.get(index);
        } else {
            return null;
        }
    }

    /**
     * Prints the items of the column.
     */
    public void show() {
        if (indexes.size() == 0) {
            System.out.println("Empty column");
            return;
        }

        for (int i = 0; i < indexes.size(); i++) {
            String value = "NaN";

            if (data.containsKey(indexes.get(i))) {
                value = data.get(indexes.get(i)).toString();
            }

            System.out.printf("%s%s", value, (i != indexes.size() - 1 ? " " : ""));
        }

        System.out.println();
    }

    /**
     * Removes the cell corresponding to the index. If such an index does
     * not exist, returns false. On successful removal - returns true and
     * keeps the index.
     *
     * @param index - index of the cell to be removed
     * @return cell removal status
     */
    public boolean remove(String index) {
        if (data.containsKey(index)) {
            data.put(index, null);
            return true;
        }

        return false;
    }
}