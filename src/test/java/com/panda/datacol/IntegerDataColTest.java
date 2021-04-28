package com.panda.datacol;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;

/**
 * Unit test for simple IntegerDataCol.
 */
public class IntegerDataColTest {

    @Test
    @DisplayName("Test IntegerDataCol()")
    public void testConstructorMain() {
        IntegerDataCol dc = new IntegerDataCol();
        Assertions.assertSame(ColType.INTEGER, dc.getType());
        Assertions.assertSame(0, dc.getSize());
        Assertions.assertSame(0, dc.getIndexes().size());
    }

    @Test
    @DisplayName("Test IntegerDataCol(int[] v)")
    public void testConstructorWithValues() {
        int[] values = {1, 2, 3};
        IntegerDataCol dc = new IntegerDataCol(values);

        Assertions.assertSame(values.length, dc.getSize());

        ArrayList<String> indexes = dc.getIndexes();
        Assertions.assertSame(values.length, indexes.size());

        // check if default indexes are well assigned
        for (int i = 0; i < values.length; i++) {
            Assertions.assertEquals(indexes.get(i), String.valueOf(i));
        }
    }

    @Test
    @DisplayName("Test IntegerDataCol(int[] v, String[] i)")
    public void testConstructorWithValuesAndIndexes() {
        int[] values = {1, 2, 3};
        String[] indexes = {"a", "b", "c"};

        IntegerDataCol dc = new IntegerDataCol(values, indexes);

        Assertions.assertSame(values.length, dc.getSize());

        ArrayList<String> dcIndexes = dc.getIndexes();
        Assertions.assertSame(indexes.length, dcIndexes.size());

        // check if indexes are well assigned
        for (int i = 0; i < indexes.length; i++) {
            Assertions.assertEquals(indexes[i], dcIndexes.get(i));
        }
    }

    @Test
    @DisplayName("Test IntegerDataCol(int[] v, String[] i), empty i")
    public void testConstructorWithValuesAndNoIndexes() {
        int[] values = {1, 2, 3};
        String[] indexes = {};

        IntegerDataCol dc = new IntegerDataCol(values, indexes);

        Assertions.assertSame(indexes.length, dc.getSize());
        Assertions.assertSame(indexes.length, dc.getIndexes().size());
    }

    @Test
    @DisplayName("Test IntegerDataCol(int[] v, String[] i), less indexes")
    public void testConstructorWithValuesAndLessIndexes() {
        int[] values = {1, 2, 3};
        String[] indexes = {"a", "b"};

        IntegerDataCol dc = new IntegerDataCol(values, indexes);

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
        IntegerDataCol dc = new IntegerDataCol();
        dc.add(1);
        // min(v.len, i.len) items are assigned
        Assertions.assertSame(1, dc.getSize());

        ArrayList<String> indexes = dc.getIndexes();
        Assertions.assertEquals(1, dc.get(indexes.get(0)));
    }

    @Test
    @DisplayName("Test add(int v, String i)")
    public void testAddValueWithIndex() {
        IntegerDataCol dc = new IntegerDataCol();
        int value = 4224;
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
        int[] values = {1, 2, 3, 4, 5};
        String[] indexes = {"a", "b", "c", "d", "e"};

        String index = indexes[3];
        int newValue = 42;

        IntegerDataCol dc = new IntegerDataCol(values, indexes);
        dc.add(newValue, index);
        // item successfully updated
        Assertions.assertEquals(newValue, dc.get(index));
        // keeps the same number of ids
        Assertions.assertSame(indexes.length, dc.getSize());
    }

    @Test
    @DisplayName("Test get(String i), existing")
    public void testGetExisting() {
        int[] values = {1, 2, 3, 4, 5};
        String[] indexes = {"a", "b", "c", "d", "e"};

        int value = values[1];
        String index = indexes[1];

        IntegerDataCol dc = new IntegerDataCol(values, indexes);

        int res = dc.get(index);
        Assertions.assertEquals(value, res);
    }

    @Test
    @DisplayName("Test get(String i), non existing")
    public void testGetNonExisting() {
        int[] values = {1, 2, 3, 4, 5};
        String[] indexes = {"a", "b", "c", "d", "e"};

        String index = "z";

        IntegerDataCol dc = new IntegerDataCol(values, indexes);

        Integer res = dc.get(index);
        Assertions.assertSame(null, res);
    }

    @Test
    @DisplayName("Test remove(String i), existing")
    public void testRemoveExisting() {
        int[] values = {1, 2, 3, 4, 5};
        String[] indexes = {"a", "b", "c", "d", "e"};

        String index = "b";

        IntegerDataCol dc = new IntegerDataCol(values, indexes);

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
        int[] values = {1, 2, 3, 4, 5};
        String[] indexes = {"a", "b", "c", "d", "e"};

        String index = "z";

        IntegerDataCol dc = new IntegerDataCol(values, indexes);
        Assertions.assertSame(values.length, dc.getSize());

        boolean res = dc.remove(index);
        Assertions.assertFalse(res);
    }

    @Test
    @DisplayName("Test toString(String i)")
    public void testToString() {
        int[] values = {1, 2, 3, 4, 5};
        IntegerDataCol dc = new IntegerDataCol(values);
        Assertions.assertEquals("1 2 3 4 5", dc.toString());
    }

    @Test
    @DisplayName("Test toString(String i), missing value")
    public void testToStringMissingCell() {
        int[] values = {1, 2, 3, 4, 5};
        String[] indexes = {"a", "b", "c", "d", "e"};
        IntegerDataCol dc = new IntegerDataCol(values, indexes);
        dc.remove(indexes[2]);
        Assertions.assertEquals("1 2 NaN 4 5", dc.toString());
    }

    @Test
    @DisplayName("Test toString(String i), empty column")
    public void testToStringEmptyCol() {
        IntegerDataCol dc = new IntegerDataCol();
        Assertions.assertEquals("Empty column", dc.toString());
    }

    @Test
    @DisplayName("Test max")
    public void testMax() {
        int[] values = {1, 5, 3, 4, 2,1,3};
        IntegerDataCol dc = new IntegerDataCol(values);
        dc.remove("1");
        Assertions.assertEquals(4, dc.max());
    }

    @Test
    @DisplayName("Test min")
    public void testMin() {
        int[] values = {1, -5, 3, 4, 200,-1,3};
        IntegerDataCol dc = new IntegerDataCol(values);
        dc.remove("1");
        Assertions.assertEquals(-1, dc.min());
    }

    @Test
    @DisplayName("Test means")
    public void testMeans() {
        int[] values = {1, 5, 3, 4, 9,-1,3};
        IntegerDataCol dc = new IntegerDataCol(values);
        dc.remove("1");
        Assertions.assertEquals(3.1666666666666665, dc.means());
    }

    @Test
    @DisplayName("Test sum")
    public void testSum() {
        int[] values = {1, 5, 3, 4, 9,-1,3};
        IntegerDataCol dc = new IntegerDataCol(values);
        dc.remove("1");
        Assertions.assertEquals(19, dc.sum());
    }


    @Test
    @DisplayName("Test isSorted, 0 elements")
    public void testIsSorted0(){
        IntegerDataCol dc = new IntegerDataCol();
        Assertions.assertTrue(dc.isSorted());
    }

    @Test
    @DisplayName("Test isSorted, 1 elements")
    public void testIsSorted1(){
        int[] values = {69};
        String[] indexes = {"a"};
        IntegerDataCol dc = new IntegerDataCol(values, indexes);
        Assertions.assertTrue(dc.isSorted());
    }

    @Test
    @DisplayName("Test isSorted, 3 elements, not sorted")
    public void testIsSortedFalse(){
        int[] values = { 6, 12, 2};
        String[] indexes = { "a", "b", "c"};
        IntegerDataCol dc = new IntegerDataCol(values, indexes);
        Assertions.assertFalse(dc.isSorted());
    }

    @Test
    @DisplayName("Test isSorted, 3 elements, sorted")
    public void testIsSorted(){
        int[] values = { 1, 2, 3};
        String[] indexes = { "a", "b", "c"};
        IntegerDataCol dc = new IntegerDataCol(values, indexes);
        Assertions.assertTrue(dc.isSorted());
    }

    @Test
    @DisplayName("Test sortByValue, no element")
    public void testSortEmpty(){
        IntegerDataCol dc = new IntegerDataCol();
        dc.sortByValue();
        Assertions.assertTrue(dc.isSorted());
    }

    @Test
    @DisplayName("Test sortByValue, 5 elems")
    public void testSort1(){
        int[] values = { 1, 4, 2, 13, 3};
        String[] indexes = { "a", "b", "c", "d", "e"};
        IntegerDataCol dc = new IntegerDataCol(values, indexes);
        dc.sortByValue();
        Assertions.assertTrue(dc.isSorted());
    }
}
