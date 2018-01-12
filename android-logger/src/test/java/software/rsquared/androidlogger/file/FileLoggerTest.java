//package com.rafalzajfert.androidlogger.file;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.File;
//
///**
// * Created by Rafał on 2015-12-16.
// */
//public class FileLoggerTest {
//    private File logFile;
//    private FileAppender logger;
//    private FileAppenderConfig loggerConfig;
//
//    @Before
//    public void setUp() throws Exception {
//        logFile = new File("dir/log.txt");
//        logFile.mkdirs();
//
//        loggerConfig = new FileAppenderConfig();
//
//        logger = new FileAppender();
//
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        logFile.delete();
//        logFile = null;
////        loggerConfig
//    }
//
//    @Test
//    public void testGetConfig() throws Exception {
//
//    }
//
//    @Test
//    public void testSetConfig() throws Exception {
//
//    }
//}