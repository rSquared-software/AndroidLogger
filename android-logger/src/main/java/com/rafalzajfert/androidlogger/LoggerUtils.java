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

package com.rafalzajfert.androidlogger;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
abstract class LoggerUtils {

    @SafeVarargs
    static <T> String array2String(@NonNull String separator, @NonNull T... array) {
        StringBuilder builder = new StringBuilder();
        String str;
        for (int i = 0; i < array.length; i++) {
            if (i>0){
                builder.append(separator);
            }
            str = array[i] + "";
            builder.append(str);
        }
        return builder.toString();
    }


    static Map<String, String> getStackTraceFieldMap() {
        final StackTraceElement element = getStackTraceElement();
        if (element == null) {
            return null;
        }
        Map<String, String> stackMap = new HashMap<>();

        stackMap.put(Logger.PARAM_CLASS_NAME, getField(element, StackTraceField.SIMPLE_CLASS_NAME));
        stackMap.put(Logger.PARAM_FULL_CLASS_NAME, getField(element, StackTraceField.FULL_CLASS_NAME));
        stackMap.put(Logger.PARAM_METHOD_NAME, getField(element, StackTraceField.METHOD_NAME));
        stackMap.put(Logger.PARAM_FILE_NAME, getField(element, StackTraceField.FILE_NAME));
        stackMap.put(Logger.PARAM_LINE_NUMBER, getField(element, StackTraceField.LINE_NUMBER));

        return stackMap;
    }

    @Nullable
    static String getStackTraceField(@NonNull StackTraceField field) {
        StackTraceElement element = getStackTraceElement();
        if (element == null) {
            return null;
        }
        return getField(element, field);
    }

    @Nullable
    private static StackTraceElement getStackTraceElement() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (elements == null || elements.length <= 2) {
            return null;
        }

        for (int i = 2; i < elements.length; i++) {
            String className = elements[i].getClassName();
            if (!isLoggerClass(className)) {
                return elements[i];
            }
        }
        return null;
    }

    private static boolean isLoggerClass(String className) {
        return className.startsWith(BuildConfig.APPLICATION_ID) || classExtendLogger(className);
    }

    @NonNull
    private static String getField(@NonNull StackTraceElement element, @NonNull StackTraceField field) {
        switch (field) {
            case FULL_CLASS_NAME:
                return element.getClassName();
            case SIMPLE_CLASS_NAME:
                return getSimpleName(element.getClassName());
            case FILE_NAME:
                return element.getFileName();
            case METHOD_NAME:
                return element.getMethodName();
            case LINE_NUMBER:
                return element.getLineNumber() + "";
            default:
                return "";
        }
    }

    @NonNull
    private static String getSimpleName(@NonNull String className) {
        int idx = className.lastIndexOf('.');
        boolean canSubstring = idx >= 0 && idx < className.length();
        return canSubstring ? className.substring(idx + 1) : className;
    }

    private static boolean classExtendLogger(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return Logger.class.isAssignableFrom(clazz);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * @return tag with replaced constant tags
     */
    @NonNull
    static String formatTag(@NonNull String tag, @NonNull Level level) {
        Map<String, String> map = LoggerUtils.getStackTraceFieldMap();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                tag = tag.replace(entry.getKey(), entry.getValue());
            }
        }
        tag = tag.replace(Logger.PARAM_LEVEL, level.name());
        tag = tag.replace(Logger.PARAM_SHORT_LEVEL, level.name().substring(0, 1));
        tag = tag.replace(Logger.PARAM_TIME, Logger.getTime());
        return tag;
    }

    /**
     * @return message with replaced constant tags
     */
    @NonNull
    static String formatMessage(@NonNull Object msg) {
        if (!(msg instanceof String)) {
            msg = msg + "";
        }

        String message = (String) msg;
        if (TextUtils.isEmpty(message)) {
            return "";
        }

        Map<String, String> map = LoggerUtils.getStackTraceFieldMap();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                message = message.replace(entry.getKey(), entry.getValue());
            }
        }
        return message;
    }

    @NonNull
    static String throwableToString(@NonNull Throwable throwable, @NonNull BaseLoggerConfig config){
        Boolean stackTrace = config.isLogThrowableWithStackTrace();
        if (stackTrace != null && !stackTrace) {
            return throwable.getMessage();
        } else {
            return Log.getStackTraceString(throwable);
        }
    }

    static Context getApplicationContext() {
        try {
            return (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null, (Object[]) null);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot retrieve application context, please config logger programmatically");
        }
    }

    enum StackTraceField {
        SIMPLE_CLASS_NAME, FULL_CLASS_NAME, FILE_NAME, METHOD_NAME, LINE_NUMBER
    }
}
