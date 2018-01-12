//package com.rafalzajfert.androidlogger;
//
//import android.os.Build;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.robolectric.RobolectricGradleTestRunner;
//
//import static junit.framework.Assert.assertEquals;
///**
// * Created by Rafał on 2015-12-14.
// */
//@org.robolectric.annotation._Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
//@RunWith(RobolectricGradleTestRunner.class)
//public class LoggerUtilsTest {
//
//    @Before
//    public void setup() {
//    }
//
//    @Test
//    public void testArray2String() throws Exception {
//        Long[] array = new Long[]{1l, 2l, null, 3l};
//        assertEquals("1; 2; null; 3", LoggerUtils.array2String("; ", array));
//        assertEquals("1,2,3,test", LoggerUtils.array2String(",", 1, 2, 3, "test"));
//    }
//
//    @Test
//    public void testGetStackTraceField() throws Exception {
//        assertEquals("StackTraceElement", LoggerUtils.getStackTraceField(LoggerUtils.StackTraceField.SIMPLE_CLASS_NAME));
//        assertEquals("com.rafalzajfert.androidlogger.LoggerUtilsTest", LoggerUtils.getStackTraceField(LoggerUtils.StackTraceField.FULL_CLASS_NAME));
//        assertEquals("testGetStackTraceField", LoggerUtils.getStackTraceField(LoggerUtils.StackTraceField.METHOD_NAME));
//        assertEquals("LoggerUtilsTest.java", LoggerUtils.getStackTraceField(LoggerUtils.StackTraceField.FILE_NAME));
//        // next test depends on line number.
//        assertEquals("40", LoggerUtils.getStackTraceField(LoggerUtils.StackTraceField.LINE_NUMBER));
//    }
//
//    @Test
//    public void testReplaceCodeLine() throws Exception {
//        assertEquals("a($FileName:$LineNumber)a", LoggerUtils.replaceCodeLine("a$CodeLinea"));
//        // next tests depends on line number.
//        assertEquals("a(LoggerUtilsTest.java:47)a", LoggerUtils.formatMessage("a$CodeLinea", Level.VERBOSE));
//        assertEquals("a(LoggerUtilsTest.java:48)a", LoggerUtils.formatTag("a$CodeLinea", Level.VERBOSE));
//    }
//
//    @Test
//    public void testFormatTag() throws Exception {
//    }
//
//    @Test
//    public void testFormatMessage() throws Exception {
//    }
//
//    @Test
//    public void testThrowableToString() throws Exception {
//
//    }
//
//    @Test
//    public void testGetApplicationContext() throws Exception {
//
//    }
//}
