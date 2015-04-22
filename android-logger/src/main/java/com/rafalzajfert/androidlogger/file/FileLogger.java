package com.rafalzajfert.androidlogger.file;

import android.content.Context;
import android.util.Log;

import com.rafalzajfert.androidlogger.Level;
import com.rafalzajfert.androidlogger.StandardLogger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

/**
 * {@link com.rafalzajfert.androidlogger.Logger Logger} that save log messages in the
 * file<br>
 * default file is saved in application files directory
 *
 * @author Rafal Zajfert
 * @version 1.0.1 (15/04/2015)
 * @see {@link android.content.Context#getFilesDir() getFilesDir()}
 */
public class FileLogger extends StandardLogger {

	private static final String DEFAULT_PATH = "log.txt";

	private Context context;

	private FileLoggerConfig config;

	/**
	 * Configure logger properties
	 */
	public void setConfiguration(FileLoggerConfig config) {
		this.config = config;
	}

	/**
	 * Returns Logger configuration
	 */
	public FileLoggerConfig getConfiguration() {
		return this.config;
	}

	public FileLogger(Context context) {
		this.context = context;
		config = new FileLoggerConfig.Builder().build();
	}

	/**
	 * Get {@link java.io.File} where log messages are saved
	 */
	public File getLogFile() {
		return config.logFile == null ? getDefaultFile() : config.logFile;
	}

	private File getDefaultFile() {
		return new File(context.getFilesDir(), DEFAULT_PATH);
	}

	@Override
	protected String getTag() {
		if (config == null || config.tag == null) {
			return getFormattedTag();
		} else {
			return formatTag(config.getTag());
		}
	}

	@Override
	protected boolean canLogMessage(Level level) {
		return config == null || (config.isEnabled() && config.isLevelAllowed(level));
	}

	@Override
	protected void printError(String message) {
		appendFile("E", message);
	}

	@Override
	protected void printInfo(String message) {
		appendFile("I", message);
	}

	@Override
	protected void printDebug(String message) {
		appendFile("D", message);
	}

	@Override
	protected void printVerbose(String message) {
		appendFile("V", message);
	}

	@Override
	protected void printWarning(String message) {
		appendFile("W", message);
	}

	protected void appendFile(String type, String message) {
		String tag = getTag();
		RandomAccessFile file = null;
		try {
			checkLogFile();
			file = new RandomAccessFile(getLogFile(), "rw");
			String string = type + " " + getTime() + " " + tag + " " + message + "\r\n";
			file.seek(file.length());
			file.writeChars(string);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (file != null) {
					file.close();
				}
			} catch (IOException e) {
				Log.e(FileLogger.class.getSimpleName(), "Failed to close file", e);
			}
		}
	}

	public void clearLogFile() {
		RandomAccessFile file = null;
		try {
			checkLogFile();
			file = new RandomAccessFile(getLogFile(), "rw");
			file.setLength(0);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (file != null) {
					file.close();
				}
			} catch (IOException e) {
				Log.e(FileLogger.class.getSimpleName(), "Cannot create Log file", e);
			}
		}
	}

	private String getTime() {
		return config.writeTimeEnabled ? config.format.format(new Date()) + "" : "";
	}

	private void checkLogFile() {
		if (config.logFile == null) {
			config.logFile = getDefaultFile();
		}
		if (!config.logFile.exists()) {
			try {
				config.logFile.createNewFile();
			} catch (IOException e) {
				Log.e(FileLogger.class.getSimpleName(), "Cannot create Log file", e);
			}
		}
	}
}
