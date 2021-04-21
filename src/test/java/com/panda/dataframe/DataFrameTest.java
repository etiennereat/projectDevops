package com.panda.dataframe;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import com.panda.datacol.StringDataCol;

/**
 * Unit test for simple DoubleDataCol.
 */
public class DataFrameTest {

    @Test
    @DisplayName("Test show()")
    public void testshow() {
        DataFrame df = new DataFrame();
        String[] indexes1 = {"a", "b", "c", "d", "e"};
        String[] indexes2 = {"f", "g", "h", "i", "j"};
        String[] indexes3 = {"k", "l", "m", "n", "o"};
        String[] indexes4 = {"p", "q", "r", "s", "t"};
        String[] values1 = {"data1", "data2", "data3", "data4", "data5"};
        String[] values2 = {"data6", "data7", "data8", "data9", "data10"};
        String[] values3 = {"data11", "data12", "data13", "data14", "data15"};
        String[] values4 = {"data16", "data17", "data18", "data19", "data20"};
        StringDataCol dc1 = new StringDataCol(values1, indexes1);
        StringDataCol dc2 = new StringDataCol(values2, indexes2);
        StringDataCol dc3 = new StringDataCol(values3, indexes3);
        StringDataCol dc4 = new StringDataCol(values4, indexes4);

        ArrayList<String> dfindexes = new ArrayList<String>(5);
        dfindexes.add("A");
        dfindexes.add("B");
        dfindexes.add("C");
        dfindexes.add("D");
        dfindexes.add("E");
        df.indexes = dfindexes;


        df.addCol("Col1", dc1);
        df.addCol("Col2", dc2);
        df.addCol("Col3", dc3);
        df.addCol("Col4", dc4);

        df.show();
        assert(true);
    }

}