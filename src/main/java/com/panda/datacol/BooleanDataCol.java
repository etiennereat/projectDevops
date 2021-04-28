package com.panda.datacol;

import java.util.*;

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

    /**
     * Sorts the data column (attribute data) by value, with true first and then false
     * Replaces original data, doesn't return anything
     */
    @Override
    public void sortByValue() {
        HashMap<String, Boolean> mapRes= new HashMap<>();
        List<String> keysList = new ArrayList(this.data.keySet());
        int i = 0;

        for (Map.Entry mapentry : this.data.entrySet()) {
            boolean val = (boolean) (mapentry.getValue());
            if (val) {
                String key = keysList.get(i);
                i++;
                mapRes.put(key, val);
            }
        }

        for (Map.Entry mapentry : this.data.entrySet()) {
            boolean val = (boolean) (mapentry.getValue());
            if (!val) {
                String key = keysList.get(i);
                i++;
                mapRes.put(key, val);
            }
        }
        this.data = mapRes;
    }

    /**
     * Returns whether the boolean data column is sorted (only true then only false)
     * @return a boolean indicating whether the hashmap's values are sorted
     */
    @Override
    public boolean isSorted() {
        boolean isFirst = true;
        boolean current, previous = true;
        for (Map.Entry mapentry : this.data.entrySet()){
            if(isFirst){
                previous = (boolean) (mapentry.getValue());
                isFirst = false;
            }else{
                current = (boolean) (mapentry.getValue());
                if(current && !previous){
                    return false;
                }
                previous = current;
            }
        }
        return true;
    }
}