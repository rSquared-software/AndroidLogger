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

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Map;

import software.rsquared.androidlogger.AppenderConfig;
import software.rsquared.androidlogger.Level;

/**
 * @author Rafal Zajfert
 */
@SuppressWarnings("unused")
public class TextViewAppenderConfig extends AppenderConfig<TextViewAppenderConfig> {


    private static final String COLOR_REGEX = "^#((([0-7a-fA-F])?([0-7a-fA-F]){3})|(([0-7a-fA-F]{2})?([0-7a-fA-F]{2}){3}))$";
    private boolean inNewLine = true;
    private Method printMethod = Method.APPEND;
    private ColorScheme colorScheme = ColorScheme.LIGHT;

    TextViewAppenderConfig() {
    }

    /**
     * Set {@link TextViewAppenderConfig.Method} to print messages in the TextView
     */
    public TextViewAppenderConfig setPrintMethod(Method method) {
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
    public TextViewAppenderConfig setInNewLine(boolean inNewLine) {
        this.inNewLine = inNewLine;
        return this;
    }

    /**
     * Color scheme for the log levels
     */
    @NonNull
    public ColorScheme getColorScheme() {
        return colorScheme;
    }

    /**
     * Color scheme for the log levels
     */
    public TextViewAppenderConfig setColorScheme(@NonNull ColorScheme colorScheme) {
        this.colorScheme = colorScheme;
        return this;
    }

    /**
     * If true each message will be logged in new line
     */
    public boolean isInNewLine() {
        return inNewLine;
    }

    @Override
    protected void read(@NonNull Map<String, String> config) {
        super.read(config);

        if (config.containsKey("inNewLine")) {
            setInNewLine(Boolean.parseBoolean(config.get("inNewLine")));
        }
        if (config.containsKey("printMethod")) {
            try {
                setPrintMethod(Method.valueOf(config.get("printMethod")));
            } catch (Exception e) {
                throw new IllegalArgumentException("Unknown print method: " + config.get("printMethod"));
            }
        }
        if (config.containsKey("colorScheme")) {
            String colorScheme = config.get("colorScheme");
            try {
                if (colorScheme.contains(",")) {
                    this.colorScheme = parseColorScheme(colorScheme);
                } else if ("dark".equalsIgnoreCase(colorScheme)) {
                    this.colorScheme = ColorScheme.DARK;
                } else if ("light".equalsIgnoreCase(colorScheme)) {
                    this.colorScheme = ColorScheme.LIGHT;
                } else {
                    this.colorScheme = ColorScheme.NONE;
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Unknown color scheme: " + colorScheme);
            }
        }
    }

    private ColorScheme parseColorScheme(String colorScheme) {
        String[] colors = colorScheme.split(",");
        if (colors.length == 5) {
            String colorVerbose = parseColor(colors[0], null);
            String colorDebug = parseColor(colors[1], null);
            String colorInfo = parseColor(colors[2], null);
            String colorWarning = parseColor(colors[3], null);
            String colorError = parseColor(colors[4], null);
            return new ColorScheme(colorVerbose, colorDebug, colorInfo, colorWarning, colorError);
        }
        return ColorScheme.NONE;
    }

    private String parseColor(String color, String defaultValue) {
        color = color.trim();
        if (color.matches(COLOR_REGEX)) {
            return color;
        } else if (TextUtils.isEmpty(defaultValue)) {
            return "#000000";
        } else {
            return defaultValue;
        }
    }

    public enum Method {
        APPEND, PREPEND, OVERWRITE
    }

    public static class ColorScheme {

        public static final ColorScheme NONE = new ColorScheme(
                "#000000",
                "#000000",
                "#000000",
                "#000000",
                "#000000");

        public static final ColorScheme LIGHT = new ColorScheme(
                "#000000",
                "#00007F",
                "#007F00",
                "#7F7F00",
                "#7F0000");

        public static final ColorScheme DARK = new ColorScheme(
                "#BBBBBB",
                "#6F84DD",
                "#68E168",
                "#E1E168",
                "#FF6868");

        private String colorVerbose;

        private String colorDebug;

        private String colorInfo;

        private String colorWarning;

        private String colorError;

        public ColorScheme(String colorVerbose, String colorDebug, String colorInfo, String colorWarning, String colorError) {
            this.colorVerbose = colorVerbose;
            this.colorDebug = colorDebug;
            this.colorInfo = colorInfo;
            this.colorWarning = colorWarning;
            this.colorError = colorError;
        }

        public String getColorString(Level level) {
            switch (level) {
                case VERBOSE:
                    return colorVerbose;
                case DEBUG:
                    return colorDebug;
                case INFO:
                    return colorInfo;
                case WARNING:
                    return colorWarning;
                case ERROR:
                    return colorError;
                case SILENT:
                default:
                    return "#000000";
            }
        }

        @ColorInt
        public int getColor(Level level) {
            return Color.parseColor(getColorString(level));
        }
    }
}
