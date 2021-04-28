package com.panda.datacol;

import java.util.*;

/**
 * DataColumn of Integers.
 */
public class DoubleDataCol extends AbstractDataCol<Double> {

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
        DoubleDataCol newDataCol = new DoubleDataCol();
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
        DoubleDataCol newDataCol = new DoubleDataCol();
        selectRowsInto(indexes, newDataCol);
        return newDataCol;
    }

    @Override
    public void sortByValue() {
        HashMap passedMap = this.data;
        List<Double> mapValues = new ArrayList(passedMap.values());
        List<String> mapKeys = new ArrayList(passedMap.keySet());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        HashMap<String, Double> sortedMap = new LinkedHashMap<>();

        Iterator<Double> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Double val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                Double comp1 = (double) passedMap.get(key);

                if (val.equals(comp1)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        this.data = sortedMap;
    }

    @Override
    public boolean isSorted() {
        if(this.data.isEmpty()){return true;}
        double current, previous = 0;
        boolean isFirst = true;

        for (Map.Entry mapentry : this.data.entrySet()){
            if(isFirst){
                previous = (double) (mapentry.getValue());
                isFirst = false;
            }else{
                current = (double) (mapentry.getValue());
                if(previous > current){
                    return false;
                }
                previous = current;
            }
        }
        return true;
    }

}