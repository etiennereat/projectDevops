package com.panda.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static com.panda.utils.PaternMatcher.*;


public class PaternMatcherTest {
    @Test
    @DisplayName("Test Int succes")
    public void testIntSucces() {
        Assertions.assertTrue(isInteger("1"));
        Assertions.assertTrue(isInteger("1 "));
        Assertions.assertTrue(isInteger(" 1"));
        Assertions.assertTrue(isInteger(" 1 "));
        Assertions.assertTrue(isInteger("123345678"));
    }

    @Test
    @DisplayName("Test Int fail")
    public void testIntFail() {
        Assertions.assertFalse(isInteger(""));
        Assertions.assertFalse(isInteger("  "));
        Assertions.assertFalse(isInteger("1.2"));
        Assertions.assertFalse(isInteger("salut1 "));
        Assertions.assertFalse(isInteger("salut 1"));
        Assertions.assertFalse(isInteger(" 1salut"));
        Assertions.assertFalse(isInteger(" 1 salut"));
        Assertions.assertFalse(isInteger("sa1lut"));
    }
    @Test
    @DisplayName("Test Boolean succes")
    public void testBooleanSucces() {
        Assertions.assertTrue(isBoolean("false"));
        Assertions.assertTrue(isBoolean("False"));
        Assertions.assertTrue(isBoolean("FALSE"));
        Assertions.assertTrue(isBoolean("True"));
        Assertions.assertTrue(isBoolean("true"));
        Assertions.assertTrue(isBoolean("TRUE"));
        Assertions.assertTrue(isBoolean("         TRUE"));
        Assertions.assertTrue(isBoolean("TRUE         "));
        Assertions.assertTrue(isBoolean("       TRUE         "));
    }

    @Test
    @DisplayName("Test Boolean fail")
    public void testBooleanFail() {
        Assertions.assertFalse(isBoolean("   "));
        Assertions.assertFalse(isBoolean(""));
        Assertions.assertFalse(isBoolean("truesalut"));
        Assertions.assertFalse(isBoolean("saluttrue"));
        Assertions.assertFalse(isBoolean("true salut"));
        Assertions.assertFalse(isBoolean("salut true"));
        Assertions.assertFalse(isBoolean("1"));
        Assertions.assertFalse(isBoolean("0"));
        Assertions.assertFalse(isBoolean("vrai"));
        Assertions.assertFalse(isBoolean("faux"));
    }
    @Test
    @DisplayName("Test double succes")
    public void testDoubleSucces() {
        Assertions.assertTrue(isDouble("1"));
        Assertions.assertTrue(isDouble("1 "));
        Assertions.assertTrue(isDouble(" 1"));
        Assertions.assertTrue(isDouble(" 1 "));
        Assertions.assertTrue(isDouble("1.23"));
        Assertions.assertTrue(isDouble("   1.23"));
        Assertions.assertTrue(isDouble("1.23   "));
        Assertions.assertTrue(isDouble("     1.23   "));
    }

    @Test
    @DisplayName("Test double fail")
    public void testDoubleFail() {
        Assertions.assertFalse(isDouble("1.0 salut"));
        Assertions.assertFalse(isDouble("salut.0 "));
        Assertions.assertFalse(isDouble("0.salut"));
        Assertions.assertFalse(isDouble("0.0.0"));
        Assertions.assertFalse(isDouble(".0"));
    }
    @Test
    @DisplayName("Test empty case succes")
    public void testEmptyCaseSucces() {
        Assertions.assertTrue(isEmptyCase("Nan"));
        Assertions.assertTrue(isEmptyCase("NAN "));
        Assertions.assertTrue(isEmptyCase("nAN"));
        Assertions.assertTrue(isEmptyCase("nan"));
        Assertions.assertTrue(isEmptyCase("nAn"));
        Assertions.assertTrue(isEmptyCase("naN"));
        Assertions.assertTrue(isEmptyCase("NaN"));
        Assertions.assertTrue(isEmptyCase("Nan      "));
        Assertions.assertTrue(isEmptyCase("         Nan      "));
        Assertions.assertTrue(isEmptyCase("       Nan"));
    }

    @Test
    @DisplayName("Test empty case fail")
    public void testEmptyCaseFail() {
        Assertions.assertFalse(isEmptyCase("Non"));
        Assertions.assertFalse(isEmptyCase("Nan Nan"));
        Assertions.assertFalse(isEmptyCase("Nansalut"));
        Assertions.assertFalse(isEmptyCase("salutNan"));

    }
}
