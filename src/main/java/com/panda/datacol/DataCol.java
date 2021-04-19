package com.panda.datacol;

import java.util.ArrayList;

/**
 * Abstract class offering shared functionality between different
 * types of DataCol-s.
 *
 * @todo select intervals of indexes
 * @todo sort
 * @todo swap
 */
public abstract class DataCol {

    protected ColType type;
    protected ArrayList<String> indexes;

    /**
     * Print the cells of the column.
     */
    abstract public void show();

    /**
     * Returns column's type.
     *
     * @return type
     */
    public ColType getType() {
        return type;
    }

    /**
     * Returns a clone of the list of indexes.
     *
     * @return list of indexes
     */
    public ArrayList<String> getIndexes() {
        return new ArrayList<>(indexes);
    }

    /**
     * Returns the number of cells.
     *
     * @return size
     */
    public int getSize() {
        return indexes.size();
    }

    /**
     * Removes the cell corresponding to the index. If such an index does
     * not exist, returns false. On successful removal - returns true and
     * keeps the index.
     *
     * @param index - index of the cell to be removed
     * @return cell removal status
     */
    public abstract boolean remove(String index);
}
