package com.personal.music.test;

import com.personal.music.pojo.AlbumJSON;
import com.personal.music.solr.SolrOperationWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hrajagopal on 5/21/15.
 */
public class TestSolrOperationWrapper extends BaseJUniteTester {

    public static final String TEST = "TEST";
    @Autowired
    private SolrOperationWrapper solrOperationWrapper;

    @Test
    public void testAddPrimitivesToDataStore() {
        List<String> types = new ArrayList<>();
        types.add("Test1");
        types.add("Test2");

        Assert.assertTrue(solrOperationWrapper.addPrimitivesToDataStore(types, TEST));
    }

    @Test
    public void testAddObjectsToDataStore() {
        List<AlbumJSON> albumList = new ArrayList<>();
        AlbumJSON albumJSON = new AlbumJSON();
        albumJSON.setId("AAA");
        albumJSON.setAlbumName("AAA");
        albumJSON.setAlbumUrl("http://www.AAA.com");

        List<String> songNameList = new ArrayList<>();
        List<String> songUrlList = new ArrayList<>();
        songNameList.add("Song1");
        songUrlList.add("http://www.Song1.com");
        albumJSON.setSongNameList(songNameList);
        albumJSON.setSongUrlList(songUrlList);

        albumList.add(albumJSON);
        Assert.assertTrue(solrOperationWrapper.addObjectsToDataStore(albumList));
    }
}
