package com.panda.dataframe;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DataFrameTest {

    DataFrame myDataFrame;

    @Before
    public void initDataFrame(){
        myDataFrame = new DataFrame();
    }

    @Test
    public void countEmptyTest(){
        assertEquals("CountEmptyTest avec une DataFrame vide :",0,myDataFrame.getColsCount());
    }
    @Test
    public void countNotEmptyTest(){
        //Todo add col to dataframe with labels "test"
        //assertEquals("countNotEmptyTest avec une DataFrame de taille 1 :",1,myDataFrame.getColsCount());
    }

    @Test
    public void getLabelsEmptyTest(){
        ArrayList<String> goal = new ArrayList<String>();
        assertEquals("getLabelsEmptyTest avec une DataFrame vide :",goal,myDataFrame.getLabels());
    }
    @Test
    public void getLabelsNotEmptyTest(){
        //Todo add col to dataframe with labels "test"
        ArrayList<String> goal = new ArrayList<String>();
        goal.add("test");
        //assertEquals("getLabelsEmptyTest avec une DataFrame de taille 1 :",goal,myDataFrame.getLabels());
    }

    @Test
    public void removeColSuccesTest(){
        //Todo add col to dataframe with labels "test"
        //assertTrue("removeColSuccesTest avec une DataFrame de taille 1 :",myDataFrame.removeCol("test"));

    }
    @Test
    public void removeColFailTest(){
        //Todo add col to dataframe with labels "test"
        assertFalse("removeColFail1Test avec une DataFrame de taille 1 :",myDataFrame.removeCol("fake"));
    }

    @Test
    public void removeColFailEmptyTest(){
        assertFalse("removeColFailEmptyTest avec une DataFrame vide :",myDataFrame.removeCol("fake"));
    }

    @Test
    public void removeRowSuccesTest(){
        //Todo add col to dataframe with labels "test" et un index "check"
        //assertTrue("removeColSuccesTest avec une DataFrame de taille 1 :",myDataFrame.removeRow("check"));

    }
    @Test
    public void removeRowFailTest(){
        //Todo add col to dataframe with labels "test" et un index "check"
        assertFalse("removeColFail1Test avec une DataFrame de taille 1 :",myDataFrame.removeRow("fake"));
    }

    @Test
    public void removeRowFailEmptyTest(){
        assertFalse("removeColFailEmptyTest avec une DataFrame vide :",myDataFrame.removeCol("fake"));
    }



    @Test
    public void getIndexesEmptyTest(){
        ArrayList<String> goal = new ArrayList<String>();
        assertEquals("getIndexesEmptyTest avec une DataFrame vide :",goal,myDataFrame.getIndexes());
    }
    @Test
    public void getIndexesSize1Test(){
        //Todo add col to dataframe with labels "test" et ses indexes "1","2","3"
        ArrayList<String> goal = new ArrayList<String>();
        goal.add("1");
        goal.add("2");
        goal.add("3");

        //assertEquals("getIndexesNotEmptyTest avec une DataFrame de taille 1 :",goal,myDataFrame.getIndexes());
    }
    @Test
    public void getIndexesSize2Test(){
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
