package com.rafalzajfert.androidlogger.textview;

import com.rafalzajfert.androidlogger.BaseLoggerConfig;

/**
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
@SuppressWarnings("unused")
public class TextViewLoggerConfig extends BaseLoggerConfig<TextViewLoggerConfig> {
    private boolean eachInNewLine = true;
    private Method printMethod = Method.APPEND;

    /**
     * Set {@link com.rafalzajfert.androidlogger.textview.TextViewLoggerConfig.Method} to print messages in the TextView
     */
    public TextViewLoggerConfig setPrintMethod(Method method) {
        this.printMethod = method;
        return this;
    }

    /**
     * Method to print messages in the TextView
     */
    public Method getPrintMethod() {
        return printMethod;
    }

    /**
     * If true all messages will be logged in new line
     */
    public TextViewLoggerConfig setEachInNewLine(boolean eachInNewLine) {
        this.eachInNewLine = eachInNewLine;
        return this;
    }

    /**
     * If true all messages will be logged in new line
     */
    public boolean isEachInNewLine() {
        return eachInNewLine;
    }

    public enum Method {
        APPEND, PREPEND, OVERWRITE
    }
}
