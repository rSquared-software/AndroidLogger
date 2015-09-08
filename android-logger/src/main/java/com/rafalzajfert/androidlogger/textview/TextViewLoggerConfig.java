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
