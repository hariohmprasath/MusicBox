package com.personal.music.util;


import com.personal.music.pojo.AlbumJSON;
import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hrajagopal on 5/18/15.
 */
public class Utils {

    public static final String ENCODING = "UTF-8";

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);
    private static List<String> dateFormatList;

    static {
        String dateFormats = ConfigurationProvider.getConfigurationProperties().getProperty(ConfigurationProvider.DATE_FORMAT);
        if (dateFormats != null && dateFormats.trim().length() > 0)
            dateFormatList = new LinkedList<>(Arrays.asList(dateFormats.split("@")));

    }

    public static String getDataFromHtmlNode(Element element, List<String> patterns) {
        if (element != null && patterns != null && patterns.size() > 0) {
            for (String x : patterns) {
                String value = getDataFromHtmlNode(element, x);
                if (value != null && value.trim().length() > 0)
                    return value;
            }
        }
        return null;
    }

    public static String getDataFromHtmlNode(Element element, String path) {
        if (element != null && path != null) {
            try {
                path = StringEscapeUtils.unescapeXml(path);
                Elements listOfElements = element.select(path);
                if (listOfElements != null && listOfElements.size() == 1) {
                    if (path.contains("[") && path.contains("]")) {
                        int startIndex = path.indexOf("[") + 1;
                        int endIndex = path.indexOf("]", startIndex);
                        if (startIndex != -1 && endIndex != -1) {
                            String attributeName = path.substring(startIndex, endIndex);
                            return listOfElements.first().attr(attributeName);
                        }
                    } else
                        return listOfElements.first().ownText();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static void logFailureInDataStoreOperation(String message) {
        if (message != null && message.trim().length() > 0) {
            logger.error("Error while performing data store operation: " + message);
        }
    }

    public static InputStream getInputStream(String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                return new ByteArrayInputStream(value.getBytes(ENCODING));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static Document getDocument(String xml) {
        if (xml != null && xml.trim().length() > 0) {
            try {
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                return db.parse(new InputSource(new ByteArrayInputStream(xml.getBytes(Utils.ENCODING))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getXML(Node node) {
        StringWriter sw = new StringWriter();
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(node), new StreamResult(sw));
            return StringEscapeUtils.unescapeHtml(sw.toString());
        } catch (Exception te) {
            te.printStackTrace();
        }
        return null;
    }

    public static String decode(String data) {
        if (data != null && data.trim().length() > 0) {
            try {
                data = data.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
                data = data.replaceAll("\\+", "%2B");
                data = URLDecoder.decode(data, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public static void saveToFile(List<AlbumJSON> albumList) {
        if (albumList != null && albumList.size() > 0) {
            try {
                File file = new File("/Users/hrajagopal/Desktop/sample.txt");
                if (!file.exists())
                    if (!file.createNewFile())
                        System.err.println("Error while creating the file. Try again");

                FileWriter writer = new FileWriter(file);
                for (AlbumJSON x : albumList) {
                    if (x.getReleaseDate() != null) {
                        writer.write(x.getReleaseDate().toString());
                        writer.write(System.getProperty("line.separator"));
                    } else
                        System.out.println(x.getAlbumName());
                }

                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Date getDate(String dateString) {
        if (dateString != null && dateString.trim().length() > 0 && dateFormatList != null && dateFormatList.size() > 0) {
            for (String x : dateFormatList) {
                if (dateString.contains("("))
                    dateString = dateString.split("\\(")[0];

                SimpleDateFormat formatter = new SimpleDateFormat(x.trim());
                try {
                    return formatter.parse(dateString);
                } catch (Exception e) {
                    //Don't do anything in case of parse error
                }
            }
        }

        return null;
    }
}
