package com.panda.datacol;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * DataColumn of Integers.
 */
public class DoubleDataCol extends DataCol<Double> {

    /**
     * Default constructor. Initializes data structures and sets the
     * type of the column.
     */
    public DoubleDataCol() {
        super(ColType.DOUBLE);
    }

    /**
     * Constructor from value.
     * <p>
     * Uses default indexes from 0 to values.length - 1.
     *
     * @param values - array of cell values
     */
    public DoubleDataCol(double[] values) {
        super(ColType.DOUBLE);

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
    public DoubleDataCol(double[] values, String[] indexes) {
        super(ColType.DOUBLE);

        // get the min length
        int elementsCount = Math.min(values.length, indexes.length);

        // fin in data
        for (int i = 0; i < elementsCount; i++) {
            add(values[i], indexes[i]);
        }
    }
}