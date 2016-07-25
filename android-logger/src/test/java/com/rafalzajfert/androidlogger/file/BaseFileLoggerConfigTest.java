package com.rafalzajfert.androidlogger.file;

import android.os.Build;
import android.os.Environment;

import com.rafalzajfert.androidlogger.BuildConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Rafał on 2015-12-15.
 */
@org.robolectric.annotation.Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class BaseFileLoggerConfigTest {
    private BaseFileLoggerConfig config;
    private BaseFileLoggerConfig config2;
    private File logFile;

    @Before
    public void setUp() throws Exception {
        logFile = new File("log.txt");
        config = new BaseFileLoggerConfig() {
        };
        config2 = new BaseFileLoggerConfig() {
            @Override
            public File getLogFile() {
                return logFile;
            }
        };
    }

    @After
    public void tearDown() throws Exception {
        logFile = null;
        config = null;
    }

    @Test
    public void testGetLogFile() throws Exception {
        assertNull(config.getLogFile());
        assertEquals(logFile, config2.getLogFile());
    }

    @Test
    public void testSetLogFile() throws Exception {
        assertNull(config.getLogFile());
        config.setLogFile(logFile);
        assertNotNull(config.getLogFile());
        assertEquals(logFile, config.getLogFile());
        config.setLogFile((File)null);
        assertNull(config.getLogFile());
    }

    @Test
    public void testSetLogFileStringPath() throws Exception {
        assertNull(config.getLogFile());
        config.setLogFile("test");
        assertNotNull(config.getLogFile());
        assertEquals("test", config.getLogFile().getName());
        config.setLogFile((String) null);
        assertNull(config.getLogFile());
    }

    @Test
    public void testSetExternalLogFile() throws Exception {
        assertNull(config.getLogFile());
        config.setExternalLogFile("test");
        assertNotNull(config.getLogFile());
        assertEquals(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "test", config.getLogFile().getAbsolutePath());
        config.setExternalLogFile(null);
        assertNull(config.getLogFile());
    }

    @Test
    public void testRead() throws Exception {
        Map<String, String> map = new HashMap<>();
        config.read(map);
        assertNull(config.getLogFile());
        assertEquals(BaseFileLoggerConfig.DATE_PATTERN, config.getDatePattern());

        map.put("externalFile", "test2");
        map.put("datePattern", "yyyy");
        config.read(map);

        assertNotNull(config.getLogFile());
        assertEquals(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "test2", config.getLogFile().getAbsolutePath());
        assertEquals("yyyy", config.getDatePattern());
    }

    @Test
    public void testGetSetDatePattern() throws Exception {
        assertEquals(BaseFileLoggerConfig.DATE_PATTERN, config.getDatePattern());
        config.setDatePattern("yyyy");
        assertEquals("yyyy", config.getDatePattern());
    }

    @Test
    public void testSetDatePattern() throws Exception {
        config.setLogFile("log_"+BaseFileLoggerConfig.DATE);
        assertTrue(config.getLogFile().getName().matches("log_\\d{2}_\\d{2}_\\d{4}"));
        config.setDatePattern("HH:mm:ss.SSS");
        assertTrue(config.getLogFile().getName().matches("log_\\d{2}:\\d{2}:\\d{2}\\.\\d{3}"));
        config.setDatePattern("HH:mm:ss");
        assertTrue(config.getLogFile().getName().matches("log_\\d{2}:\\d{2}:\\d{2}"));

        config.setLogFile(new File("log2_" + BaseFileLoggerConfig.DATE));
        assertTrue(config.getLogFile().getName().matches("log2_\\d{2}:\\d{2}:\\d{2}"));
    }

    @Test
    public void testGetSetDateFormat() throws Exception {
        DateFormat format= new SimpleDateFormat(BaseFileLoggerConfig.DATE_PATTERN);
        assertEquals(format, config.getDateFormat());
    }

    @Test
    public void testSetDateFormat() throws Exception {
        DateFormat format;
        config.setLogFile("log_"+BaseFileLoggerConfig.DATE);
        assertTrue(config.getLogFile().getName().matches("log_\\d{2}_\\d{2}_\\d{4}"));
        format = new SimpleDateFormat("HH:mm:ss.SSS");
        config.setDateFormat(format);
        assertTrue(config.getLogFile().getName().matches("log_\\d{2}:\\d{2}:\\d{2}\\.\\d{3}"));
        format = new SimpleDateFormat("HH:mm:ss");
        config.setDateFormat(format);
        assertTrue(config.getLogFile().getName().matches("log_\\d{2}:\\d{2}:\\d{2}"));

        config.setLogFile(new File("log2_" + BaseFileLoggerConfig.DATE));
        assertTrue(config.getLogFile().getName().matches("log2_\\d{2}:\\d{2}:\\d{2}"));

    }
}