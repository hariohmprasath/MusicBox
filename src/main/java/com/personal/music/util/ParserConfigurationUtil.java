package com.personal.music.util;

/**
 * Created by hrajagopal on 5/18/15.
 */
public class ParserConfigurationUtil {

    public static final String PARSER_CONFIGURATION_XML = "ParserConfiguration.xml";
    public static String ENCODING = "UTF-8";

    private static String parserConfigStream = null;

    private ParserConfigurationUtil() {
        super();
        if (parserConfigStream == null)
            parserConfigStream = ResourceUtils.readResource(PARSER_CONFIGURATION_XML);
    }

    public static String getParserConfigStream() {
        if (parserConfigStream == null)
            new ParserConfigurationUtil();

        return parserConfigStream;
    }
}
