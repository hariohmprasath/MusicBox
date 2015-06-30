package com.personal.music.parser;

import com.personal.music.pojo.AlbumJSON;
import com.personal.music.pojo.AlbumReaderConfiguration;
import com.personal.music.pojo.MediaConfiguration;
import com.personal.music.util.Utils;
import com.personal.music.wiki.WikiHelper;
import com.personal.music.yahoo.YahooImageSearch;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

/**
 * Created by hrajagopal on 5/19/15.
 */

public class AlbumParserUnit implements Callable<AlbumParserResponse> {

    private static final Logger logger = LoggerFactory.getLogger(AlbumParserUnit.class);

    private AlbumParserConfiguration albumParserConfiguration;

    public AlbumParserUnit(AlbumParserConfiguration albumParserConfiguration) {
        super();
        this.albumParserConfiguration = albumParserConfiguration;
    }

    @Override
    public AlbumParserResponse call() throws Exception {
        try {
            List<AlbumJSON> albumList = getAllAlbumsFromPage(albumParserConfiguration.getAlbumReaderConfiguration());
            List<AlbumJSON> albumCollection = new LinkedList<>();
            List<String> albumNameCollection = new LinkedList<>(albumParserConfiguration.getAlbumIndexNameCollection());

            // Check in the collection whether its already indexed if so then don't index or else index it
            if (albumList != null && albumList.size() > 0) {
                for (AlbumJSON albumJSON : albumList) {
                    String albumIndexName = getAlbumIndexName(albumJSON.getAlbumName());
                    if (!albumNameCollection.contains(albumIndexName)) {
                        Document doc = Jsoup.connect(albumJSON.getAlbumUrl()).get();
                        String imageUrl = null;
                        if (doc != null) {

                            // Get thumbnail URL for preview
                            imageUrl = Utils.getDataFromHtmlNode(doc.getAllElements().get(0), albumParserConfiguration.getAlbumReaderConfiguration().getSiteImage());
                            String key = StringEscapeUtils.unescapeXml(albumParserConfiguration.getSongReaderConfiguration().getList());
                            Elements listOfElements = doc.select(key);
                            if (listOfElements != null && listOfElements.size() > 0) {
                                List<String> songNameList = new LinkedList<>();
                                List<String> songUrlList = new LinkedList<>();

                                logger.info(" ----- " + albumJSON.getAlbumName() + " ----- ");


                                for (Element songItem : listOfElements) {
                                    String songName = Utils.getDataFromHtmlNode(songItem, albumParserConfiguration.getSongReaderConfiguration().getName());
                                    songName = getExtractMediaName(albumParserConfiguration.getSongReaderConfiguration(), songName);

                                    String songUrl = Utils.getDataFromHtmlNode(songItem, albumParserConfiguration.getSongReaderConfiguration().getUrl());

                                    if (songUrl != null) {
                                        songName = getExtractMediaName(albumParserConfiguration.getSongReaderConfiguration(), songName);
                                        logger.info(songName + " -- > " + songUrl);

                                        songNameList.add(songName);
                                        songUrlList.add(songUrl);
                                    }
                                }

                                albumJSON.setSongNameList(songNameList);
                                albumJSON.setSongUrlList(songUrlList);
                                albumJSON.setThumbNail(imageUrl);

                                // Get information related to movie from Wiki
                                WikiHelper.searchWiki(albumJSON, albumParserConfiguration.getAlbumReaderConfiguration());

                                // Do a yahoo image search only when we don't have image from actual site and Wiki
                                if (albumJSON.getThumbNail() == null) {
                                    StringBuilder identifier = new StringBuilder(albumJSON.getAlbumName());

                                    if (albumParserConfiguration.getAlbumReaderConfiguration().getImageSearchToken() != null
                                            && albumParserConfiguration.getAlbumReaderConfiguration().getImageSearchToken().trim().length() > 0) {
                                        identifier.append(" ");
                                        identifier.append(albumParserConfiguration.getAlbumReaderConfiguration().getImageSearchToken());
                                    }

                                    String imageSearchToken = identifier.toString();
                                    imageSearchToken = imageSearchToken.replaceAll(Pattern.quote(" "), "+");

                                    YahooImageSearch imageSearch = new YahooImageSearch();
                                    imageSearch.setSearchString(imageSearchToken);
                                    albumJSON.setThumbNail(imageSearch.getImageUrl());
                                }
                            }
                            albumCollection.add(albumJSON);

                            // Check and add it the collection
                            if (!albumParserConfiguration.getAlbumIndexNameCollection().contains(albumIndexName))
                                albumParserConfiguration.getAlbumIndexNameCollection().add(albumIndexName);
                        }
                    } else {
                        albumParserConfiguration.getAlbumIndexNameCollection().remove(albumIndexName);
                    }
                }

                AlbumParserResponse response = new AlbumParserResponse();
                response.setAlbumCollection(albumCollection);
                response.setIndexNameCollection(albumParserConfiguration.getAlbumIndexNameCollection());

                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private List<AlbumJSON> getAllAlbumsFromPage(AlbumReaderConfiguration albumReaderConfiguration) {
        if (albumReaderConfiguration != null) {
            try {
                logger.info("Executing the album configuration for URL " + albumReaderConfiguration.getSongUrl());
                Document doc = Jsoup.connect(albumReaderConfiguration.getSongUrl().trim()).get();
                if (doc != null) {
                    List<AlbumJSON> albumList = new LinkedList<>();
                    String key = StringEscapeUtils.unescapeXml(albumReaderConfiguration.getList());
                    Elements listOfElements = doc.select(key);
                    if (listOfElements != null && listOfElements.size() > 0) {
                        for (Element albumItem : listOfElements) {
                            String albumName = Utils.getDataFromHtmlNode(albumItem, albumReaderConfiguration.getName());
                            String albumUrl = Utils.getDataFromHtmlNode(albumItem, albumReaderConfiguration.getUrl());
                            albumName = getExtractMediaName(albumReaderConfiguration, albumName);

                            if (albumName != null && albumName.trim().length() > 0) {
                                logger.info("Adding album information " + albumName + " --> " + albumUrl);
                                AlbumJSON x = new AlbumJSON();
                                x.setAlbumName(albumName);
                                x.setId(albumName);
                                x.setAlbumUrl(albumUrl);
                                albumList.add(x);
                            }
                        }
                    }

                    return albumList;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private String getExtractMediaName(MediaConfiguration mediaConfiguration, String mediaName) {
        if (mediaConfiguration.getSplitterType() != null && mediaConfiguration.getSplitterValue() != null && mediaName != null) {
            SplitterConfiguration splitterConfiguration = new SplitterConfiguration();
            splitterConfiguration.setSplitterType(mediaConfiguration.getSplitterType());
            splitterConfiguration.setSplitterValue(mediaConfiguration.getSplitterValue());
            String updatedValue = SplitterUtil.parseAndSplit(mediaName, splitterConfiguration);
            if (updatedValue != null && updatedValue.trim().length() > 0)
                mediaName = updatedValue;

            return mediaName.trim();
        }
        return null;
    }

    private String getAlbumIndexName(String albumName) {
        if (albumName != null && albumName.trim().length() > 0) {
            return (albumName + "_Name");
        }

        return null;
    }
}
