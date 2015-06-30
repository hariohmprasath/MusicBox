package com.personal.music.wiki;

import com.personal.music.pojo.AlbumJSON;
import com.personal.music.pojo.AlbumReaderConfiguration;
import com.personal.music.util.ConfigurationProvider;
import com.personal.music.util.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by hrajagopal on 5/22/15.
 */
public class WikiHelper {

    public static final String WIKI_BASE_URL = "http://en.wikipedia.org/wiki/%s";
    public static final String WIKI_PARSER = "http://en.wikipedia.org/w/api.php?action=query&list=search&srsearch=%s&format=xml";
    private static List<String> filmPageTokens = new LinkedList<>();

    private static Tidy tidy = null;
    private static XPath xPath = null;

    static {
        tidy = new Tidy();
        xPath = XPathFactory.newInstance().newXPath();

        // Load the movie page urls once and re-use it.
        String movieUrl = ConfigurationProvider.getConfigurationProperties().getProperty(ConfigurationProvider.MOVIE_URL);
        if (movieUrl != null && movieUrl.trim().length() > 0) {
            filmPageTokens = new LinkedList<>(Arrays.asList(movieUrl.split(",")));
        }
    }

    public static void searchWiki(AlbumJSON albumJSON, AlbumReaderConfiguration albumReaderConfiguration) {
        try {
            if (albumJSON != null && albumJSON.getAlbumName() != null && albumJSON.getAlbumName().trim().length() > 0
                    && albumReaderConfiguration.isWikiEnabled()) {
                StringBuilder searchTerm = new StringBuilder(albumJSON.getAlbumName());
                if (albumReaderConfiguration.getWikiSearchToken() != null && albumReaderConfiguration.getWikiSearchToken().trim().length() > 0) {
                    searchTerm.append(" ");
                    searchTerm.append(albumReaderConfiguration.getWikiSearchToken());
                }

                String formattedTxt = String.format(WIKI_PARSER, URLEncoder.encode(searchTerm.toString(), Utils.ENCODING));
                if (formattedTxt != null) {

                    //Get search results from WIKI
                    Document wikiDocument = getSourceFromUrl(formattedTxt);
                    if (wikiDocument != null) {
                        NodeList searchList = (NodeList) xPath.evaluate("/api/query/search/p", wikiDocument, XPathConstants.NODESET);
                        if (searchList != null && searchList.getLength() > 0) {
                            for (int i = 0; i < searchList.getLength(); i++) {
                                Node x = searchList.item(i);
                                String title = (String) xPath.evaluate("@title", x, XPathConstants.STRING);
                                if (title != null && title.trim().length() > 0) {
                                    title = title.replace(" ", "_");
                                    String docUrl = String.format(WIKI_BASE_URL, title);
                                    if (docUrl != null) {
                                        Document sourceDoc = getSourceFromUrl(docUrl);
                                        if (sourceDoc != null) {
                                            org.jsoup.nodes.Document htmlDom = getWikiExtract(sourceDoc);

                                            // Check the wiki page contains the movie information
                                            if (htmlDom != null && isValidMoviePage(htmlDom)) {
                                                Elements infoBox = htmlDom.select(".infobox > tbody > tr");
                                                if (infoBox != null) {
                                                    albumJSON.setWikiUrl(docUrl);
                                                    albumJSON.setDirector(WikiParserUtils.getDataFromExtract(infoBox, WikiParserUtils.DIRECTED_BY));
                                                    albumJSON.setProducer(WikiParserUtils.getDataFromExtract(infoBox, WikiParserUtils.PRODUCED_BY));
                                                    albumJSON.setWrittenBy(WikiParserUtils.getDataFromExtract(infoBox, WikiParserUtils.WRITTEN_BY));

                                                    String releaseDate = WikiParserUtils.getDataFromExtract(infoBox, WikiParserUtils.RELEASE_DATE);
                                                    albumJSON.setReleaseDate(Utils.getDate(releaseDate));

                                                    albumJSON.setMusic(WikiParserUtils.getDataFromExtract(infoBox, WikiParserUtils.MUSIC));
                                                    albumJSON.setActor(WikiParserUtils.getDataFromExtract(infoBox, WikiParserUtils.STARRING));

                                                    //Check we already have a album art if so then don't get it from wiki
                                                    if (albumJSON.getThumbNail() == null || albumJSON.getThumbNail().trim().length() == 0)
                                                        albumJSON.setThumbNail(WikiParserUtils.getImageFromWiki(infoBox));

                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Document getSourceFromUrl(String formattedTxt) {
        BufferedReader in = null;
        try {
            URL obj = new URL(formattedTxt);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return Utils.getDocument(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    private static org.jsoup.nodes.Document getWikiExtract(Document wikiDom) {
        if (wikiDom != null) {
            try {
                String wikiTextFragment = Utils.getXML(wikiDom.getDocumentElement());
                if (wikiTextFragment != null && wikiTextFragment.trim().length() > 0) {
                    StringWriter stringWriter = new StringWriter();
                    tidy.parse(Utils.getInputStream(wikiTextFragment), stringWriter);
                    wikiTextFragment = stringWriter.toString();
                    return Jsoup.parse(wikiTextFragment);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private static boolean isValidMoviePage(org.jsoup.nodes.Document document) {
        if (document != null) {
            Elements listOfExternalLinks = document.select(".external");
            if (listOfExternalLinks != null && listOfExternalLinks.size() > 0) {
                for (Object listOfExternalLink : listOfExternalLinks) {
                    Element x = (Element) listOfExternalLink;
                    if (("nofollow").equals(x.attr("rel"))) {
                        String href = x.attr("href");
                        if (href != null && href.trim().length() > 0) {
                            for (String token : getFilmPageTokens()) {
                                if (href.trim().contains(token.trim()))
                                    return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public static List<String> getFilmPageTokens() {
        return filmPageTokens;
    }
}
