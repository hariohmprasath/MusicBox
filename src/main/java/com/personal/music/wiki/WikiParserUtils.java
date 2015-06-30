package com.personal.music.wiki;

import com.personal.music.util.Utils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hrajagopal on 5/22/15.
 */
public class WikiParserUtils {

    public static final String DIRECTED_BY = "Directed by";
    public static final String PRODUCED_BY = "Produced by";
    public static final String MUSIC = "Music by";
    public static final String RELEASE_DATE = "Release dates";
    public static final String WRITTEN_BY = "Written by";
    public static final String STARRING = "Starring";

    public static final String IMAGE_PATTERN = "tr > td > a > img";
    private static final List<String> KEY_PATTERNS = Arrays.asList("tr > th", "tr > th > div");
    private static final List<String> VALUE_PATTERNS = Arrays.asList("tr > td > a", "tr > td", "tr > td > div > ul > li");

    private static final Logger logger = LoggerFactory.getLogger(WikiParserUtils.class);

    public static String getDataFromExtract(List<Element> elements, String identifier) {
        if (elements != null && elements.size() > 0 && identifier != null) {
            for (Element x : elements) {
                String key = Utils.getDataFromHtmlNode(x, KEY_PATTERNS);
                if (identifier.equals(key)) {
                    return Utils.getDataFromHtmlNode(x, VALUE_PATTERNS);
                }
            }
        }

        return null;
    }

    public static String getImageFromWiki(List<Element> elements) {
        if (elements != null && elements.size() > 0) {
            for (Element x : elements) {
                Elements images = x.select(IMAGE_PATTERN);
                if (images != null && images.size() > 0) {
                    try {
                        return ("http:" + URLDecoder.decode(images.get(0).attr("src"), Utils.ENCODING));
                    } catch (Exception e) {
                        logger.error("Error in URL decoding: " + e.getMessage());
                    }
                }
            }
        }

        return null;
    }
}
