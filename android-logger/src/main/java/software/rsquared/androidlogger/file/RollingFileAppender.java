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

package software.rsquared.androidlogger.file;

import java.io.File;
import java.io.IOException;

import software.rsquared.androidlogger.Logger;

/**
 * {@link Logger Logger} that save log messages in the
 * file<p>
 * default file is saved in root directory of the default external storage with name "logger.log"
 *
 * @author Rafal Zajfert
 */
@SuppressWarnings("unused")
public class RollingFileAppender extends BaseFileAppender  {

    protected RollingFileAppenderConfig config;

    public RollingFileAppender() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RollingFileAppenderConfig getConfig() {
        if (config == null) {
            config =  new RollingFileAppenderConfig();
        }
        return config;
    }


    @Override
    protected synchronized void writeToFile(File file, String string) {
        super.writeToFile(file, string);
        if (file.length() > config.getMaxFileSize() && config.getMaxFileSize() > 0 && config.getMaxFileBackupCount()>0) {
            rollOver(file);
        }
    }

    private boolean rollOver(File file) {
        int backupsCount = config.getMaxFileBackupCount();
        File f = new File(file.getAbsolutePath() + ".bac" + backupsCount);
        boolean success = true;
        if (f.exists()) {
            success = f.delete();
        }

        for (int i = backupsCount - 1; i > 0 && success; i--) {
            f = new File(file.getAbsolutePath() + ".bac" + i);
            if (f.exists()) {
                success = f.renameTo(new File(file.getAbsolutePath() + ".bac" + (i + 1)));
            }
        }

        if (success) {
            success = file.renameTo(new File(file.getAbsolutePath() + ".bac" + 1));
        }

        if (success){
            try {
                success = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                success = false;
            }
        }
        return success;
    }
}
