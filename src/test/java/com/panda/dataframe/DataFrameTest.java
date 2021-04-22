package com.panda.dataframe;

import com.panda.datacol.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DataFrameTest {

    DataFrame df;

    @BeforeEach
    public void initDataFrame() {
        df = new DataFrame();
    }


    @Test
    @DisplayName("Test DataFrame(), initial state")
    public void testConstructor() {
        Assertions.assertEquals(0, df.indexes.size());
        Assertions.assertEquals(0, df.labels.size());
        Assertions.assertEquals(0, df.table.size());
    }

    @Test
    @DisplayName("Test addCol(String l, DataCol col)")
    public void testAddColDataAndLabel() {
        DataCol dc = new IntegerDataCol(new int[]{1, 2, 3});

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

        df.table = new HashMap<>();
        df.table.put("key1", dc);
        Assertions.assertEquals(1, df.getColsCount());
    }

    @Test
    @DisplayName("Test getColsCount(), empty df")
    public void testGetColsCountOnEmpty() {
        Assertions.assertEquals(0, df.getColsCount());
    }

    @Test
    @DisplayName("Test getLabels(), empty df")
    public void testGetLabelsOnEmpty() {
        Assertions.assertEquals(new ArrayList<>(), df.getLabels());
    }

    @Test
    @DisplayName("Test getLabels()")
    public void getLabelsNotEmptyTest() {
        DataCol doubleCol = new DoubleDataCol(new double[]{1.0, 2.0, 3.0});
        DataCol integerCol = new IntegerDataCol(new int[]{1, 2, 3});
        DataCol stringCol = new StringDataCol(new String[]{"a", "b", "c"});

        ArrayList<String> goal = new ArrayList<>(Arrays.asList("doube", "integer", "string"));

        df.addCol(goal.get(0), doubleCol);
        df.addCol(goal.get(1), integerCol);
        df.addCol(goal.get(2), stringCol);

        Assertions.assertEquals(goal, df.getLabels());
    }

    @Test
    @DisplayName("Test df.removeCol(String l), success")
    public void testRemoveCol() {
        String[] labels = new String[]{"one", "two", "three"};
        DataCol[] cols = new DataCol[]{new IntegerDataCol(), new DoubleDataCol(), new StringDataCol()};

        for (int i = 0; i < labels.length; i++) {
            df.table.put(labels[i], cols[i]);
            df.labels.add(labels[i]);
        }

        for (int i = 0; i < labels.length; i++) {
            boolean res = df.removeCol(labels[i]);
            Assertions.assertTrue(res);
        }

        Assertions.assertSame(0, df.labels.size());
        Assertions.assertSame(0, df.table.size());
    }

    @Test
    @DisplayName("Test removeCol(String l), non existing col")
    public void testRemoveColNonExisting() {
        String[] labels = new String[]{"one", "two", "three"};
        DataCol[] cols = new DataCol[]{new IntegerDataCol(), new DoubleDataCol(), new StringDataCol()};

        for (int i = 0; i < labels.length; i++) {
            df.table.put(labels[i], cols[i]);
            df.labels.add(labels[i]);
        }

        boolean res = df.removeCol("non existing");
        Assertions.assertFalse(res);

        Assertions.assertSame(3, df.labels.size());
        Assertions.assertSame(3, df.table.size());
    }

    @Test
    @DisplayName("Test removeCol(String l), empty df")
    public void testRemoveColOnEmpty() {
        boolean res = df.removeCol("non existing");
        Assertions.assertFalse(res);
    }

    @Test
    @DisplayName("Test col(String l), empty df")
    public void testColOnEmpty() {
        DataCol res = df.col("non existing");
        Assertions.assertNull(res);
    }

    @Test
    @DisplayName("Test col(String l), non existing")
    public void testColNotExisting() {
        df.addCol("doubles", new DoubleDataCol());
        df.addCol("integers", new IntegerDataCol());

        DataCol res = df.col("non existing");
        Assertions.assertNull(res);
    }

    @Test
    @DisplayName("Test col(String l), success")
    public void testCol() {
        DataCol doubles = new DoubleDataCol();
        DataCol integers = new IntegerDataCol();

        df.table.put("doubles", doubles);
        df.table.put("integers", integers);
        df.labels.addAll(Arrays.asList("doubles", "integers"));

        DataCol res = df.col("doubles");
        Assertions.assertSame(doubles, res);
        res = df.col("integers");
        Assertions.assertSame(integers, res);
    }

    @Test
    @DisplayName("Test removeRow(String i), success")
    public void testRemoveRow() {
        String[] indexes = new String[]{"1", "2"};
        DataCol doubles = new DoubleDataCol(new double[]{1.0, 2.0}, indexes);
        DataCol integers = new IntegerDataCol(new int[]{1, 2}, indexes);

        df.addCol("doubles", doubles);
        df.addCol(integers);

        boolean res = df.removeRow(indexes[0]);

        Assertions.assertTrue(res);
        // there should be only one index/element left
        Assertions.assertEquals(1, df.col("doubles").getSize());
    }

    @Test
    @DisplayName("Test removeRow(String i), non existing index")
    public void removeRowFailTest() {
        String[] indexes = new String[]{"1", "2"};
        DataCol doubles = new DoubleDataCol(new double[]{1.0, 2.0}, indexes);
        DataCol integers = new IntegerDataCol(new int[]{1, 2}, indexes);

        df.addCol("doubles", doubles);
        df.addCol(integers);

        boolean res = df.removeRow("non existing");

        Assertions.assertFalse(res);
        // there should be no changes in the number of indexes, or their order
        Assertions.assertEquals(2, df.col("doubles").getSize());
        Assertions.assertEquals(new ArrayList<>(Arrays.asList(indexes)), df.getIndexes());
    }

    @Test
    @DisplayName("Test removeRow(String i), on empty df")
    public void testRemoveRowOnEmpty() {
        boolean res = df.removeRow("non existing");
        Assertions.assertFalse(res);
    }


    @Test
    @DisplayName("Test getIndexes(String i), on empty df")
    public void testGetIndexesOnEmpty() {
        Assertions.assertEquals(new ArrayList<>(), df.getIndexes());
    }

    @Test
    @DisplayName("Test getIndexes(String i)")
    public void testGetIndexes() {
        String[] indexes = new String[]{"a", "b", "c"};
        double[] data = new double[]{1.0, 2.0, 3.0};

        df.addCol("col", new DoubleDataCol(data, indexes));

        ArrayList<String> goal = new ArrayList<>(Arrays.asList(indexes));
        Assertions.assertEquals(goal, df.getIndexes());
    }

    @Test
    @DisplayName("Test addNewIndexes()")
    public void testAddNewIndexes() {
        String[] indexes = new String[]{"a", "b", "c"};
        ArrayList<String> newAddedIndexes = new ArrayList<>(Arrays.asList("a", "c", "d"));
        ArrayList<String> goal = new ArrayList<>(Arrays.asList("a", "b", "c", "d"));
        double[] data = new double[]{1.0, 2.0, 3.0};

        df.addCol("col", new DoubleDataCol(data, indexes));
        df.addNewIndexes(newAddedIndexes);

        // a,b,c - old indexes; a,c,d are inserted; only d is new;
        // expected a,b,c,d
        Assertions.assertEquals(goal, df.getIndexes());
    }

    @Test
    @DisplayName("Test Constructeur with wrong file ")
    public void testConstructeurCSVwrong_file() {
        Assertions.assertThrows(AssertionError.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new DataFrame("src/test/CSV/test_type.txt");
            }
        });
    }

    @Test
    @DisplayName("Test Constructeur with test_type.csv")
    public void testConstructeurCSVtest_type() {
        DataFrame frame = new DataFrame("src/test/CSV/test_type.csv");
        ArrayList<String> goalLabel = new ArrayList();
        goalLabel.add("label1");
        goalLabel.add("label2");
        goalLabel.add("label3");
        goalLabel.add("label4");
        for (String label : frame.getLabels()) {
            Assertions.assertTrue(goalLabel.contains(label));
        }
        for (String index : frame.getIndexes()) {
            Assertions.assertTrue(index.equals("index1"));
        }
        Assertions.assertEquals(ColType.INTEGER, frame.col("label1").getType());
        Assertions.assertEquals(ColType.STRING, frame.col("label2").getType());
        Assertions.assertEquals(ColType.BOOLEAN, frame.col("label3").getType());
        Assertions.assertEquals(ColType.DOUBLE, frame.col("label4").getType());
    }

    @Test
    @DisplayName("Test Constructeur with test_type_complexe.csv")
    public void testConstructeurCSVtest_type_complexe() {
        DataFrame frame = new DataFrame("src/test/CSV/test_type_complexe.csv");
        ArrayList<String> goalLabel = new ArrayList();
        goalLabel.add("label1");
        goalLabel.add("label2");
        goalLabel.add("label3");
        goalLabel.add("label4");
        for (String label : frame.getLabels()) {
            Assertions.assertTrue(goalLabel.contains(label));
        }
        ArrayList<String> goalIndex = new ArrayList();
        goalIndex.add("index1");
        goalIndex.add("index2");
        goalIndex.add("index3");
        for (String index : frame.getIndexes()) {
            Assertions.assertTrue(goalIndex.contains(index));
        }
        Assertions.assertEquals(ColType.DOUBLE, frame.col("label1").getType());
        Assertions.assertEquals(ColType.STRING, frame.col("label2").getType());
        Assertions.assertEquals(ColType.STRING, frame.col("label3").getType());
        Assertions.assertEquals(ColType.DOUBLE, frame.col("label4").getType());
    }

    @Test
    @DisplayName("Test Constructeur with test_type_complexe2.csv")
    public void testConstructeurCSVtest_type_complexe2() {
        DataFrame frame = new DataFrame("src/test/CSV/test_type_complexe2.csv");
        ArrayList<String> goalLabel = new ArrayList();
        goalLabel.add("label1");
        goalLabel.add("label2");
        goalLabel.add("label3");
        goalLabel.add("label4");
        for (String label : frame.getLabels()) {
            Assertions.assertTrue(goalLabel.contains(label));
        }
        ArrayList<String> goalIndex = new ArrayList();
        goalIndex.add("index1");
        goalIndex.add("index2");
        for (String index : frame.getIndexes()) {
            Assertions.assertTrue(goalIndex.contains(index));
        }
        Assertions.assertEquals(ColType.INTEGER, frame.col("label1").getType());
        Assertions.assertEquals(ColType.STRING, frame.col("label2").getType());
        Assertions.assertEquals(ColType.BOOLEAN, frame.col("label3").getType());
        Assertions.assertEquals(ColType.STRING, frame.col("label4").getType());
    }

    @Test
    @DisplayName("Test Constructeur with test_empty_case.csv")
    public void testConstructeurCSVtest_empty_case() {
        DataFrame frame = new DataFrame("src/test/CSV/test_empty_case.csv");
        ArrayList<String> goalLabel = new ArrayList();
        goalLabel.add("label1");
        goalLabel.add("label2");
        goalLabel.add("label3");
        goalLabel.add("label4");
        for (String label : frame.getLabels()) {
            Assertions.assertTrue(goalLabel.contains(label));
        }
        ArrayList<String> goalIndex = new ArrayList();
        goalIndex.add("index1");
        goalIndex.add("index2");
        goalIndex.add("index3");
        for (String index : frame.getIndexes()) {
            Assertions.assertTrue(goalIndex.contains(index));
        }
        Assertions.assertEquals(ColType.DOUBLE, frame.col("label1").getType());
        Assertions.assertEquals(ColType.STRING, frame.col("label2").getType());
        Assertions.assertEquals(ColType.BOOLEAN, frame.col("label3").getType());
        Assertions.assertEquals(ColType.STRING, frame.col("label4").getType());

    }


}
