package com.panda.datacol;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;

/**
 * DataColumn of Integers.
 */
public class BooleanDataCol extends AbstractDataCol<Boolean> {

    /**
     * Default constructor. Initializes data structures and sets the
     * type of the column.
     */
    public BooleanDataCol() {
        super(ColType.BOOLEAN);
    }

    /**
     * Constructor from value.
     * <p>
     * Uses default indexes from 0 to values.length - 1.
     *
     * @param values - array of cell values
     */
    public BooleanDataCol(boolean[] values) {
        super(ColType.BOOLEAN);

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
    public BooleanDataCol(boolean[] values, String[] indexes) {
        super(ColType.BOOLEAN);

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
        BooleanDataCol newDataCol = new BooleanDataCol();
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
        BooleanDataCol newDataCol = new BooleanDataCol();
        selectRowsInto(indexes, newDataCol);
        return newDataCol;
    }


}