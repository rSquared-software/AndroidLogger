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

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;

/**
 * @author Rafal Zajfert
 * @version 1.1.0 (29/04/2015)
 */
@SuppressWarnings("unused")
public abstract class BaseLoggerConfig<E extends BaseLoggerConfig> {
    protected Level level = Level.VERBOSE;
    protected String tag;
    protected Boolean logThrowableWithStackTrace;

    /**
     * Minimal {@link Level Level} of message to sent. Default it is {@link Level#VERBOSE}
     */
    @NonNull
    public Level getLevel() {
        return level;
    }

    /**
     * Minimal {@link Level Level} of message to sent. Default it is {@link Level#VERBOSE}
     */
    @SuppressWarnings("unchecked")
    public E setLevel(@NonNull Level level) {
        this.level = level;
        return (E) this;
    }

    /**
     * @return true if is allowed or level is not set
     */
    public boolean isLevelAllowed(@NonNull Level level) {
        return level.ordinal() >= this.level.ordinal();
    }

    /**
     * Tag, used to identify source of a log message<p>
     * <p>
     * You can also use auto generated values:<p>
     * {@link Logger#CLASS_NAME CLASS_NAME}<p>
     * {@link Logger#FULL_CLASS_NAME FULL_CLASS_NAME}<p>
     * {@link Logger#METHOD_NAME METHOD_NAME}<p>
     * {@link Logger#FILE_NAME FILE_NAME}<p>
     * {@link Logger#LINE_NUMBER LINE_NUMBER}<p>
     * {@link Logger#LEVEL LEVEL}<p>
     * {@link Logger#SHORT_LEVEL SHORT_LEVEL}<p>
     * {@link Logger#LEVEL LEVEL}<p>
     * {@link Logger#SHORT_LEVEL SHORT_LEVEL}<p>
     * {@link Logger#CURRENT_TIME CURRENT_TIME}<p>
     * {@link Logger#CODE_LINE CODE_LINE}
     */
    @SuppressWarnings("unchecked")
    public E setTag(@Nullable String tag) {
        this.tag = tag;
        return (E) this;
    }

    /**
     * Tag, used to identify source of a log message
     */
    @Nullable
    public String getTag() {
        return tag;
    }

    /**
     * If true throwable will be logged with message and full stack trace
     */
    @SuppressWarnings("unchecked")
    public E setLogThrowableWithStackTrace(@Nullable Boolean logThrowableWithStackTrace) {
        this.logThrowableWithStackTrace = logThrowableWithStackTrace;
        return (E) this;
    }

    /**
     * Returns true if full stack trace should be logged
     */
    @Nullable
    public Boolean isLogThrowableWithStackTrace() {
        return logThrowableWithStackTrace;
    }

    @CallSuper
    protected void read(@NonNull Map<String, String> config) {
        if (config.containsKey("level")){
            try{
                setLevel(Level.valueOf(config.get("level")));
            }catch (Exception e) {
                throw new IllegalArgumentException("Unknown level: " + config.get("level"));
            }
        }
        if (config.containsKey("tag")){
            setTag(config.get("tag"));
        }
        if (config.containsKey("logThrowableWithStackTrace")){
            setLogThrowableWithStackTrace(Boolean.parseBoolean(config.get("logThrowableWithStackTrace")));
        }
    }

}
