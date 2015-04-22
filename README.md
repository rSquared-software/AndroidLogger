# Logger
Simple Android logger library

Gradle Dependency (jCenter)
=======

```Gradle
dependencies {
    compile 'com.rafalzajfert:android-logger:1.0.2'
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

Usage
=======
```java
new MaterialDialog.Builder(this)
        .title(R.string.title)
        .content(R.string.content)
        .positiveText(R.string.agree)
        .negativeText(R.string.disagree)
        .show();
```

Configuration
=======

Global Configuration
---

```java
LoggerConfig config = new LoggerConfig.Builder()
        .tag(Logger.PARAM_CLASS_NAME + " (" + Logger.PARAM_LINE_NUMBER + ")")
        .enabled(true)
        .logLevel(Level.DEBUG)
        .separator(" ")
        .throwableSeparator("\n")
        .addLogger(logger)
        .addLogger("logcatTag", logcatLogger).build();
Logger.setGlobalConfiguration(config);
```
`tag` - Tag, used to identify source of a log message, default it's class name with line number<br>

You can also use auto generated values:
Logger.PARAM_SIMPLE_CLASS_NAME
Logger.PARAM_CLASS_NAME
Logger.PARAM_METHOD_NAME
Logger.PARAM_FILE_NAME
Logger.PARAM_LINE_NUMBER

`enabled` - Enable or disable loggers, if this parameters is set true no message will be send to loggers
 
`logLevel` - Minimal log level, all messages with level below this will be ignored. Levels order: VERBOSE < DEBUG <
  INFO < WARNING < ERROR < SILENT
  
`separator` - String used to separate parts of message e.g. in method Logger.debug(String...)

`throwableSeparator` - String used to separate message and Throwable stack trace

`addLogger` - You can add more then one logger and if you set custom global configuration then you should add at 
least one. For better management you can also set tag for each logger. This library provide four types of logger:
LogCatLogger, FileLogger, TextViewLogger, ToastLogger. You can create custom logger by extend StandardLogger class.

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
