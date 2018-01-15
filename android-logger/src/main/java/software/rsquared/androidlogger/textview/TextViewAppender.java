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

import software.rsquared.androidlogger.Appender;
import software.rsquared.androidlogger.ConfigurableAppender;
import software.rsquared.androidlogger.Level;
import software.rsquared.androidlogger.Logger;
import software.rsquared.androidlogger.logcat.LogcatAppender;

/**
 * @author Rafal Zajfert
 */
@SuppressWarnings("unused")
public class TextViewAppender extends Appender implements ConfigurableAppender<TextViewAppenderConfig> {
	private final Logger logger = Logger.createWith(new LogcatAppender());

	private TextViewAppenderConfig config;

	@Nullable
	private TextView textView;

	public TextViewAppender() {
	}

	public TextViewAppender(@Nullable TextView textView) {
		this.textView = textView;
	}

	@Override
	public TextViewAppenderConfig getConfig() {
		if (config == null) {
			config = new TextViewAppenderConfig();
		}
		return config;
	}

	public TextViewAppender setTextView(@Nullable TextView textView) {
		this.textView = textView;
		return this;
	}

	@Override
	protected void append(Level level, String tag, String message) {
        if (textView != null) {
            Spannable spannable = convertToSpannable(textView, level, tag, message);

            if (TextViewAppenderConfig.Method.APPEND.equals(config.getPrintMethod())) {
                textView.append(spannable);
            } else {
                textView.setText(spannable);
            }
        } else {
            logger.w("Text view in " + TextViewAppender.class.getSimpleName() + " is not initialized (call setTextView(TextView) method)");
        }

	}

    @NonNull
    private Spannable convertToSpannable(@NonNull TextView textView, @NonNull Level level, String tag, String message) {
        String text;
        switch (config.getPrintMethod()) {
            case OVERWRITE:
                text = tag + Logger.SPACE + message;
                break;
            case PREPEND:
                text = tag + Logger.SPACE + message + getMessageSeparator(textView) + textView.getText();
                break;
            case APPEND:
            default:
                text = getMessageSeparator(textView) + tag + Logger.SPACE + message;
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
            return Logger.NEW_LINE;
        }
        return Logger.SPACE;
    }


}
