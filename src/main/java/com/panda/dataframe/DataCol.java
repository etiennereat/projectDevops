package com.panda.dataframe;

import java.util.ArrayList;

public interface DataCol {

    public void show();

    public ColType getType();

    public ArrayList<String> getIndexes();

    public int getSize();

    public boolean remove(String index);

    //Todo  : select first x or last x, sort, swap
}
