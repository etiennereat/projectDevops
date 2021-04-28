package com.panda.datacol;

import java.util.ArrayList;
import java.util.Map;

/**
 * DataColumn of Integers.
 */
public class StringDataCol extends AbstractDataCol<String> {

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
        StringDataCol newDataCol = new StringDataCol();
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
        StringDataCol newDataCol = new StringDataCol();
        selectRowsInto(indexes, newDataCol);
        return newDataCol;
    }

    /**
     * return shortest string of the datacol
     * @return shortest string or null if the col is empty
     */
    protected String min() {
        int max = Integer.MAX_VALUE;
        String save = null;
        for(Map.Entry row :data.entrySet()){
            String value = (String)row.getValue();
            if(value != null ) {
                int size = value.length();
                if (max > size) {
                    save = value;
                    max = size;
                }
            }
        }
        return save;
    }

    /**
     * return longest string of the datacol
     * @return longest string or null if the col is empty
     */
    protected String max() {
        int max = -1;
        String save = null;
        for(Map.Entry row :data.entrySet()){
            String value = (String)row.getValue();
            if(value != null ) {
                int size = value.length();
                if (max < size) {
                    save = value;
                    max = size;
                }
            }
        }
        return save;
    }
}