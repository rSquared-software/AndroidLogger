# Logger
Simple and useful Android logger library.

Gradle Dependency (jCenter)
=======

```Gradle
dependencies {
    compile 'com.rafalzajfert:android-logger:1.1.1'
}
```

[ ![Download](https://api.bintray.com/packages/rafalzajfert/maven/android-logger/images/download.svg) ](https://bintray.com/rafalzajfert/maven/android-logger/_latestVersion)

Maven Dependency
=======
```Maven
<dependency>
    <groupId>com.rafalzajfert</groupId>
    <artifactId>android-logger</artifactId>
    <version>1.1.1</version>
</dependency>
```

Sample Usage
=======

```java
//Send a message to all loggers defined in LoggerConfig
Logger.verbose("Verbose log");
Logger.debug("Debug log");
Logger.info("Info log");
Logger.warning("Warning log");
Logger.error("Error log");

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
logcat.d("Debug log");
logcat.i("Info log");
logcat.w("Warning log");
logcat.e("Error log");s
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

Configuration
=======

Base Configuration
-----------

Global configuration sets log parameters for all loggers from this library.
LoggerConfiguration is created with default LogcatLogger, if you want you can remove it by calling removeLogger method with LoggerConfig.DEFAULT_LOGGER tag

```java
LoggerConfig loggerConfig = new LoggerConfig()
        .removeLogger(LoggerConfig.DEFAULT_LOGGER)
        .setTag(Logger.PARAM_CODE_LINE)
        .useANRWatchDog(new ANRWatchDog(2000).preventCrash(true))
        .catchUncaughtExceptions()
        .setLevel(Level.VERBOSE)
        .setSeparator(Logger.PARAM_SPACE)
        .setThrowableSeparator(Logger.PARAM_NEW_LINE)
        .addLogger(fileLogger);

Logger.setBaseConfig(loggerConfig);
```
`setTag()` - Tag, used to identify source of a log message, default it's class name with line number
You can also use auto generated values:
Logger.PARAM_SIMPLE_CLASS_NAME
Logger.PARAM_CLASS_NAME
Logger.PARAM_METHOD_NAME
Logger.PARAM_FILE_NAME
Logger.PARAM_LINE_NUMBER
Logger.PARAM_LEVEL
Logger.PARAM_SHORT_LEVEL
Logger.PARAM_TIME
Logger.PARAM_CODE_LINE

`setLevel()` - Minimal log level, all messages with level below this will be ignored.
Levels order: VERBOSE < DEBUG < INFO < WARNING < ERROR < SILENT

`setSeparator` - String used to separate parts of message e.g. in method Logger.debug(String...)

`setThrowableSeparator` - String used to separate message and Throwable stack trace

`addLogger` - You can add more then one logger. For better management you can also set tag for each logger.
This library provide four types of logger: LogcatLogger, FileLogger, TextViewLogger, ToastLogger.
You can create custom logger by extending StandardLogger class.

Developed By
=======

 * Rafal Zajfert - <rafal.zajfert@gmail.com>

License
=======

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
