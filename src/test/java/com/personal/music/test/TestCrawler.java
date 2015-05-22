package com.personal.music.test;

import com.personal.music.Crawler;
import com.personal.music.elastic.ElasticSearchServer;
import com.personal.music.elastic.ElasticServices;
import com.personal.music.pojo.AlbumJSON;
import com.personal.music.pojo.SongJSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hrajagopal on 5/18/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-appContext.xml"})
public class TestCrawler {

    @Autowired
    private Crawler crawler;

    @Autowired
    private ElasticServices elasticServices;

    @Test
    public void testCrawler() {
        crawler.parseAndIndexData();
    }

    @Test
    public void testBulkSave() {
        List<AlbumJSON> saveData = new LinkedList<>();
        AlbumJSON albumJSON = new AlbumJSON();
        albumJSON.setAlbumName("AAA");
        albumJSON.setAlbumUrl("www.html.com");
        List<SongJSON> songJSONList = new LinkedList<>();
        SongJSON song = new SongJSON();
        song.setSongName("NAME");
        song.setSongUrl("www.thl.com");
        songJSONList.add(song);
        song = new SongJSON();
        song.setSongName("NAME");
        song.setSongUrl("www.thl.com");
        songJSONList.add(song);
        song = new SongJSON();
        song.setSongName("NAME");
        song.setSongUrl("www.thl.com");
        songJSONList.add(song);

        albumJSON.setSongList(songJSONList);
        saveData.add(albumJSON);

        List<String> listOfAlbums = new LinkedList<>();
        listOfAlbums.add("AAA");
        listOfAlbums.add("AAA");

        elasticServices.insertBulkData(saveData, ElasticSearchServer.ALBUM, ElasticSearchServer.ALBUM_INDEX_TYPE);
        List<AlbumJSON> list = elasticServices.getAllDataFromIndexType(ElasticSearchServer.ALBUM, ElasticSearchServer.ALBUM_INDEX_TYPE, AlbumJSON.class);
        System.out.println(list);
        //elasticServices.insertBulkPrimitiveData(listOfAlbums, ElasticSearchServer.ALBUM, ElasticSearchServer.ALBUM_INDEX_NAME);
    }
}
