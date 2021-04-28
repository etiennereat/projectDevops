package com.panda.datacol;

import com.panda.dataframe.DataFrame;
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
    @DisplayName("Test DoubleDataCol(double[] v)")
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
    @DisplayName("Test DoubleDataCol(double[] v, String[] i)")
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
    @DisplayName("Test DoubleDataCol(double[] v, String[] i), empty i")
    public void testConstructorWithValuesAndNoIndexes() {
        double[] values = {1, 2, 3};
        String[] indexes = {};

        DoubleDataCol dc = new DoubleDataCol(values, indexes);

        Assertions.assertSame(indexes.length, dc.getSize());
        Assertions.assertSame(indexes.length, dc.getIndexes().size());
    }

    @Test
    @DisplayName("Test DoubleDataCol(double[] v, String[] i), less indexes")
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
    @DisplayName("Test toString(String i), missing value")
    public void testToStringMissingCell() {
        double[] values = {1, 2, 3, 4, 5};
        String[] indexes = {"a", "b", "c", "d", "e"};
        DoubleDataCol dc = new DoubleDataCol(values, indexes);
        dc.remove(indexes[2]);
        Assertions.assertEquals("1.0 2.0 NaN 4.0 5.0", dc.toString());
    }

    @Test
    @DisplayName("Test toString(String i), empty column")
    public void testToStringEmptyCol() {
        DoubleDataCol dc = new DoubleDataCol();
        Assertions.assertEquals("Empty column", dc.toString());
    }

    @Test
    @DisplayName("Test max")
    public void testMax() {
        double[] values = {8, 5, 3, 4, 2,5.2,3};
        DoubleDataCol dc = new DoubleDataCol(values);
        dc.remove("0");
        Assertions.assertEquals(5.2, dc.max());
    }

    @Test
    @DisplayName("Test min")
    public void testMin() {
        double[] values = {0.99, 5, 3, 4, 1,5.2,3};
        DoubleDataCol dc = new DoubleDataCol(values);
        dc.remove("0");
        Assertions.assertEquals(1, dc.min());
    }

    @Test
    @DisplayName("Test means")
    public void testMeans() {
        double[] values = {-2,1.2, 5, 3.5, 4, 0.5,5.2,3,2.55};
        DoubleDataCol dc = new DoubleDataCol(values);
        dc.remove("0");
        Assertions.assertEquals(3.11875, dc.means());
    }

    @Test
    @DisplayName("Test sum")
    public void testSum() {
        double[] values = {-2,1.2, 5, 3.5, 4, 0.5,5.2,3,2.55};
        DoubleDataCol dc = new DoubleDataCol(values);
        dc.remove("0");
        Assertions.assertEquals(24.95, dc.sum());
    }

    @Test
    @DisplayName("Test isSorted, 0 elements")
    public void testIsSorted0(){
        DoubleDataCol dc = new DoubleDataCol();
        Assertions.assertTrue(dc.isSorted());
    }

    @Test
    @DisplayName("Test isSorted, 1 elements")
    public void testIsSorted1(){
        double[] values = {69};
        String[] indexes = {"a"};
        DoubleDataCol dc = new DoubleDataCol(values, indexes);
        Assertions.assertTrue(dc.isSorted());
    }

    @Test
    @DisplayName("Test isSorted, 3 elements, not sorted")
    public void testIsSortedFalse(){
        double[] values = { 6, 12, 2};
        String[] indexes = { "a", "b", "c"};
        DoubleDataCol dc = new DoubleDataCol(values, indexes);
        Assertions.assertFalse(dc.isSorted());
    }

    @Test
    @DisplayName("Test isSorted, 3 elements, sorted")
    public void testIsSorted(){
        double[] values = { 1, 2, 3};
        String[] indexes = { "a", "b", "c"};
        DoubleDataCol dc = new DoubleDataCol(values, indexes);
        Assertions.assertTrue(dc.isSorted());
    }

    @Test
    @DisplayName("Test sortByValue, no element")
    public void testSortEmpty(){
        DoubleDataCol dc = new DoubleDataCol();
        dc.sortByValue();
        Assertions.assertTrue(dc.isSorted());
    }

    @Test
    @DisplayName("Test sortByValue, 5 elems")
    public void testSort1(){
        double[] values = { 1, 4, 2, 13, 3};
        String[] indexes = { "a", "b", "c", "d", "e"};
        DoubleDataCol dc = new DoubleDataCol(values, indexes);
        dc.sortByValue();
        Assertions.assertTrue(dc.isSorted());
    }

    @Test
    @DisplayName("Test add to all element")
    public void testAddToAll() {
        double[] values = {1, 4, -2, 13, 3};
        DoubleDataCol dc = new DoubleDataCol(values);
        dc.addToAll(1.4);
        int label = 0;
        for(double valueTest : values) {
            Assertions.assertEquals(valueTest + 1.4, dc.get(Integer.toString(label)));
            label++;
        }
    }

    @Test
    @DisplayName("Test sub to all element")
    public void testSubToAll() {
        double[] values = {1, 4, -2, 13, 3};
        DoubleDataCol dc = new DoubleDataCol(values);
        dc.subToAll(1.4);
        int label = 0;
        for(double valueTest : values) {
            Assertions.assertEquals(valueTest - 1.4, dc.get(Integer.toString(label)));
            label++;
        }
    }

    @Test
    @DisplayName("Test multiply to all element")
    public void testMultiplyToAll() {
        double[] values = {1, 4, -2, 13, 3};
        DoubleDataCol dc = new DoubleDataCol(values);
        dc.multiplyToAll(2.2);
        int label = 0;
        for(double valueTest : values) {
            Assertions.assertEquals(valueTest* 2.2, dc.get(Integer.toString(label)));
            label++;
        }
    }

    @Test
    @DisplayName("Test divide to all element")
    public void testDivideToAll() {
        double[] values = {1, 4, -2, 13, 3};
        DoubleDataCol dc = new DoubleDataCol(values);
        dc.divideToAll(2.5);
        int label = 0;
        for(double valueTest : values) {
            Assertions.assertEquals(valueTest/2.5, dc.get(Integer.toString(label)));
            label++;
        }
    }

    @Test
    @DisplayName("test fill empty space")
    public void testFillEmptySpace() {
        double[] values = {1, 4, -2, 13, 3};
        double[] valueGoal = {1, 8.2, -2, 13, 8.2};
        DoubleDataCol dc = new DoubleDataCol(values);
        dc.remove("1");
        dc.remove("4");
        dc.fillEmptySpaceWith(8.2);
        int label = 0;
        for (double valueTest : valueGoal) {
            Assertions.assertEquals(valueTest, dc.get(Integer.toString(label)));
            label++;
        }
    }

    @Test
    @DisplayName("test add with another datacol ")
    public void testAddDatacol() {
        double[] values = {1, 4, -2, 13, 3};
        double[] values2 = {1, 8, -2, 13, 8,2};
        DoubleDataCol dc = new DoubleDataCol(values2);
        DoubleDataCol dc2 = new DoubleDataCol(values);
        dc.addCol(dc2);
        for (int label=0; label < values.length ; label++) {
            Assertions.assertEquals((values[label]+values2[label]), dc.get(Integer.toString(label)));
        }
    }

    @Test
    @DisplayName("test add with another datacol ")
    public void testSubDatacol() {
        double[] values = {1, 4, -2, 13, 3};
        double[] values2 = {1, 8, -2, 13, 8,2};
        DoubleDataCol dc = new DoubleDataCol(values);
        DoubleDataCol dc2 = new DoubleDataCol(values2);
        dc.subCol(dc2);
        for (int label=0; label < values.length ; label++) {
            Assertions.assertEquals((values[label]-values2[label]), dc.get(Integer.toString(label)));
        }
    }

    @Test
    @DisplayName("test divide with another datacol ")
    public void testDivideDatacol() {
        double[] values = {1, 4, -2, 13, 3};
        double[] values2 = {1, 8, -2, 13, 8,2,0};
        DoubleDataCol dc = new DoubleDataCol(values);
        DoubleDataCol dc2 = new DoubleDataCol(values2);
        dc.divideCol(dc2);
        for (int label=0; label < values.length ; label++) {
            Assertions.assertEquals((values[label]/values2[label]), dc.get(Integer.toString(label)));
        }
    }

    @Test
    @DisplayName("test multiply with another datacol ")
    public void testMuliplyDatacol() {
        double[] values = {1, 4, -2, 13, 3};
        double[] values2 = {1, 8, -2, 13, 8,2};
        DoubleDataCol dc = new DoubleDataCol(values);
        DoubleDataCol dc2 = new DoubleDataCol(values2);
        dc.multiplyCol(dc2);
        for (int label=0; label < values.length ; label++) {
            Assertions.assertEquals((values[label]*values2[label]), dc.get(Integer.toString(label)));
        }
    }

    @Test
    @DisplayName("test divide with another datacol with 0")
    public void testDivideDatacolwithzero() {
        double[] values = {1, 4, -2, 13, 3};
        double[] values2 = {1, 0, -2, 13, 8};
        DoubleDataCol dc = new DoubleDataCol(values);
        DoubleDataCol dc2 = new DoubleDataCol(values2);
        Assertions.assertThrows(AssertionError.class,() -> {
            dc.divideCol(dc2);
        });
    }

    @Test
    @DisplayName("test divide with another datacol with 0")
    public void testDividebyzero() {
        double[] values = {1, 4, -2, 13, 3};
        DoubleDataCol dc = new DoubleDataCol(values);
        Assertions.assertThrows(AssertionError.class,() -> {
            dc.divideToAll(0);
        });
    }
}
