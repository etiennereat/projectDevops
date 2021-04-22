package com.panda.utils;

import java.io.File;

public class CSVFileManager {

    /**
     * Open CSV file with verification
     * @param  path path of CSV to open it
     */
    public static File openCSV(String path) {
        File fileCSV = new File(path);
        String fileName = fileCSV.getName();
        int i = fileName.lastIndexOf('.');
        String extension = "";
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        assert (fileCSV.exists() && extension.equals("csv"));
        return fileCSV;
    }
}
