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


/**
 * Level of {@link com.rafalzajfert.androidlogger.Logger Logger} used to limit logged
 * message types<br/>
 * <br/>
 * e.g if you set <code>LogLevel</code> as {@link #WARNING} then only massages
 * sent with level {@link #WARNING} or {@link #ERROR} will be sent, other will
 * be ignored<br/>
 * <br/>
 * <p/>
 * Level order: {@link #VERBOSE} < {@link #DEBUG} < {@link #INFO} <
 * {@link #WARNING} < {@link #ERROR} < {@link #SILENT}
 *
 * @author Rafal Zajfert
 * @version 1.0.0 (04/02/2015)
 */
@SuppressWarnings("unused")
public enum Level {
    /**
     * verbose {@link Level}, used in {@link Logger#verbose(Object)}
     */
    VERBOSE,
    /**
     * debug {@link Level}, used in {@link Logger#debug(Object)}
     */
    DEBUG,
    /**
     * info {@link Level}, used in {@link Logger#info(Object)}
     */
    INFO,
    /**
     * warning {@link Level}, used in {@link Logger#warning(Object)}
     */
    WARNING,
    /**
     * error {@link Level}, used in {@link Logger#error(Object)}
     */
    ERROR,
    /**
     * silent {@link Level} (none of massages will be sent)
     */
    SILENT
}
