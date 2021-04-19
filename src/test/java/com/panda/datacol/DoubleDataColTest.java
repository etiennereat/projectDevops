package com.panda.datacol;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Unit test for simple DoubleDataCol.
 */
public class DoubleDataColTest {

    @Test
    @DisplayName("Test DoubleDataCol()")
    public void testConstructorMain() {
        DoubleDataCol dc = new DoubleDataCol();
        Assertions.assertSame(ColType.DOUBLE, dc.getType());
        Assertions.assertSame(0, dc.getSize());
        Assertions.assertSame(0, dc.getIndexes().size());
    }

    @Test
    @DisplayName("Test DoubleDataCol(int[] v)")
    public void testConstructorWithValues() {
        double[] values = {1, 2, 3};
        DoubleDataCol dc = new DoubleDataCol(values);

        Assertions.assertSame(values.length, dc.getSize());

        ArrayList<String> indexes = dc.getIndexes();
        Assertions.assertSame(values.length, indexes.size());

        // check if default indexes are well assigned
        for (int i = 0; i < values.length; i++) {
            Assertions.assertEquals(indexes.get(i), String.valueOf(i));
        }
    }

    @Test
    @DisplayName("Test DoubleDataCol(int[] v, String[] i)")
    public void testConstructorWithValuesAndIndexes() {
        double[] values = {1, 2, 3};
        String[] indexes = {"a", "b", "c"};

        DoubleDataCol dc = new DoubleDataCol(values, indexes);

        Assertions.assertSame(values.length, dc.getSize());

        ArrayList<String> dcIndexes = dc.getIndexes();
        Assertions.assertSame(indexes.length, dcIndexes.size());

        // check if indexes are well assigned
        for (int i = 0; i < indexes.length; i++) {
            Assertions.assertEquals(indexes[i], dcIndexes.get(i));
        }
    }

    @Test
    @DisplayName("Test DoubleDataCol(int[] v, String[] i), empty i")
    public void testConstructorWithValuesAndNoIndexes() {
        double[] values = {1, 2, 3};
        String[] indexes = {};

        DoubleDataCol dc = new DoubleDataCol(values, indexes);

        Assertions.assertSame(indexes.length, dc.getSize());
        Assertions.assertSame(indexes.length, dc.getIndexes().size());
    }

    @Test
    @DisplayName("Test DoubleDataCol(int[] v, String[] i), less indexes")
    public void testConstructorWithValuesAndLessIndexes() {
        double[] values = {1, 2, 3};
        String[] indexes = {"a", "b"};

        DoubleDataCol dc = new DoubleDataCol(values, indexes);

        // min(v.len, i.len) items are assigned
        Assertions.assertSame(indexes.length, dc.getSize());
        ArrayList<String> dcIndexes = dc.getIndexes();
        Assertions.assertSame(indexes.length, dcIndexes.size());

        // check if indexes are well assigned
        for (int i = 0; i < indexes.length; i++) {
            Assertions.assertEquals(indexes[i], dcIndexes.get(i));
        }
    }

    @Test
    @DisplayName("Test add(int v)")
    public void testAddValue() {
        DoubleDataCol dc = new DoubleDataCol();
        dc.add(1.0);
        // min(v.len, i.len) items are assigned
        Assertions.assertSame(1, dc.getSize());

        ArrayList<String> indexes = dc.getIndexes();
        Assertions.assertEquals(1, dc.get(indexes.get(0)));
    }

    @Test
    @DisplayName("Test add(int v, String i)")
    public void testAddValueWithIndex() {
        DoubleDataCol dc = new DoubleDataCol();
        double value = 4224;
        String index = "42";
        dc.add(value, index);
        // min(v.len, i.len) items are assigned
        Assertions.assertSame(1, dc.getSize());
        // is this the right element
        Assertions.assertEquals(value, dc.get(index));
    }

    @Test
    @DisplayName("Test add(int v, String i), update existing val")
    public void testUpdateAdd() {
        double[] values = {1, 2, 3, 4, 5};
        String[] indexes = {"a", "b", "c", "d", "e"};

        String index = indexes[3];
        double newValue = 42.0;

        DoubleDataCol dc = new DoubleDataCol(values, indexes);
        dc.add(newValue, index);
        // item successfully updated
        Assertions.assertEquals(newValue, dc.get(index));
        // keeps the same number of ids
        Assertions.assertSame(indexes.length, dc.getSize());
    }

    @Test
    @DisplayName("Test get(String i), existing")
    public void testGetExisting() {
        double[] values = {1, 2, 3, 4, 5};
        String[] indexes = {"a", "b", "c", "d", "e"};

        double value = values[1];
        String index = indexes[1];

        DoubleDataCol dc = new DoubleDataCol(values, indexes);

        double res = dc.get(index);
        Assertions.assertEquals(value, res);
    }

    @Test
    @DisplayName("Test get(String i), non existing")
    public void testGetNonExisting() {
        double[] values = {1, 2, 3, 4, 5};
        String[] indexes = {"a", "b", "c", "d", "e"};

        String index = "z";

        DoubleDataCol dc = new DoubleDataCol(values, indexes);

        Double res = dc.get(index);
        Assertions.assertSame(null, res);
    }

    @Test
    @DisplayName("Test remove(String i), existing")
    public void testRemoveExisting() {
        double[] values = {1, 2, 3, 4, 5};
        String[] indexes = {"a", "b", "c", "d", "e"};

        String index = "b";

        DoubleDataCol dc = new DoubleDataCol(values, indexes);

        boolean res = dc.remove(index);
        Assertions.assertTrue(res);
        // check if not removed index
        Assertions.assertSame(values.length, dc.getSize());
        // check if element really removed
        Assertions.assertSame(null, dc.get(index));
    }

    @Test
    @DisplayName("Test remove(String i), non existing")
    public void testRemoveNonExisting() {
        double[] values = {1, 2, 3, 4, 5};
        String[] indexes = {"a", "b", "c", "d", "e"};

        String index = "z";

        DoubleDataCol dc = new DoubleDataCol(values, indexes);
        Assertions.assertSame(values.length, dc.getSize());

        boolean res = dc.remove(index);
        Assertions.assertFalse(res);
    }

    @Test
    @DisplayName("Test toString(String i)")
    public void testToString() {
        double[] values = {1, 2, 3, 4, 5};
        DoubleDataCol dc = new DoubleDataCol(values);
        Assertions.assertEquals("1.0 2.0 3.0 4.0 5.0", dc.toString());
    }

    @Test
    @DisplayName("Test toString(String i), empty column")
    public void testToStringEmptyCol() {
        DoubleDataCol dc = new DoubleDataCol();
        Assertions.assertEquals("Empty column", dc.toString());
    }
}
