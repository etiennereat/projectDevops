package com.panda.datacol;

import java.util.ArrayList;

/**
 * DataColumn of Integers.
 */
public class IntegerDataCol extends AbstractDataCol<Integer> {

    /**
     * Default constructor. Initializes data structures and sets the
     * type of the column.
     */
    public IntegerDataCol() {
        super(ColType.INTEGER);
    }

    /**
     * Constructor from value.
     * <p>
     * Uses default indexes from 0 to values.length - 1.
     *
     * @param values - array of cell values
     */
    public IntegerDataCol(int[] values) {
        super(ColType.INTEGER);

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
        super(ColType.INTEGER);

        // get the min length
        int elementsCount = Math.min(values.length, indexes.length);

        // fill in data
        for (int i = 0; i < elementsCount; i++) {
            add(values[i], indexes[i]);
        }
    }


    /**
     * Returns a new DataCol composed of the rows between the
     * specified from and to indexes inclusive.
     * <p>
     * If one of the two indexes does not exist - returns empty DC. If from is
     * placed below the index to - returns empty DC. Otherwise, return the
     * DataCol containing the required selection of rows.
     *
     * @param from - starting index
     * @param to   - ending index
     * @return the new DataCol
     */
    @Override
    public DataCol selectRows(String from, String to) {
        IntegerDataCol newDataCol = new IntegerDataCol();
        selectRowsInto(from, to, newDataCol);
        return newDataCol;
    }

    /**
     * Returns a new DataCol composed of the rows associated to
     * the specified indexes. If indexes are not valid or null, returns
     * an empty DC.
     *
     * @param indexes - list of indexes
     * @return the new DataCol
     */
    @Override
    public DataCol selectRows(ArrayList<String> indexes) {
        IntegerDataCol newDataCol = new IntegerDataCol();
        selectRowsInto(indexes, newDataCol);
        return newDataCol;
    }

    @Override
    public void sortByValue() {

    }

    @Override
    public boolean isSorted() {
        return false;
    }
}