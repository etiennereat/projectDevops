# Panda

![GitHub last commit (branch)](https://img.shields.io/github/last-commit/etiennereat/projectDevops/main)
![GitHub repo size](https://img.shields.io/github/repo-size/etiennereat/projectDevops)
![GitHub top language](https://img.shields.io/github/languages/top/etiennereat/projectDevops)
![codecov](https://codecov.io/gh/etiennereat/projectDevops/branch/development/graph/badge.svg?token=YRL0FVRI5K)

[comment]: <> ([![codecov]&#40;https://codecov.io/gh/etiennereat/projectDevops/branch/development/graph/badge.svg?token=YRL0FVRI5K&#41;]&#40;https://codecov.io/gh/etiennereat/projectDevops&#41;)

## Table of contents

1. [Overview](#overview)
2. [How to build](#how-to-build)
3. [How to test](#how-to-test)
4. [Usage examples](#usage-examples)
5. [Contribute](#contribute)
    1. [Bug reports](#bug-reports)
    2. [Git workflow](#git-workflow)

## Overview

**Panda** is a data analysis library for java. It supports flexible creation of tables -`DataFrame`, containing columns
of types `Integer`, `Double`, `String` or `Boolean`.

Supported operations on tables / rows / columns:

- print contents
- select ranges of columns or rows
- sort rows

Visit our [website](https://etiennereat.github.io/projectDevops/) in order to read the full documentation of the library.

## How to build

In order to compile the source code, execute :

```
mvn compile
```

## How to test

In order to execute all unit tests, execute :

```
mvn test
```

In order to generate `jacoco` code coverage report, execute :

```
mvn jacoco:report
```

The generated report can be found in the directory `target/site`.

## Usage examples

A simple _Hello world_ program :

```java
import com.panda.dataframe.DataFrame;
import com.panda.datacol.StringDataCol;

class Test {
    public static void main(String[] argv) {
        // create an empty DataFrame
        DataFrame df = new DataFrame();

        // create a column
        String[] data = new String[]{"hello", "world"};
        StringDataCol dc = new StringDataCol(data);

        // add column named 'text' to the DataFrame
        df.addCol("text", dc);

        // print the contents of the data frame
        df.show();
    }
}
```

You can create a `DataFrame` from a list of `DataCol`s :

```java
import com.panda.datacol.DataCol;
import com.panda.datacol.IntegerDataCol;
import com.panda.dataframe.DataFrame;

import java.util.ArrayList;
import java.util.Arrays;

class Test {
    public static void main(String[] argv) {
        ArrayList<DataCol> listOfColumns = new ArrayList<>();

        // add a column of type Integer
        listOfColumns.add(new IntegerDataCol());
        // add a few more columns...

        // create a DataFrame from a list of DataCol-s
        DataFrame df = new DataFrame(listOfColumns);

        // you can provide a custom list of column labels
        ArrayList<String> labels = new ArrayList<>();
        labels.add("col1");
        // add other column labels...

        // create a DataFrame with specific column labels and associated data
        DataFrame dfWithLabels = new DataFrame(listOfColumns, listOfLabels);

        // ...
    }
}
```

You can also create a `DataFrame` from a `.csv` file:

```java
import com.panda.dataframe.DataFrame;

class Test {
    public static void main(String[] argv) {
        // create a DataFrame from a .csv file
        DataFrame df = new DataFrame("path/to/file.csv");

        // ...
    }
}
```

## Contribute

### Bug reports

If you find a bug on the `main` or `development` branches, feel free to create a new issue with the tag `bug`. Please
give a relevant issue name, a short description of the detected bug, the steps to reproduce, what was the expected and
the actual behaviour.

### Git workflow

- branch `main` : the latest stable version of the library
- branch `development` : used for the development and test of new features
- branches `dev/*` : development of a specific new feature
- branches `fix/*` : for fixing bugs on the dev version
