//package com.rafalzajfert.androidlogger.file;
//
//import android.os.Build;
//import android.os.Environment;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//
//import com.rafalzajfert.androidlogger.BaseLoggerConfig;
//import com.rafalzajfert.androidlogger.BuildConfig;
//import com.rafalzajfert.androidlogger.Level;
//import com.rafalzajfert.androidlogger.Logger;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.robolectric.RobolectricGradleTestRunner;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNotSame;
//import static org.junit.Assert.assertTrue;
//
///**
// * Created by Rafał on 2015-12-14.
// */
//@org.robolectric.annotation.Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
//@RunWith(RobolectricGradleTestRunner.class)
//public class BaseFileLoggerTest {
//
//    private File logFileDir;
//    private File testLogFileDir;
//    private File logFile;
//    private BaseFileLogger logger;
//    private BaseFileLoggerConfig loggerConfig;
//
//    @Before
//    @SuppressWarnings("ResultOfMethodCallIgnored")
//    public void setUp() throws Exception {
//        logFileDir = new File("dir");
//        testLogFileDir = new File(logFileDir, "test");
//        logFile = new File(logFileDir, "log.txt");
//        testLogFileDir.mkdirs();
//
//        loggerConfig = new BaseFileLoggerConfig() {
//        };
//
//        logger = new BaseFileLogger() {
//            @Nullable
//            @Override
//            protected BaseLoggerConfig getConfig() {
//                return loggerConfig;
//            }
//        };
//    }
//
//    @After
//    @SuppressWarnings("ResultOfMethodCallIgnored")
//    public void tearDown() throws Exception {
//        logFile.delete();
//        logFile = null;
//        logFileDir.delete();
//        logFileDir = null;
//        testLogFileDir.delete();
//        testLogFileDir = null;
//        logger = null;
//    }
//
//    @Test
//    public void testPrint() throws Exception {
//        loggerConfig.setLogFile(logFile);
//        loggerConfig.setTag("testTag");
//        assertEquals(0, logFile.length());
//        logger.print(Level.VERBOSE, "test message");
//        assertTrue(logFile.length() > 0);
//
//        assertEquals("testTag test message", readFile(logFile));
//    }
//
//    // print method cannot throw an exception
//    @Test
//    public void testPrintException() throws Exception {
//        loggerConfig.setLogFile(testLogFileDir);
//        logger.print(Level.VERBOSE, "test message");
//    }
//
//    @Test
//    public void testGetMessage() throws Exception {
//        loggerConfig.setTag(Logger.LEVEL);
//        assertEquals("VERBOSE test message\n", logger.getMessage(Level.VERBOSE, "test message"));
//        assertEquals("DEBUG test message\n", logger.getMessage(Level.DEBUG, "test message"));
//        assertEquals("WARNING test message\n", logger.getMessage(Level.WARNING, "test message"));
//        assertEquals("ERROR test message\n", logger.getMessage(Level.ERROR, "test message"));
//    }
//
//    @Test
//    public void testWriteToFile() throws Exception {
//        logger.writeToFile(logFile, "log message");
//        assertEquals("log message", readFile(logFile));
//    }
//
//    // method cannot throw an exception
//    @Test
//    public void testWriteToFileException() throws Exception {
//        logger.writeToFile(testLogFileDir, "log message");
//    }
//
//    @Test
//    public void testClearLogFile() throws Exception {
//        loggerConfig.setLogFile(logFile);
//        logger.print(Level.VERBOSE, "test message");
//        assertTrue(logFile.length() > 0);
//
//        logger.clearLogFile();
//        assertEquals(0, logFile.length());
//    }
//
//    // method cannot throw an exception
//    @Test
//    public void testClearLogFileException() throws Exception {
//        loggerConfig.setLogFile(testLogFileDir);
//        logger.clearLogFile();
//    }
//
//    @Test
//    public void testCreateFileIfNeeded() throws Exception {
//        File f = logger.createFileIfNeeded();
//        assertNotNull(f);
//        assertTrue(f.exists());
//        assertEquals("logger.log", f.getName());
//    }
//
//    @Test(expected = IOException.class)
//    public void testCreateFileIfNeededDirectory() throws Exception {
//        loggerConfig.setLogFile(testLogFileDir);
//        logger.createFileIfNeeded();
//    }
//
//    @Test
//    public void testCreateFileIfNeededWithCustomFile() throws Exception {
//        loggerConfig.setLogFile(logFile);
//        File file = logger.createFileIfNeeded();
//        assertNotNull(file);
//        assertTrue(file.exists());
//        assertEquals("log.txt", file.getName());
//        assertEquals(logFile, file);
//    }
//
//    @Test
//    public void testGetLogFile() throws Exception {
//        File file = logger.getLogFile();
//        assertNotNull(file);
//        assertEquals(new File(Environment.getExternalStorageDirectory(), BaseFileLogger.DEFAULT_LOG_FILE), file);
//        assertNotSame(logFile, file);
//    }
//
//    @Test
//    public void testGetLogFileWithCustomFile() throws Exception {
//        loggerConfig.setLogFile(logFile);
//        File file = logger.getLogFile();
//        assertNotNull(file);
//        assertEquals(logFile, file);
//        assertNotSame(new File(Environment.getExternalStorageDirectory(), BaseFileLogger.DEFAULT_LOG_FILE), file);
//    }
//
//
//    @NonNull
//    private static String readFile(File logFile) throws IOException {
//        StringBuilder total = new StringBuilder();
//        FileInputStream is = null;
//        try {
//            is = new FileInputStream(logFile);
//            BufferedReader r = new BufferedReader(new InputStreamReader(is));
//            String line;
//            while ((line = r.readLine()) != null) {
//                total.append(line);
//            }
//        } finally {
//            if (is != null) {
//                is.close();
//            }
//        }
//        return total.toString();
//    }
//}