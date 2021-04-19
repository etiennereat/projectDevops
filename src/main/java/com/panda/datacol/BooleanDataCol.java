package com.panda.datacol;

/**
 * DataColumn of Integers.
 */
public class BooleanDataCol extends DataCol<Boolean> {

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
}