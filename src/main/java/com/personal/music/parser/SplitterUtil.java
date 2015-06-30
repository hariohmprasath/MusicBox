package com.personal.music.parser;

/**
 * Created by hrajagopal on 5/19/15.
 */
public class SplitterUtil {

    public static String parseAndSplit(String value, SplitterConfiguration configuration) {
        if (value != null && configuration != null && configuration.getSplitterType() != null && configuration.getSplitterValue() != null) {
            switch (configuration.getSplitterType()) {
                case DELIMITER:
                    String[] delimitedValue = value.split(configuration.getSplitterValue());
                    if (delimitedValue.length > 0)
                        return delimitedValue[0];
            }
        }

        return null;
    }
}
