package com.personal.music.util;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by hrajagopal on 5/18/15.
 */
public class Utils {
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
}
