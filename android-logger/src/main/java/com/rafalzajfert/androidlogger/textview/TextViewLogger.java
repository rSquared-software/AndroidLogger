package com.rafalzajfert.androidlogger.textview;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.rafalzajfert.androidlogger.BaseLoggerConfig;
import com.rafalzajfert.androidlogger.Configurable;
import com.rafalzajfert.androidlogger.Level;
import com.rafalzajfert.androidlogger.Logger;

/**
 * {@link Logger Logger} that send messages to Logcat
 * console
 *
 * @author Rafal Zajfert
 * @version 1.0.1 (15/04/2015)
 */
@SuppressWarnings("unused")
public class TextViewLogger extends Logger implements Configurable<TextViewLoggerConfig> {

    private TextViewLoggerConfig config = new TextViewLoggerConfig();

    private final TextView textView;

    public TextViewLogger(TextView textView) {
        this.textView = textView;
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public TextViewLoggerConfig getConfig() {
        return config;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setConfig(@NonNull TextViewLoggerConfig config) {
        this.config = config;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void print(Level level, String message) {
        String tag = getTag(level);
        switch (config.getPrintMethod()) {
            case APPEND:
                textView.append(getMessageSeparator() + tag + PARAM_SPACE + message);
                break;
            case OVERWRITE:
                textView.setText(tag + PARAM_SPACE + message);
                break;
            case PREPEND:
                textView.setText(tag + PARAM_SPACE + message + getMessageSeparator() + textView.getText());
                break;
        }
    }

    private String getMessageSeparator() {
        if (textView.length() <= 0){
            return "";
        }
        if (config.isInNewLine()){
            return PARAM_NEW_LINE;
        }
        return PARAM_SPACE;
    }

}
