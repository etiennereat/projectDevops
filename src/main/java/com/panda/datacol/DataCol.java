package com.panda.datacol;

import java.util.ArrayList;

/**
 * Common interface for all types of DataCol
 */
public interface DataCol {

    /**
     * Returns column's type.
     *
     * @return type
     */
    ColType getType();

    /**
     * Returns a clone of the list of indexes.
     *
     * @return list of indexes
     */
    ArrayList<String> getIndexes();

    /**
     * Returns the number of cells.
     *
     * @return size
     */
    int getSize();

    /**
     * Removes the cell corresponding to the index. If such an index does
     * not exist, returns false. On successful removal - returns true and
     * keeps the index.
     *
     * @param index - index of the cell to be removed
     * @return cell removal status
     */
    boolean remove(String index);

    /**
     * Prints the items of the column.
     */
    void show();

}
