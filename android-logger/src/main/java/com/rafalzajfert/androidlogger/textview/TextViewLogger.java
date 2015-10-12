/*
 * Copyright 2015 Rafal Zajfert
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rafalzajfert.androidlogger.textview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.rafalzajfert.androidlogger.ConfigSetter;
import com.rafalzajfert.androidlogger.Level;
import com.rafalzajfert.androidlogger.Logger;
import com.rafalzajfert.androidlogger.logcat.LogcatLogger;

/**
 * {@link Logger Logger} that send messages to Logcat
 * console
 *
 * @author Rafal Zajfert
 * @version 1.0.1 (15/04/2015)
 */
@SuppressWarnings("unused")
public class TextViewLogger extends Logger implements ConfigSetter<TextViewLoggerConfig> {

    private final Logger logger = new LogcatLogger();
    private TextViewLoggerConfig config = new TextViewLoggerConfig();

    @Nullable
    private TextView textView;

    public TextViewLogger(){
    }

    @SuppressWarnings("NullableProblems")
    public TextViewLogger(@NonNull TextView textView) {
        this.textView = textView;
    }

    public void setTextView(@Nullable TextView textView) {
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
        if (textView != null) {
            switch (config.getPrintMethod()) {
                case APPEND:
                    textView.append(getMessageSeparator(textView) + tag + PARAM_SPACE + message);
                    break;
                case OVERWRITE:
                    textView.setText(tag + PARAM_SPACE + message);
                    break;
                case PREPEND:
                    textView.setText(tag + PARAM_SPACE + message + getMessageSeparator(textView) + textView.getText());
                    break;
            }
        }else{
            logger.w("Text view for the logger is not initialized (call setTextView(TextView) method)");
        }
    }

    private String getMessageSeparator(@NonNull TextView textView) {
        if (textView.length() <= 0){
            return "";
        }
        if (config.isInNewLine()){
            return PARAM_NEW_LINE;
        }
        return PARAM_SPACE;
    }

}
