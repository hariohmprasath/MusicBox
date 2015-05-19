package com.personal.music;

import com.personal.music.elastic.ElasticSearchServer;
import com.personal.music.elastic.ElasticServices;
import com.personal.music.pojo.AlbumJSON;
import com.personal.music.pojo.AlbumReaderConfiguration;
import com.personal.music.pojo.Configuration;
import com.personal.music.pojo.PageConfiguration;
import com.personal.music.util.JAXBUtils;
import com.personal.music.util.ParserConfigurationUtil;
import com.personal.music.util.Utils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hrajagopal on 5/18/15.
 */
@Service
public class Crawler {

    @Autowired
    private ElasticServices elasticServices;

    public List<AlbumJSON> getAllIndexData(){
        List<AlbumJSON> albumJSONList = elasticServices.getAllDataFromIndexType(ElasticSearchServer.ALBUM, ElasticSearchServer.ALBUM_INDEX_TYPE, AlbumJSON.class);
        return albumJSONList;
    }

    public void parseAndIndexData() {
        Object response = JAXBUtils.convertStringToObject(ParserConfigurationUtil.getParserConfigStream(), Configuration.class);
        if (response != null && response instanceof Configuration) {
            Configuration configuration = (Configuration) response;
            for (PageConfiguration pageConfiguration : configuration.getPageConfiguration()) {
                if (pageConfiguration != null && pageConfiguration.getAlbumReaderConfigurations() != null) {
                    List<AlbumJSON> albumList = new LinkedList<AlbumJSON>();
                    for (AlbumReaderConfiguration albumReaderConfiguration : pageConfiguration.getAlbumReaderConfigurations()) {
                        getAllAlbumsFromPage(albumReaderConfiguration, albumList);
                    }

                    if (albumList.size() > 0)
                        elasticServices.insertBulkData(albumList, ElasticSearchServer.ALBUM, ElasticSearchServer.ALBUM_INDEX_TYPE);
                }
            }
        }
    }

    public void getAllAlbumsFromPage(AlbumReaderConfiguration albumReaderConfiguration, List<AlbumJSON> albumList) {
        if (albumReaderConfiguration != null) {
            try {
                Document doc = Jsoup.connect(albumReaderConfiguration.getSongUrl().trim()).get();
                if (doc != null) {
                    String key = StringEscapeUtils.unescapeXml(albumReaderConfiguration.getList());
                    Elements listOfElements = doc.select(key);
                    if (listOfElements != null && listOfElements.size() > 0) {
                        for (int i = 0; i < listOfElements.size(); i++) {
                            AlbumJSON x = new AlbumJSON();
                            Iterator<Element> itr = listOfElements.iterator();
                            while (itr.hasNext()) {
                                Element albumItem = itr.next();
                                String albumName = Utils.getDataFromHtmlNode(albumItem, albumReaderConfiguration.getName());
                                String albumUrl = Utils.getDataFromHtmlNode(albumItem, albumReaderConfiguration.getUrl());
                                x.setAlbumName(albumName);
                                x.setAlbumUrl(albumUrl);
                                albumList.add(x);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


