package com.personal.music.util;

import java.util.Properties;

/**
 * Created by hrajagopal on 5/21/15.
 */
public class ConfigurationProvider {
    public static final String SOLR_URL = "SOLR_URL";
    public static final String MOVIE_URL = "MOVIE_PAGES";
    public static final String DATE_FORMAT = "DATE_FORMAT";
    public static final String CONFIGURATION_PROPERTY = "Configuration.properties";
    private static Properties configurationProperties = null;

    public ConfigurationProvider() {
        super();

        if (configurationProperties == null)
            configurationProperties = ResourceUtils.readProperty(CONFIGURATION_PROPERTY);
    }

    public static Properties getConfigurationProperties() {
        if (configurationProperties == null)
            new ConfigurationProvider();

        return configurationProperties;
    }
}
