package com.panda.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaternMatcher {
    public static boolean isEmptyCase(String reg){
        String patternEmptyCase = "^\\s*[Nn][Aa][Nn]\\s*$|^$";
        Pattern r = Pattern.compile(patternEmptyCase);
        Matcher m = r.matcher(reg);
        return m.find();
    }
    public static boolean isDouble(String reg){
        String patternDouble = "^\\s*[1-9]*[0-9](.[0-9]*)?\\s*$";
        Pattern r = Pattern.compile(patternDouble);
        Matcher m = r.matcher(reg);
        return m.find();
    }
    public static boolean isInteger(String reg){
        String patternInt = "^\\s*[0-9]+\\s*$";
        Pattern r = Pattern.compile(patternInt);
        Matcher m = r.matcher(reg);
        return m.find();
    }
    public static boolean isBoolean(String reg){
        String patternBoolean = "^\\s*[tT]rue\\s*$|^\\s*FALSE\\s*$|^\\s*TRUE\\s*$|^\\s*[fF]alse\\s*$";
        Pattern r = Pattern.compile(patternBoolean);
        Matcher m = r.matcher(reg);
        return m.find();
    }
}
