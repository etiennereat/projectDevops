package com.panda.datacol;

import java.util.*;
import java.util.Map;

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
     * return min value of the datacol
     * @return min value or null if the col is empty
     */
    public Integer min() {
        Integer min = Integer.MAX_VALUE;
        Integer save = null;
        for(Map.Entry row :data.entrySet()){
            Integer value = (Integer)row.getValue();
            if(value != null && min > value){
                min = value;
                save = value;
            }
        }
        return save;
    }

    /**
     * return max value of the datacol
     * @return max value or null if the col is empty
     */
    public Integer max() {
        Integer max = Integer.MIN_VALUE;
        Integer save = null;
        for(Map.Entry row :data.entrySet()){
            Integer value = (Integer)row.getValue();
            if(value != null && max < value){
                max = value;
                save = value;
            }
        }
        return save;
    }

    /**
     * return means of integer in the datacol
     * @return means value or 0 if empty
     */
    public double means() {
        double somme = 0;
        double compteur =0;
        for (Map.Entry row : data.entrySet()) {
            Integer value = (Integer) row.getValue();
            if(value != null ) {
                somme += value;
                compteur++;
            }
        }
        return somme / Math.max(compteur,1);
    }

    /**
     * return sum of Double in the datacol
     * @return sum value or 0 if empty
     */
    public int sum() {
        int somme = 0;
        for (Map.Entry row : data.entrySet()) {
            Integer value = (Integer) row.getValue();
            if(value != null ) {
                somme += value;
            }
        }
        return somme;
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


    /**
     * add to all int in datacol the value in parameter
     *
     * @param valueToAdd value to add
     */
    public void addToAll(int valueToAdd) {
        for (Map.Entry row : data.entrySet()) {
            Integer value = (Integer) row.getValue();
            if (value != null) {
                value += valueToAdd;
                data.put((String) row.getKey(), value);
            }
        }
    }

    /**
     * sub to all int in datacol the value in parameter
     *
     * @param valueToSub value to sub
     */
    public void subToAll(int valueToSub) {
        addToAll(-valueToSub);
    }

    /**
     * multiply to all int in datacol the value in parameter
     *
     * @param valueTomultiply value to multiply
     */
    public void multiplyToAll(int valueTomultiply) {
        for (Map.Entry row : data.entrySet()) {
            Integer value = (Integer) row.getValue();
            if (value != null) {
                value *= valueTomultiply;
                data.put((String) row.getKey(), value);
            }
        }
    }

    /**
     * divide to all int in datacol the value in parameter
     * assert fail if valueTodivide equals 0
     * @param valueTodivide value to multiply
     */
    public void divideToAll(int valueTodivide) {
        assert(valueTodivide != 0);
        for (Map.Entry row : data.entrySet()) {
            Integer value = (Integer) row.getValue();
            if (value != null) {
                value /= valueTodivide;
                data.put((String) row.getKey(), value);
            }
        }
    }

    /**
     * add integer from col to this
     * @param integerDataCol
     */
    public void addCol(IntegerDataCol integerDataCol){
        for (Map.Entry row : data.entrySet()) {
            Integer value = (Integer) row.getValue();
            if (value != null) {
                Integer integer = integerDataCol.get((String)row.getKey());
                if(integer != null){
                    data.put((String) row.getKey(),integer+value);
                }
            }
        }
    }

    /**
     * sub integer from col to this
     * @param integerDataCol
     */
    public void subCol(IntegerDataCol integerDataCol){
        for (Map.Entry row : data.entrySet()) {
            Integer value = (Integer) row.getValue();
            if (value != null) {
                Integer integer = integerDataCol.get((String)row.getKey());
                if(integer != null){
                    data.put((String) row.getKey(),value-integer);
                }
            }
        }
    }

    /**
     * divide integer from col to this
     * assert fail if 0 in integerDataCol
     * @param integerDataCol
     */
    public void divideCol(IntegerDataCol integerDataCol){
        for (Map.Entry row : data.entrySet()) {
            Integer value = (Integer) row.getValue();
            if (value != null) {
                Integer integer = integerDataCol.get((String)row.getKey());
                if(integer != null ){
                    assert(integer != 0);
                    data.put((String) row.getKey(),value/integer);
                }
            }
        }
    }

    /**
     * multiply integer from col to this
     * @param integerDataCol
     */
    public void multiplyCol(IntegerDataCol integerDataCol){
        for (Map.Entry row : data.entrySet()) {
            Integer value = (Integer) row.getValue();
            if (value != null) {
                Integer integer = integerDataCol.get((String)row.getKey());
                if(integer != null){
                    data.put((String) row.getKey(),value*integer);
                }
            }
        }
    }
}