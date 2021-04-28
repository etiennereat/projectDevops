package com.panda.datacol;

import java.util.*;

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

    /**
     * Sorts the data column (attribute data) by value from lowest integer to highest
     * Replaces original data, doesn't return anything
     */
    @Override
    public void sortByValue() {
        HashMap passedMap = this.data;
        List<Integer> mapValues = new ArrayList(passedMap.values());
        List<String> mapKeys = new ArrayList(passedMap.keySet());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        HashMap<String, Integer> sortedMap = new LinkedHashMap<>();

        Iterator<Integer> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            int val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                int comp1 = (int) passedMap.get(key);

                if (val==comp1) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        this.data = sortedMap;
    }

    /**
     * Returns whether the integer data column is sorted
     * @return a boolean indicating whether the hashmap's values are sorted
     */
    @Override
    public boolean isSorted() {
        if(this.data.isEmpty()){return true;}
        int current, previous = 0;
        boolean isFirst = true;

        for (Map.Entry mapentry : this.data.entrySet()){
            if(isFirst){
                previous = (int) (mapentry.getValue());
                isFirst = false;
            }else{
                current = (int) (mapentry.getValue());
                if(previous > current){
                    return false;
                }
                previous = current;
            }
        }
        return true;
    }
}