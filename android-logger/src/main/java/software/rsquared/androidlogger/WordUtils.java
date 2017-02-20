package software.rsquared.androidlogger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * <p>Operations on Strings that contain words.</p>
 * <p>
 * <p>This class tries to handle <code>null</code> input gracefully.
 * An exception will not be thrown for a <code>null</code> input.
 * Each method documents its behaviour in more detail.</p>
 *
 * @since 2.0
 */
class WordUtils {

    private WordUtils() {
        super();
    }


    public static String[] wrap(final String str, int wrapLength) {
        if (str == null) {
            return null;
        }
        if (wrapLength < 1) {
            wrapLength = 1;
        }
        Pattern patternToWrapOn = Pattern.compile("\\s");
        final int inputLineLength = str.length();
        int offset = 0;
        List<String> wrappedLines = new ArrayList<>();
        while (offset < inputLineLength) {
            int spaceToWrapAt = -1;
            Matcher matcher = patternToWrapOn.matcher(str.substring(offset, Math.min(offset + wrapLength + 1, inputLineLength)));
            if (matcher.find()) {
                if (matcher.start() == 0) {
                    offset += matcher.end();
                    continue;
                } else {
                    spaceToWrapAt = matcher.start();
                }
            }

            // only last line without leading spaces is left
            if (inputLineLength - offset <= wrapLength) {
                break;
            }

            while (matcher.find()) {
                spaceToWrapAt = matcher.start() + offset;
            }

            if (spaceToWrapAt >= offset) {
                wrappedLines.add(str.substring(offset, spaceToWrapAt+1));
                offset = spaceToWrapAt + 1;

            } else {
                // really long word or URL
                // wrap really long word one line at a time
                wrappedLines.add(str.substring(offset, wrapLength + offset));
                offset += wrapLength;

            }
        }

        // Whatever is left in line is short enough to just pass through
        wrappedLines.add(str.substring(offset));

        return wrappedLines.toArray(new String[0]);
    }
}