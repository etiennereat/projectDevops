package com.panda.datacol;

import java.util.ArrayList;
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
}