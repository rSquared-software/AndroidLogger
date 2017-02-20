/*
 * Copyright 2017 rSquared s.c. R. Orlik, R. Zajfert
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

package software.rsquared.androidlogger.textview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import software.rsquared.androidlogger.ConfigSetter;
import software.rsquared.androidlogger.Level;
import software.rsquared.androidlogger.Logger;
import software.rsquared.androidlogger.logcat.LogcatLogger;

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

    public TextViewLogger() {
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
    @Nullable
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
        if (textView != null) {
            Spannable spannable = convertToSpannable(textView, level, message);

            if (TextViewLoggerConfig.Method.APPEND.equals(config.getPrintMethod())) {
                textView.append(spannable);
            } else {
                textView.setText(spannable);
            }
        } else {
            logger.w("Text view for the logger is not initialized (call setTextView(TextView) method)");
        }
    }

    @NonNull
    private Spannable convertToSpannable(@NonNull TextView textView, @NonNull Level level, String message) {
        String tag = getTag(level);
        String text;
        switch (config.getPrintMethod()) {
            case OVERWRITE:
                text = tag + SPACE + message;
                break;
            case PREPEND:
                text = tag + SPACE + message + getMessageSeparator(textView) + textView.getText();
                break;
            case APPEND:
            default:
                text = getMessageSeparator(textView) + tag + SPACE + message;
                break;

        }
        Spannable spannable = new SpannableString(text);
        spannable.setSpan(new ForegroundColorSpan(config.getColorScheme().getColor(level)), 0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    private String getMessageSeparator(@NonNull TextView textView) {
        if (textView.length() <= 0) {
            return "";
        }
        if (config.isInNewLine()) {
            return NEW_LINE;
        }
        return SPACE;
    }


}
