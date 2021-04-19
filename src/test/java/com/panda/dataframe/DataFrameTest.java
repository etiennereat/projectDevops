package com.panda.dataframe;

import com.panda.datacol.DataCol;
import com.panda.datacol.DoubleDataCol;
import com.panda.datacol.IntegerDataCol;
import com.panda.datacol.StringDataCol;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DataFrameTest {

    DataFrame myDataFrame;

    @BeforeEach
    public void initDataFrame() {
        myDataFrame = new DataFrame();
    }

//    public boolean removeCol(String label);
//    public DataCol col(String label);

//    public ArrayList<String> getIndexes();
//    private void updateIndexes(ArrayList<String> indexes);
//    public boolean removeRow(String index);

//    public void show();
//    public void show(String index);

    @Test
    @DisplayName("Test DataFrame(), initial state")
    public void testConstructor() {
        DataFrame df = new DataFrame();
        Assertions.assertEquals(0, df.indexes.size());
        Assertions.assertEquals(0, df.labels.size());
        Assertions.assertEquals(0, df.table.size());
    }

    @Test
    @DisplayName("Test addCol(String l, DataCol col)")
    public void testAddColDataAndLabel() {
        DataCol dc = new IntegerDataCol(new int[]{1, 2, 3});
        DataFrame df = new DataFrame();

        df.addCol("integers", dc);
        Assertions.assertEquals(1, df.table.size());
        Assertions.assertEquals(1, df.labels.size());

        Assertions.assertSame(dc, df.col("integers"));

        df.addCol("another column", dc);

        Assertions.assertEquals(2, df.table.size());
        Assertions.assertEquals(2, df.labels.size());
    }

    @Test
    @DisplayName("Test addCol(DataCol col)")
    public void testAddCol() {
        DataCol dc = new DoubleDataCol(new double[]{1.0, 2.0, 3.0});
        DataFrame df = new DataFrame();

        df.addCol(dc);
        Assertions.assertEquals(1, df.getColsCount());

        dc = new DoubleDataCol(new double[]{4.0, 5.0, 6.0});
        boolean res = df.addCol(dc);

        Assertions.assertTrue(res);
        Assertions.assertEquals(2, df.table.size());
        Assertions.assertEquals(2, df.labels.size());
        Assertions.assertEquals(3, df.indexes.size());
    }


    @Test
    @DisplayName("Test addCol(String l, DataCol col), existing col")
    public void testAddColOnExistingLabel() {
        DataCol dc = new DoubleDataCol(new double[]{1.0, 2.0, 3.0});
        DataFrame df = new DataFrame();

        df.addCol("col", dc);
        Assertions.assertSame(dc, df.table.get("col"));

        DataCol newDc = new DoubleDataCol(new double[]{4.0, 5.0, 6.0});
        boolean res = df.addCol("col", newDc);
        // fail to insert new col
        Assertions.assertFalse(res);
        Assertions.assertSame(dc, df.table.get("col"));
    }

    @Test
    @DisplayName("Test getColsCount()")
    public void testGetColsCount() {
        DataCol dc = new DoubleDataCol(new double[]{1.0, 2.0, 3.0});
        DataFrame df = new DataFrame();

        df.table = new HashMap<>();
        df.table.put("key1", dc);
        Assertions.assertEquals(1, df.getColsCount());
    }

    @Test
    @DisplayName("Test getColsCount(), empty df")
    public void testGetColsCountOnEmpty() {
        DataFrame df = new DataFrame();
        Assertions.assertEquals(0, df.getColsCount());
    }

    @Test
    @DisplayName("Test getLabels(), empty df")
    public void tetGetLabelsOnEmpty() {
        DataFrame df = new DataFrame();
        Assertions.assertEquals(new ArrayList<>(), df.getLabels());
    }

    @Test
    @DisplayName("Test getLabels()")
    public void getLabelsNotEmptyTest() {
        DataCol doubleCol = new DoubleDataCol(new double[]{1.0, 2.0, 3.0});
        DataCol integerCol = new IntegerDataCol(new int[]{1, 2, 3});
        DataCol stringCol = new StringDataCol(new String[]{"a", "b", "c"});

        DataFrame df = new DataFrame();
        ArrayList<String> goal = new ArrayList<>(Arrays.asList("doube", "integer", "string"));

        df.addCol(goal.get(0), doubleCol);
        df.addCol(goal.get(1), integerCol);
        df.addCol(goal.get(2), stringCol);

        Assertions.assertEquals(goal, df.getLabels());
    }

    @Test
    @DisplayName("...")
    public void removeColSuccesTest() {
        //Todo add col to dataframe with labels "test"

        //assertTrue("removeColSuccesTest avec une DataFrame de taille 1 :",myDataFrame.removeCol("test"));

    }

    @Test
    @DisplayName("...")
    public void removeColFailTest() {
        //Todo add col to dataframe with labels "test"

        // assertFalse("removeColFail1Test avec une DataFrame de taille 1 :",myDataFrame.removeCol("fake"));
    }

    @Test
    @DisplayName("removeColFailEmptyTest avec une DataFrame vide")
    public void removeColFailEmptyTest() {
        Assertions.assertFalse(myDataFrame.removeCol("fake"));
    }

    @Test
    @DisplayName("...")
    public void removeRowSuccessTest() {
        // Todo add col to dataframe with labels "test" et un index "check"
        // assertTrue("removeColSuccesTest avec une DataFrame de taille 1 :",myDataFrame.removeRow("check"));

    }

    @Test
    @DisplayName("...")
    public void removeRowFailTest() {
        // Todo add col to dataframe with labels "test" et un index "check"
        // assertFalse("removeColFail1Test avec une DataFrame de taille 1 :",myDataFrame.removeRow("fake"));
    }

    @Test
    @DisplayName("...")
    public void removeRowFailEmptyTest() {
//        assertFalse("removeColFailEmptyTest avec une DataFrame vide :", myDataFrame.removeCol("fake"));
    }


    @Test
    @DisplayName("getIndexesEmptyTest avec une DataFrame vide")
    public void getIndexesEmptyTest() {
        Assertions.assertEquals(new ArrayList<>(), myDataFrame.getIndexes());
    }

    @Test
    @DisplayName("...")
    public void getIndexesSize1Test() {
        //Todo add col to dataframe with labels "test" et ses indexes "1","2","3"
        ArrayList<String> goal = new ArrayList<String>();
        goal.add("1");
        goal.add("2");
        goal.add("3");

        //assertEquals("getIndexesNotEmptyTest avec une DataFrame de taille 1 :",goal,myDataFrame.getIndexes());
    }

    @Test
    @DisplayName("...")
    public void getIndexesSize2Test() {
        //Todo add col to dataframe with labels "test" et ses indexes "1","2","3"
        //Todo add col to dataframe with labels "test2" et ses indexes "3","4"
        ArrayList<String> goal = new ArrayList<String>();
        goal.add("1");
        goal.add("2");
        goal.add("3");
        goal.add("4");

        //assertEquals("getIndexesNotEmptyTest avec une DataFrame de taille 2 :",goal,myDataFrame.getLabels());
    }

    //Todo test for addCol
}
