# Logger
Simple Android logger library

Gradle Dependency (jCenter)
=======

```Gradle
dependencies {
    compile 'com.rafalzajfert:android-logger:1.0.3'
}
```

[ ![Download](https://api.bintray.com/packages/rafalzajfert/maven/android-logger/images/download.svg) ](https://bintray.com/rafalzajfert/maven/android-logger/_latestVersion)

Maven Dependency
=======
```Maven
<dependency>
    <groupId>com.rafalzajfert</groupId>
    <artifactId>android-logger</artifactId>
    <version>1.0.2</version>
</dependency>
```

Sample Usage
=======

```java
//Send a message to all loggers added in global configuration
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

//Send message to one logger
Logger logcat = new LogCatLogger();
logcat.v("Verbose log");
logcat.d("Debug log");
logcat.i("Info log");
logcat.w("Warning log");
logcat.e("Error log");

//--------------------------------------------------------------

// get logger from configuration by tag
LoggerConfig config = new LoggerConfig.Builder()
        .addLogger("logcatTag", new LogCatLogger()).build();
Logger.setGlobalConfiguration(config);

Logger logcatLogger = Logger.getGlobalConfiguration().getLogger("logcatTag");

// remove logger from global configuration
Logger.getGlobalConfiguration().removeLogger("fileLoggerTag");

Logger.getGlobalConfiguration().removeLogger(logcatLogger);
```

Configuration
=======

Global Configuration
-----------

Global configuration sets log parameters for all loggers from this library. If you do not set this configuration then
 it is set to use LogcatLogger with minimal Debug level.

```java
LoggerConfig config = new LoggerConfig.Builder()
        .tag(Logger.PARAM_CLASS_NAME + " (" + Logger.PARAM_LINE_NUMBER + ")")
        .enabled(true)
        .logLevel(Level.DEBUG)
        .separator(" ")
        .throwableSeparator("\n")
        .addLogger(fileLogger)
        .addLogger("logcatTag", logcatLogger).build();
Logger.setGlobalConfiguration(config);
```
`tag` - Tag, used to identify source of a log message, default it's class name with line number  
You can also use auto generated values:  
Logger.PARAM_SIMPLE_CLASS_NAME  
Logger.PARAM_CLASS_NAME  
Logger.PARAM_METHOD_NAME  
Logger.PARAM_FILE_NAME  
Logger.PARAM_LINE_NUMBER  

`enabled` - Enable or disable  loggers, if this parameters is set true no message will be send to loggers
 
`logLevel` - Minimal log level, all messages with level below this will be ignored.  
Levels order: VERBOSE < DEBUG < INFO < WARNING < ERROR < SILENT
  
`separator` - String used to separate parts of message e.g. in method Logger.debug(String...)

`throwableSeparator` - String used to separate message and Throwable stack trace

`addLogger` - You can add more then one logger and if you set custom global configuration then you should add at
least one instance of logger. For better management you can also set tag for each logger.  
This library provide four types of logger: LogCatLogger, FileLogger, TextViewLogger, ToastLogger.
You can create custom logger by extending StandardLogger class.

Developed By
=======

 * Rafal Zajfer - <rafal.zajfert@gmail.com>

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
