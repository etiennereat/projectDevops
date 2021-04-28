package com.panda.datacol;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Unit test for simple StringDataCol.
 */
public class StringDataColTest {

    @Test
    @DisplayName("Test StringDataCol()")
    public void testConstructorMain() {
        StringDataCol dc = new StringDataCol();
        Assertions.assertSame(ColType.STRING, dc.getType());
        Assertions.assertSame(0, dc.getSize());
        Assertions.assertSame(0, dc.getIndexes().size());
    }

    @Test
    @DisplayName("Test StringDataCol(int[] v)")
    public void testConstructorWithValues() {
        String[] values = {"1", "2", "3"};
        StringDataCol dc = new StringDataCol(values);

        Assertions.assertSame(values.length, dc.getSize());

        ArrayList<String> indexes = dc.getIndexes();
        Assertions.assertSame(values.length, indexes.size());

        // check if default indexes are well assigned
        for (int i = 0; i < values.length; i++) {
            Assertions.assertEquals(indexes.get(i), String.valueOf(i));
        }
    }

    @Test
    @DisplayName("Test StringDataCol(int[] v, String[] i)")
    public void testConstructorWithValuesAndIndexes() {
        String[] values = {"1", "2", "3"};
        String[] indexes = {"a", "b", "c"};

        StringDataCol dc = new StringDataCol(values, indexes);

        Assertions.assertSame(values.length, dc.getSize());

        ArrayList<String> dcIndexes = dc.getIndexes();
        Assertions.assertSame(indexes.length, dcIndexes.size());

        // check if indexes are well assigned
        for (int i = 0; i < indexes.length; i++) {
            Assertions.assertEquals(indexes[i], dcIndexes.get(i));
        }
    }

    @Test
    @DisplayName("Test StringDataCol(int[] v, String[] i), empty i")
    public void testConstructorWithValuesAndNoIndexes() {
        String[] values = {"1", "2", "3"};
        String[] indexes = {};

        StringDataCol dc = new StringDataCol(values, indexes);

        Assertions.assertSame(indexes.length, dc.getSize());
        Assertions.assertSame(indexes.length, dc.getIndexes().size());
    }

    @Test
    @DisplayName("Test StringDataCol(int[] v, String[] i), less indexes")
    public void testConstructorWithValuesAndLessIndexes() {
        String[] values = {"1", "2", "3"};
        String[] indexes = {"a", "b"};

        StringDataCol dc = new StringDataCol(values, indexes);

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
        StringDataCol dc = new StringDataCol();
        dc.add("Hello");
        // min(v.len, i.len) items are assigned
        Assertions.assertSame(1, dc.getSize());

        ArrayList<String> indexes = dc.getIndexes();
        Assertions.assertEquals("Hello", dc.get(indexes.get(0)));
    }

    @Test
    @DisplayName("Test add(int v, String i)")
    public void testAddValueWithIndex() {
        StringDataCol dc = new StringDataCol();
        String value = "Old string";
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
        String[] values = {"1", "2", "3", "4", "5"};
        String[] indexes = {"a", "b", "c", "d", "e"};

        String index = indexes[3];
        String newValue = "New value";

        StringDataCol dc = new StringDataCol(values, indexes);
        dc.add(newValue, index);
        // item successfully updated
        Assertions.assertEquals(newValue, dc.get(index));
        // keeps the same number of ids
        Assertions.assertSame(indexes.length, dc.getSize());
    }

    @Test
    @DisplayName("Test get(String i), existing")
    public void testGetExisting() {
        String[] values = {"1", "2", "3", "4", "5"};
        String[] indexes = {"a", "b", "c", "d", "e"};

        String value = values[1];
        String index = indexes[1];

        StringDataCol dc = new StringDataCol(values, indexes);

        String res = dc.get(index);
        Assertions.assertEquals(value, res);
    }

    @Test
    @DisplayName("Test get(String i), non existing")
    public void testGetNonExisting() {
        String[] values = {"1", "2", "3", "4", "5"};
        String[] indexes = {"a", "b", "c", "d", "e"};

        String index = "z";

        StringDataCol dc = new StringDataCol(values, indexes);

        String res = dc.get(index);
        Assertions.assertSame(null, res);
    }

    @Test
    @DisplayName("Test remove(String i), existing")
    public void testRemoveExisting() {
        String[] values = {"1", "2", "3", "4", "5"};
        String[] indexes = {"a", "b", "c", "d", "e"};

        String index = "b";

        StringDataCol dc = new StringDataCol(values, indexes);

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
        String[] values = {"1", "2", "3", "4", "5"};
        String[] indexes = {"a", "b", "c", "d", "e"};

        String index = "z";

        StringDataCol dc = new StringDataCol(values, indexes);
        Assertions.assertSame(values.length, dc.getSize());

        boolean res = dc.remove(index);
        Assertions.assertFalse(res);
    }

    @Test
    @DisplayName("Test toString(String i)")
    public void testToString() {
        String[] values = {"1", "2", "3", "4", "5"};
        StringDataCol dc = new StringDataCol(values);
        Assertions.assertEquals("1 2 3 4 5", dc.toString());
    }

    @Test
    @DisplayName("Test toString(String i), missing value")
    public void testToStringMissingCell() {
        String[] values = {"1", "2", "3", "4", "5"};
        String[] indexes = {"a", "b", "c", "d", "e"};
        StringDataCol dc = new StringDataCol(values, indexes);
        dc.remove(indexes[2]);
        Assertions.assertEquals("1 2 NaN 4 5", dc.toString());
    }

    @Test
    @DisplayName("Test toString(String i), empty column")
    public void testToStringEmptyCol() {
        StringDataCol dc = new StringDataCol();
        Assertions.assertEquals("Empty column", dc.toString());
    }

    @Test
    @DisplayName("Test max")
    public void testMax() {
        String[] values = {"zero", "un", "deux", "trois", "quatre", "cinq"};
        StringDataCol dc = new StringDataCol(values);
        dc.remove("4");
        Assertions.assertEquals("trois", dc.max());
    }

    @Test
    @DisplayName("Test min")
    public void testMin() {
        String[] values = {"zero", "un", "deux", "trois", "quatre", "cinq"};
        StringDataCol dc = new StringDataCol(values);
        dc.remove("1");
        Assertions.assertEquals("zero", dc.min());
    }

    @Test
    @DisplayName("Test isSorted, 0 elements")
    public void testIsSorted0() {
        StringDataCol dc = new StringDataCol();
        Assertions.assertTrue(dc.isSorted());
    }

    @Test
    @DisplayName("Test isSorted, 1 elements")
    public void testIsSorted1() {
        String[] values = {"Flacide"};
        String[] indexes = {"a"};
        StringDataCol dc = new StringDataCol(values, indexes);
        Assertions.assertTrue(dc.isSorted());
    }

    @Test
    @DisplayName("Test isSorted, 3 elements, not sorted")
    public void testIsSortedFalse() {
        String[] values = {"BurgerKing", "Abo", "Bazinga"};
        String[] indexes = {"a", "b", "c"};
        StringDataCol dc = new StringDataCol(values, indexes);
        Assertions.assertFalse(dc.isSorted());
    }

    @Test
    @DisplayName("Test isSorted, 3 elements, not sorted, 3rd letter")
    public void testIsSortedFalseHard() {
        String[] values = {"Whbt", "What", "Whct"};
        String[] indexes = {"a", "b", "c"};
        StringDataCol dc = new StringDataCol(values, indexes);
        Assertions.assertFalse(dc.isSorted());
    }

    @Test
    @DisplayName("Test isSorted, 3 elements, sorted")
    public void testIsSorted() {
        String[] values = {"Abc", "Bca", "Cab"};
        String[] indexes = {"a", "b", "c"};
        StringDataCol dc = new StringDataCol(values, indexes);
        Assertions.assertTrue(dc.isSorted());
    }

    @Test
    @DisplayName("Test sortByValue, no element")
    public void testSortEmpty() {
        StringDataCol dc = new StringDataCol();
        dc.sortByValue();
        Assertions.assertTrue(dc.isSorted());
    }

    @Test
    @DisplayName("Test sortByValue, 5 elems")
    public void testSort1() {
        String[] values = {"Xin Zhao", "Abtrox", "Aatrox", "Vayne", "Vel'Koz"};
        String[] indexes = {"a", "b", "c", "d", "e"};
        StringDataCol dc = new StringDataCol(values, indexes);
        dc.sortByValue();
        Assertions.assertTrue(dc.isSorted());
    }

    @Test
    @DisplayName("test concate at left all")
    public void testConcateLeftToAll() {
        String[] values = {"Xin Zhao", "Abtrox", "Aatrox", "Vayne", "Vel'Koz"};
        StringDataCol dc = new StringDataCol(values);
        dc.concatenateLeftToAll("Mr.");
        int label = 0;
        for (String valueTest : values) {
            Assertions.assertEquals("Mr." + valueTest, dc.get(Integer.toString(label)));
            label++;
        }
    }

    @Test
    @DisplayName("test concate at right all")
    public void testConcateRightToAll() {
        String[] values = {"Xin Zhao", "Abtrox", "Aatrox", "Vayne", "Vel'Koz"};
        StringDataCol dc = new StringDataCol(values);
        dc.concatenateRightToAll("...");
        int label = 0;
        for (String valueTest : values) {
            Assertions.assertEquals(valueTest + "...", dc.get(Integer.toString(label)));
            label++;
        }
    }

    @Test
    @DisplayName("test fill empty space")
    public void testFillEmptySpace() {
        String[] values = {"Xin Zhao", "Abtrox", "Aatrox", "Vayne", "Vel'Koz"};
        String[] valueGoal = {"Xin Zhao", "fill", "Aatrox", "Vayne", "fill"};
        StringDataCol dc = new StringDataCol(values);
        dc.remove("1");
        dc.remove("4");
        dc.fillEmptySpaceWith("fill");
        int label = 0;
        for (String valueTest : valueGoal) {
            Assertions.assertEquals(valueTest, dc.get(Integer.toString(label)));
            label++;
        }
    }

    @Test
    @DisplayName("test concat right with another datacol ")
    public void testConcateDatacolRight() {
        String[] values = {"Xin Zhao", "Abtrox", "Aatrox", "Vayne", "Vel'Koz"};
        String[] values2 = {"Mr.", "Mme.", "Mlle.", "Miss", "Mister"};
        StringDataCol dc = new StringDataCol(values);
        StringDataCol dc2 = new StringDataCol(values2);
        dc.concatenateRight(dc2);
        for (int label=0; label < values.length ; label++) {
            Assertions.assertEquals((values[label]+values2[label]), dc.get(Integer.toString(label)));
        }
    }

    @Test
    @DisplayName("test concat left with another datacol ")
    public void testConcateDatacolLeft() {
        String[] values = {"Xin Zhao", "Abtrox", "Aatrox", "Vayne", "Vel'Koz"};
        String[] values2 = {"Mr.", "Mme.", "Mlle.", "Miss", "Mister"};
        StringDataCol dc = new StringDataCol(values2);
        StringDataCol dc2 = new StringDataCol(values);
        dc.concatenateLeft(dc2);
        for (int label=0; label < values.length ; label++) {
            Assertions.assertEquals((values[label]+values2[label]), dc.get(Integer.toString(label)));
        }
    }
}
