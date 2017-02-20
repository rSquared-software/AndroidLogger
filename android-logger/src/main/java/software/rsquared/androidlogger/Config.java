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

package software.rsquared.androidlogger;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Collection;

/**
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
@SuppressWarnings("unused")
public interface Config {

    boolean isLevelAllowed(@NonNull Level level);
    
    Level getLevel();
    
    String getTag();

    Boolean isLogThrowableWithStackTrace();
    
    Logger getLogger(String tag);
    
    Collection<Logger> getLoggers();

    Config removeLogger(@NonNull String tag);

    Config removeLogger(@NonNull Logger logger);

    Config removeAllLoggers();

    Config addLogger(@NonNull Logger logger);

    Config addLogger(@NonNull Logger logger, @NonNull String loggerTag);
    
    String getSeparator();

    String getThrowableSeparator();

    String getDatePattern();

    SimpleDateFormat getDateFormat();
}
