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
     * Removes the index and associated data. If such an index does
     * not exist, returns false. On successful removal - returns true.
     *
     * @param index - index to be removed
     * @return index removal status
     */
    boolean removeIndex(String index);

    /**
     * Update indexes. All data that is associated to other
     * indexes than those in the input array is removed.
     *
     * @param indexes - new indexes
     */
    void updateIndexes(ArrayList<String> indexes);

    /**
     * Prints the items of the column.
     */
    void show();

    /**
     * Select a range of rows.
     *
     * @param from - starting index
     * @param to   - ending index
     * @return a new DataCol
     */
    DataCol selectRows(String from, String to);

    /**
     * Select the rows associated to the requested indexes.
     *
     * @param indexes - list of indexes
     * @return a new DataCol
     */
    DataCol selectRows(ArrayList<String> indexes);

    /**
     * Sorts the columns by value
     * @return
     */
    void sortByValue();

    boolean isSorted();
}
