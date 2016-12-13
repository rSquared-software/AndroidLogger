# Logger
Simple and useful Android logger library.

##Gradle Dependency (jCenter)

```Gradle
dependencies {
    compile 'com.rafalzajfert:android-logger:1.1.20'
}
```

[ ![Download](https://api.bintray.com/packages/rafalzajfert/maven/android-logger/images/download.svg) ](https://bintray.com/rafalzajfert/maven/android-logger/_latestVersion)

##Maven Dependency

```Maven
<dependency>
    <groupId>com.rafalzajfert</groupId>
    <artifactId>android-logger</artifactId>
    <version>1.1.20</version>
</dependency>
```

##Sample Usage

```java
//Send a message to all loggers defined in LoggerConfig
Logger.verbose("Verbose log");
Logger.verboseF("Formatted %s message: %.2f", "verbose", 3.1415f);
Logger.debug("Debug log");
Logger.debugF("Formatted %s message: %.2f", "debug", 3.1415f);
Logger.info("Info log");
Logger.infoF("Formatted %s message: %.2f", "info", 3.1415f);
Logger.warning("Warning log");
Logger.warningF("Formatted %s message: %.2f", "warning", 3.1415f);
Logger.error("Error log");
Logger.errorF("Formatted %s message: %.2f", "error", 3.1415f);
Logger.trace();

// log multi argument message
Logger.debug("User:", user.id, user.name);

// log throwable
try {
    ...
} catch (Throwable t){
    Logger.error("error", t);
}

//--------------------------------------------------------------

//Send message to the single logger
Logger logcat = new LogcatLogger();
logcat.v("Verbose log");
logcat.vF("Formatted %s message: %.2f", "verbose", 3.1415f);
logcat.d("Debug log");
logcat.dF("Formatted %s message: %.2f", "debug", 3.1415f);
logcat.i("Info log");
logcat.iF("Formatted %s message: %.2f", "info", 3.1415f);
logcat.w("Warning log");
logcat.wF("Formatted %s message: %.2f", "warning", 3.1415f);
logcat.e("Error log");
logcat.eF("Formatted %s message: %.2f", "error", 3.1415f);
logcat.t();
//--------------------------------------------------------------

// add logger to configuration
Logger.getBaseConfig().addLogger(new LogcatLogger());
Logger.getBaseConfig().addLogger("logcatTag", new LogcatLogger());

// get logger from configuration by tag
Logger logcatLogger = Logger.getBaseConfig().getLogger("logcatTag");

// remove logger from global configuration
Logger.getBaseConfig().removeLogger("fileLoggerTag");
Logger.getBaseConfig().removeLogger(logcatLogger);
```

##Configuration
###Base Configuration

Global configuration sets log parameters for all loggers from this library.  
New *LoggerConfig* is initialized with **default _LogcatLogger_**. If you want, you can remove it by calling *removeLogger(String)* method with *LoggerConfig.DEFAULT_LOGGER* tag

```java
LoggerConfig loggerConfig = new LoggerConfig()
        .removeLogger(LoggerConfig.DEFAULT_LOGGER)
        .setTag(Logger.CODE_LINE)
        .useANRWatchDog(new LoggableANRWatchDog(2000).preventCrash(true))
        .catchUncaughtExceptions()
        .setLevel(Level.VERBOSE)
        .setSeparator(Logger.SPACE)
        .setThrowableSeparator(Logger.NEW_LINE)
        .addLogger(fileLogger);

Logger.setBaseConfig(loggerConfig);
```
`setTag()` - Tag, used to identify source of a log message, default it's class name with line number  
You can also use auto generated values:  
*Logger.SIMPLE_CLASS_NAME  
Logger.CLASS_NAME  
Logger.METHOD_NAME  
Logger.FILE_NAME  
Logger.LINE_NUMBER  
Logger.LEVEL  
Logger.SHORT_LEVEL  
Logger.TIME_  
Logger.CODE_LINE*  

`setLevel()` - Minimal log level, all messages with level below this will be ignored.  
Levels order: **_VERBOSE_** < **_DEBUG_** < **_INFO_** < **_WARNING_** < **_ERROR_** < **_SILENT_**

`setSeparator` - String used to separate parts of message e.g. in method *Logger.debug(String...)*

`setThrowableSeparator` - String used to separate message and Throwable stack trace

`addLogger` - You can add more then one logger. For better management you can also set tag for each logger.  
This library provide four types of logger: **_LogcatLogger_**, **_FileLogger_**, **_TextViewLogger_**, **_ToastLogger_**.  
You can create custom logger by extending StandardLogger class.

###Properties Configuration

Now logger can by configured by the properties file

####Initializing
```java
LoggerConfig config = new LoggerConfig(R.raw.logger);
Logger.setBaseConfig(loggerConfig);
```

####Sample fully defined properties file 
*(undefined property will be initialized with default values)*
```properties
logger=logcat, textView, file, toast
logger.level=VERBOSE
logger.tag=$CodeLine$
logger.logThrowableWithStackTrace=true
logger.separator=\u0020
logger.throwableSeparator=\r\n
logger.datePattern=dd/MM/yyyy HH:mm:ss:SSS
logger.catchUncaughtExceptions=true
logger.useANRWatchDog=true

logger.textView=com.rafalzajfert.androidlogger.textview.TextViewLogger
logger.textView.level=INFO
logger.textView.tag=$CodeLine$
logger.textView.logThrowableWithStackTrace=true
logger.textView.inNewLine=true
logger.textView.printMethod=APPEND

logger.file=com.rafalzajfert.androidlogger.file.FileLogger
logger.file.level=WARNING
logger.file.tag=$ShortLevel$ $CurrentTime$ $CodeLine$
logger.file.logThrowableWithStackTrace=true
logger.file.externalFile=loggerLogs/$Date$/log.txt
logger.file.datePattern=dd_MM_yyyy

logger.logcat=com.rafalzajfert.androidlogger.logcat.LogcatLogger
logger.logcat.level=VERBOSE
logger.logcat.tag=$CodeLine$
logger.logcat.logThrowableWithStackTrace=true

logger.toast=com.rafalzajfert.androidlogger.toast.ToastLogger
logger.toast.level=ERROR
logger.toast.tag=$CodeLine$
logger.toast.logThrowableWithStackTrace=false
logger.toast.duration=SHORT
```

##Developed By

 * Rafal Zajfert - <rafal.zajfert@gmail.com>

##License

    Copyright 2015 Rafal Zajfert

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
