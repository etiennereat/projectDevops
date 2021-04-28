package com.panda.datacol;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Unit test for simple DoubleDataCol.
 */
public class BooleanDataColTest {

    @Test
    @DisplayName("Test BooleanDataCol()")
    public void testConstructorMain() {
        BooleanDataCol dc = new BooleanDataCol();
        Assertions.assertSame(ColType.BOOLEAN, dc.getType());
        Assertions.assertSame(0, dc.getSize());
        Assertions.assertSame(0, dc.getIndexes().size());
    }

    @Test
    @DisplayName("Test BooleanDataCol(int[] v)")
    public void testConstructorWithValues() {
        boolean[] values = {true, false, true};
        BooleanDataCol dc = new BooleanDataCol(values);

        Assertions.assertSame(values.length, dc.getSize());

        ArrayList<String> indexes = dc.getIndexes();
        Assertions.assertSame(values.length, indexes.size());

        // check if default indexes are well assigned
        for (int i = 0; i < values.length; i++) {
            Assertions.assertEquals(indexes.get(i), String.valueOf(i));
        }
    }

    @Test
    @DisplayName("Test BooleanDataCol(int[] v, String[] i)")
    public void testConstructorWithValuesAndIndexes() {
        boolean[] values = {true, false, true};
        String[] indexes = {"a", "b", "c"};

        BooleanDataCol dc = new BooleanDataCol(values, indexes);

        Assertions.assertSame(values.length, dc.getSize());

        ArrayList<String> dcIndexes = dc.getIndexes();
        Assertions.assertSame(indexes.length, dcIndexes.size());

        // check if indexes are well assigned
        for (int i = 0; i < indexes.length; i++) {
            Assertions.assertEquals(indexes[i], dcIndexes.get(i));
        }
    }

    @Test
    @DisplayName("Test BooleanDataCol(int[] v, String[] i), empty i")
    public void testConstructorWithValuesAndNoIndexes() {
        boolean[] values = {true, false, true};
        String[] indexes = {};

        BooleanDataCol dc = new BooleanDataCol(values, indexes);

        Assertions.assertSame(indexes.length, dc.getSize());
        Assertions.assertSame(indexes.length, dc.getIndexes().size());
    }

    @Test
    @DisplayName("Test BooleanDataCol(int[] v, String[] i), less indexes")
    public void testConstructorWithValuesAndLessIndexes() {
        boolean[] values = {true, false, true};
        String[] indexes = {"a", "b"};

        BooleanDataCol dc = new BooleanDataCol(values, indexes);

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
        BooleanDataCol dc = new BooleanDataCol();
        dc.add(false);
        // min(v.len, i.len) items are assigned
        Assertions.assertSame(1, dc.getSize());

        ArrayList<String> indexes = dc.getIndexes();
        Assertions.assertEquals(false, dc.get(indexes.get(0)));
    }

    @Test
    @DisplayName("Test add(int v, String i)")
    public void testAddValueWithIndex() {
        BooleanDataCol dc = new BooleanDataCol();
        String index = "42";
        dc.add(true, index);
        // min(v.len, i.len) items are assigned
        Assertions.assertSame(1, dc.getSize());
        // is this the right element
        Assertions.assertEquals(true, dc.get(index));
    }

    @Test
    @DisplayName("Test add(int v, String i), update existing val")
    public void testUpdateAdd() {
        boolean[] values = {true, false, true, false, false};
        String[] indexes = {"a", "b", "c", "d", "e"};

        String index = indexes[0];
        boolean newValue = false;

        BooleanDataCol dc = new BooleanDataCol(values, indexes);
        dc.add(false, index);
        // item successfully updated
        Assertions.assertEquals(false, dc.get(index));
        // keeps the same number of ids
        Assertions.assertSame(indexes.length, dc.getSize());
    }

    @Test
    @DisplayName("Test get(String i), existing")
    public void testGetExisting() {
        boolean[] values = {true, false, false, false, false};
        String[] indexes = {"a", "b", "c", "d", "e"};

        boolean value = false;
        String index = indexes[1];

        BooleanDataCol dc = new BooleanDataCol(values, indexes);

        boolean res = dc.get(index);
        Assertions.assertFalse(res);
    }

    @Test
    @DisplayName("Test get(String i), non existing")
    public void testGetNonExisting() {
        boolean[] values = {true, false, false, false, true};
        String[] indexes = {"a", "b", "c", "d", "e"};

        String index = "z";

        BooleanDataCol dc = new BooleanDataCol(values, indexes);

        Boolean res = dc.get(index);
        Assertions.assertSame(null, res);
    }

    @Test
    @DisplayName("Test remove(String i), existing")
    public void testRemoveExisting() {
        boolean[] values = {true, false, false, false, true};
        String[] indexes = {"a", "b", "c", "d", "e"};

        String index = "b";

        BooleanDataCol dc = new BooleanDataCol(values, indexes);

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
        boolean[] values = {true, false, false, false, true};
        String[] indexes = {"a", "b", "c", "d", "e"};

        String index = "z";

        BooleanDataCol dc = new BooleanDataCol(values, indexes);
        Assertions.assertSame(values.length, dc.getSize());

        boolean res = dc.remove(index);
        Assertions.assertFalse(res);
    }

    @Test
    @DisplayName("Test toString(String i)")
    public void testToString() {
        boolean[] values = {true, false, false, false, true};
        BooleanDataCol dc = new BooleanDataCol(values);
        Assertions.assertEquals("true false false false true", dc.toString());
    }

    @Test
    @DisplayName("Test toString(String i), missing value")
    public void testToStringMissingCell() {
        boolean[] values = {true, false, false, true, false};
        String[] indexes = {"a", "b", "c", "d", "e"};
        BooleanDataCol dc = new BooleanDataCol(values, indexes);
        dc.remove(indexes[2]);
        Assertions.assertEquals("true false NaN true false", dc.toString());
    }

    @Test
    @DisplayName("Test toString(String i), empty column")
    public void testToStringEmptyCol() {
        BooleanDataCol dc = new BooleanDataCol();
        Assertions.assertEquals("Empty column", dc.toString());
    }

    @Test
    @DisplayName("Test proportion of true")
    public void testTrueProportion() {
        boolean[] values = {true, false, false, false, false, true, false, false, false, false, true, false};
        BooleanDataCol dc = new BooleanDataCol(values);
        dc.remove("0");
        Assertions.assertEquals(2. / 11. * 100., dc.trueProportion());
    }

    @Test
    @DisplayName("Test distribution")
    public void testdistribution() {
        boolean[] values = {true, false, false, false, false, true, false, false, false, false, true, false};
        BooleanDataCol dc = new BooleanDataCol(values);
        dc.remove("0");
        int[] distrib = dc.distribution();
        Assertions.assertEquals(2,distrib[0]);
        Assertions.assertEquals(9,distrib[1]);
    }

    @Test
    @DisplayName("Test isSorted, 0 elements")
    public void testIsSorted0(){
        BooleanDataCol dc = new BooleanDataCol();
        Assertions.assertTrue(dc.isSorted());
    }

    @Test
    @DisplayName("Test isSorted, 1 elements")
    public void testIsSorted1(){
        boolean[] values = {true};
        String[] indexes = {"a"};
        BooleanDataCol dc = new BooleanDataCol(values, indexes);
        Assertions.assertTrue(dc.isSorted());
    }

    @Test
    @DisplayName("Test isSorted, 3 elements, not sorted")
    public void testIsSortedFalse(){
        boolean[] values = { true, false, true};
        String[] indexes = { "a", "b", "c"};
        BooleanDataCol dc = new BooleanDataCol(values, indexes);
        Assertions.assertFalse(dc.isSorted());
    }

    @Test
    @DisplayName("Test isSorted, 3 elements, sorted")
    public void testIsSorted(){
        boolean[] values = { true, true, false};
        String[] indexes = { "a", "b", "c"};
        BooleanDataCol dc = new BooleanDataCol(values, indexes);
        Assertions.assertTrue(dc.isSorted());
    }

    @Test
    @DisplayName("Test sortByValue, no element")
    public void testSortEmpty(){
        BooleanDataCol dc = new BooleanDataCol();
        dc.sortByValue();
        Assertions.assertTrue(dc.isSorted());
    }

    @Test
    @DisplayName("Test sortByValue, 5 elems")
    public void testSort1(){
        boolean[] values = {false, true, true, false, true};
        String[] indexes = { "a", "b", "c", "d", "e"};
        BooleanDataCol dc = new BooleanDataCol(values, indexes);
        dc.sortByValue();
        Assertions.assertTrue(dc.isSorted());
    }

    @Test
    @DisplayName("test fill empty space")
    public void testFillEmptySpace() {
        boolean[] values = {true, true, true, true, true};
        boolean[] valueGoal = {false, true, true, false, true};
        BooleanDataCol dc = new BooleanDataCol(values);
        dc.remove("0");
        dc.remove("3");
        dc.fillEmptySpaceWith(false);
        int label = 0;
        for (boolean valueTest : valueGoal) {
            Assertions.assertEquals(valueTest, dc.get(Integer.toString(label)));
            label++;
        }
    }

    @Test
    @DisplayName("test and operation ")
    public void testAnd() {
        boolean[] values = {false, true, false, true, true};
        boolean[] values2 = {true, true, false, true, true,false};
        BooleanDataCol dc = new BooleanDataCol(values);
        BooleanDataCol dc2 = new BooleanDataCol(values2);
        dc.and(dc2);
        for (int label=0; label < values.length ; label++) {
            Assertions.assertEquals(values[label] && values2[label], dc.get(Integer.toString(label)));
        }
    }

    @Test
    @DisplayName("test or operation ")
    public void testOr() {
        boolean[] values = {false, true, true, false, true};
        boolean[] values2 = {true, true, false, false, true,false};
        BooleanDataCol dc = new BooleanDataCol(values);
        BooleanDataCol dc2 = new BooleanDataCol(values2);
        dc.or(dc2);
        for (int label=0; label < values.length ; label++) {
            Assertions.assertEquals(values[label] || values2[label], dc.get(Integer.toString(label)));
        }
    }

    @Test
    @DisplayName("test xor operation ")
    public void testXor() {
        boolean[] values = {false, true, true, false, true};
        boolean[] values2 = {true, true, false, false, true,false};
        BooleanDataCol dc = new BooleanDataCol(values);
        BooleanDataCol dc2 = new BooleanDataCol(values2);
        dc.xor(dc2);
        for (int label=0; label < values.length ; label++) {
            Assertions.assertEquals((values[label] || values2[label]) && (values[label] != values2[label]), dc.get(Integer.toString(label)));
        }
    }
}
