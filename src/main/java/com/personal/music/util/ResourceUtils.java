package com.personal.music.util;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.Properties;

/**
 * Created by hrajagopal on 5/18/15.
 */
public class ResourceUtils {
    public static String readResource(String resource){
        if(resource!=null){
            try{
                InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
                if(inputStream!=null){
                    StringWriter writer = new StringWriter();
                    IOUtils.copy(inputStream, writer, ParserConfigurationUtil.ENCODING);
                    return writer.toString();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }

    public static Properties readProperty(String resource) {
        if (resource != null) {
            try {
                InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
                if (inputStream != null) {
                    Properties properties = new Properties();
                    properties.load(inputStream);
                    return properties;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
