package com.panda.dataframe;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;

public class DataFrameTest {

    DataFrame myDataFrame;

    @BeforeEach
    public void initDataFrame() {
        myDataFrame = new DataFrame();
    }

    @Test
    @DisplayName("CountEmptyTest avec une DataFrame vide")
    public void countEmptyTest() {
        Assertions.assertEquals(0, myDataFrame.getColsCount());
    }

    @Test
    @DisplayName("...")
    public void countNotEmptyTest() {
        //Todo add col to dataframe with labels "test"
        //assertEquals("countNotEmptyTest avec une DataFrame de taille 1 :",1,myDataFrame.getColsCount());
    }

    @Test
    @DisplayName("getLabelsEmptyTest avec une DataFrame vide")
    public void getLabelsEmptyTest() {
        Assertions.assertEquals(new ArrayList<>(), myDataFrame.getLabels());
    }

    @Test
    @DisplayName("...")
    public void getLabelsNotEmptyTest() {
        //Todo add col to dataframe with labels "test"
        ArrayList<String> goal = new ArrayList<String>();
        goal.add("test");
        //assertEquals("getLabelsEmptyTest avec une DataFrame de taille 1 :",goal,myDataFrame.getLabels());
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
