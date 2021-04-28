package com.panda.datacol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
     * Sorts the data column (attribute data) by alphabetical order
     * Replaces original data, doesn't return anything
     */
    @Override
    public void sortByValue() {
        HashMap<String, String> mapRes= new HashMap<>();
        List<String> keysList = new ArrayList(this.data.keySet());
        List<String> valueList = new ArrayList(this.data.values());
        Collections.sort(valueList);

        for(int i=0; i < keysList.size(); i++){
            mapRes.put(keysList.get(i), valueList.get(i));
        }
        this.data = mapRes;
    }

    /**
     * Returns whether the boolean data column is sorted alphabetically
     * @return a boolean indicating whether the hashmap's values are sorted
     */
    @Override
    public boolean isSorted() {
        List<String> valueList = new ArrayList(this.data.values());
        List<String> sortedList = new ArrayList<>(valueList);
        Collections.sort(sortedList);
        for(int i=0; i<sortedList.size(); i++){
            if( !sortedList.get(i).equals(valueList.get(i)) ){
                return false;
            }
        }
        return true;
    }
}