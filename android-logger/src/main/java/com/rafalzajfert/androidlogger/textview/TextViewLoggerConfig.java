package com.rafalzajfert.androidlogger.textview;

import com.rafalzajfert.androidlogger.BaseLoggerConfig;

/**
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
@SuppressWarnings("unused")
public class TextViewLoggerConfig extends BaseLoggerConfig<TextViewLoggerConfig> {
    private boolean inNewLine = true;
    private Method printMethod = Method.APPEND;

    /**
     * Set {@link TextViewLoggerConfig.Method} to print messages in the TextView
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
     * If true each message will be logged in new line
     */
    public TextViewLoggerConfig setInNewLine(boolean inNewLine) {
        this.inNewLine = inNewLine;
        return this;
    }

    /**
     * If true each message will be logged in new line
     */
    public boolean isInNewLine() {
        return inNewLine;
    }

    public enum Method {
        APPEND, PREPEND, OVERWRITE
    }
}
