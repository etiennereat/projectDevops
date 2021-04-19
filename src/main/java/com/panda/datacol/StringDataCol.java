package com.panda.datacol;

/**
 * DataColumn of Integers.
 */
public class StringDataCol extends DataCol<String> {

    /**
     * Default constructor. Initializes data structures and sets the
     * type of the column.
     */
    public StringDataCol() {
        super(ColType.STRING);
    }

    /**
     * Constructor from value.
     * <p>
     * Uses default indexes from 0 to values.length - 1.
     *
     * @param values - array of cell values
     */
    public StringDataCol(String[] values) {
        super(ColType.STRING);

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
    public StringDataCol(String[] values, String[] indexes) {
        super(ColType.STRING);

        // get the min length
        int elementsCount = Math.min(values.length, indexes.length);

        // fill in data
        for (int i = 0; i < elementsCount; i++) {
            add(values[i], indexes[i]);
        }
    }
}