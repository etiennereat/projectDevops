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

    /**
     * Sorts the data column (attribute data) by value from lowest double to highest
     * Replaces original data, doesn't return anything
     */
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

    /**
     * Returns whether the double data column is sorted
     *
     * @return a boolean indicating whether the hashmap's values are sorted
     */
    @Override
    public boolean isSorted() {
        if (this.data.isEmpty()) {
            return true;
        }
        double current, previous = 0;
        boolean isFirst = true;

        for (Map.Entry mapentry : this.data.entrySet()) {
            if (isFirst) {
                previous = (double) (mapentry.getValue());
                isFirst = false;
            } else {
                current = (double) (mapentry.getValue());
                if (previous > current) {
                    return false;
                }
                previous = current;
            }
        }
        return true;
    }


    /**
     * return min value of the datacol
     *
     * @return min value or null if the col is empty
     */
    protected Double min() {
        Double min = Double.MAX_VALUE;
        Double save = null;
        for (Map.Entry row : data.entrySet()) {
            Double value = (Double) row.getValue();
            if (value != null && min > value) {
                min = value;
                save = value;
            }
        }
        return save;
    }

    /**
     * return max value of the datacol
     *
     * @return max value or null if the col is empty
     */
    protected Double max() {
        if (getSize() == 0) {
            return null;
        }
        Double max = Double.MIN_VALUE;
        Double save = null;
        for (Map.Entry row : data.entrySet()) {
            Double value = (Double) row.getValue();
            if (value != null && max < value) {
                max = value;
                save = value;
            }
        }
        return max;
    }

    /**
     * return means of Double in the datacol
     *
     * @return means value or 0 if empty
     */
    public double means() {
        double somme = 0;
        double compteur = 0;
        for (Map.Entry row : data.entrySet()) {
            Double value = (Double) row.getValue();
            if (value != null) {
                somme += value;
                compteur++;
            }
        }
        return somme / Math.max(compteur, 1);
    }

    /**
     * return sum of Double in the datacol
     *
     * @return sum value or 0 if empty
     */
    public double sum() {
        double somme = 0;
        for (Map.Entry row : data.entrySet()) {
            Double value = (Double) row.getValue();
            if (value != null) {
                somme += value;
            }
        }
        return somme;
    }

    /**
     * add to all double in datacol the value in parameter
     *
     * @param valueToAdd value to add
     */
    public void addToAll(double valueToAdd) {
        for (Map.Entry row : data.entrySet()) {
            Double value = (Double) row.getValue();
            if (value != null) {
                value += valueToAdd;
                data.put((String) row.getKey(), value);
            }
        }
    }

    /**
     * sub to all double in datacol the value in parameter
     *
     * @param valueToSub value to sub
     */
    public void subToAll(double valueToSub) {
        addToAll(-valueToSub);
    }

    /**
     * multiply to all double in datacol the value in parameter
     *
     * @param valueTomultiply value to multiply
     */
    public void multiplyToAll(double valueTomultiply) {
        for (Map.Entry row : data.entrySet()) {
            Double value = (Double) row.getValue();
            if (value != null) {
                value *= valueTomultiply;
                data.put((String) row.getKey(), value);
            }
        }
    }

    /**
     * divide to all double in datacol the value in parameter
     * assert fail if valueTodivide equals 0
     * @param valueTodivide value to multiply
     */
    public void divideToAll(double valueTodivide) {
        assert(valueTodivide != 0);
        for (Map.Entry row : data.entrySet()) {
            Double value = (Double) row.getValue();
            if (value != null) {
                value /= valueTodivide;
                data.put((String) row.getKey(), value);
            }
        }
    }

    /**
     * add double from col to this
     * @param doubleDataCol
     */
    public void addCol(DoubleDataCol doubleDataCol){
        for (Map.Entry row : data.entrySet()) {
            Double value = (Double) row.getValue();
            if (value != null) {
                Double integer = doubleDataCol.get((String)row.getKey());
                if(integer != null){
                    data.put((String) row.getKey(),integer+value);
                }
            }
        }
    }

    /**
     * sub double from col to this
     * @param doubleDataCol
     */
    public void subCol(DoubleDataCol doubleDataCol){
        for (Map.Entry row : data.entrySet()) {
            Double value = (Double) row.getValue();
            if (value != null) {
                Double integer = doubleDataCol.get((String)row.getKey());
                if(integer != null){
                    data.put((String) row.getKey(),value - integer);
                }
            }
        }
    }

    /**
     * divide double from col to this
     * assert fail if 0 in integerDataCol
     * @param doubleDataCol
     */
    public void divideCol(DoubleDataCol doubleDataCol){
        for (Map.Entry row : data.entrySet()) {
            Double value = (Double) row.getValue();
            if (value != null) {
                Double integer = doubleDataCol.get((String)row.getKey());
                if(integer != null ){
                    assert(integer != 0);
                    data.put((String) row.getKey(),value/integer);
                }
            }
        }
    }

    /**
     * multiply double from col to this
     * @param doubleDataCol
     */
    public void multiplyCol(DoubleDataCol doubleDataCol){
        for (Map.Entry row : data.entrySet()) {
            Double value = (Double) row.getValue();
            if (value != null) {
                Double integer = doubleDataCol.get((String)row.getKey());
                if(integer != null){
                    data.put((String) row.getKey(),integer*value);
                }
            }
        }
    }
}