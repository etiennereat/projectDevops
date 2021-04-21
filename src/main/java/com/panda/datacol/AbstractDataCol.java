package com.panda.datacol;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstract class offering shared functionality between different
 * types of DataCol-s.
 *
 * @todo select intervals of indexes
 * @todo sort
 * @todo swap
 */
public abstract class AbstractDataCol<T> implements DataCol {

    protected ColType type;
    protected ArrayList<String> indexes;
    protected HashMap<String, T> data;

    protected AbstractDataCol() {
        data = new HashMap<>();
        indexes = new ArrayList<>();
    }

    protected AbstractDataCol(ColType type) {
        this();

        this.type = type;
    }

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
     * Adds a value to an auto-generated index.
     *
     * @param value - value to be added
     */
    public void add(T value) {
        int hashCode = String.valueOf(value).hashCode();
        String index = String.valueOf(hashCode);

        if (!data.containsKey(index)) {
            indexes.add(index);
        }

        data.put(index, value);
    }


    /**
     * Adds a value with a specified index.
     * <p>
     * If index is already present in the column, associated value is updated.
     *
     * @param value - value to be added
     * @param index - associated index to the value
     */
    public void add(T value, String index) {
        if (!data.containsKey(index)) {
            indexes.add(index);
        }

        data.put(index, value);
    }

    /**
     * Returns a value at a specific index. Returns null if no index found.
     *
     * @param index - cell's index
     * @return cell's value
     */
    public T get(String index) {
        if (data.containsKey(index)) {
            return data.get(index);
        } else {
            return null;
        }
    }


    /**
     * Removes the cell corresponding to the index. If such an index does
     * not exist, returns false. On successful removal - returns true and
     * keeps the index.
     *
     * @param index - index of the cell to be removed
     * @return cell removal status
     */
    public boolean remove(String index) {
        if (data.containsKey(index)) {
            data.put(index, null);
            return true;
        }

        return false;
    }

    /**
     * Removes the index and associated data. If such an index does
     * not exist, returns false. On successful removal - returns true.
     *
     * @param index - index to be removed
     * @return index removal status
     */
    public boolean removeIndex(String index) {
        if (indexes.contains(index)) {
            indexes.remove(index);
            data.remove(index);
            return true;
        }
        return false;
    }

    /**
     * Update indexes. All data that is associated to other
     * indexes than those in the input array is removed.
     *
     * @param indexes - new indexes
     */
    public void updateIndexes(ArrayList<String> indexes) {
        this.indexes = new ArrayList<>(indexes);

        // remove obsolete data
        for (String key : data.keySet()) {
            if (!this.indexes.contains(key)) {
                data.remove(key);
            }
        }
    }

    /**
     * Prints the items of the column.
     */
    public void show() {
        System.out.println(this);
    }


    @Override
    public String toString() {
        if (indexes.size() == 0) {
            return "Empty column";
        }

        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < indexes.size(); i++) {
            String value = "NaN";

            if (data.containsKey(indexes.get(i))) {
                T temp = data.get(indexes.get(i));
                value = temp != null ? String.valueOf(temp) : "NaN";
            }

            buffer.append(String.format("%s%s", value, (i != indexes.size() - 1 ? " " : "")));
        }

        return buffer.toString();
    }

}
