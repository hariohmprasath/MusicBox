package com.personal.music.test;

import com.personal.music.pojo.AlbumJSON;
import com.personal.music.pojo.AlbumReaderConfiguration;
import com.personal.music.wiki.WikiHelper;
import org.junit.Test;

/**
 * Created by hrajagopal on 5/22/15.
 */
public class TestWikiHelper extends BaseJUniteTester {

    @Test
    public void testSearchWiki() {
        AlbumJSON albumJSON = new AlbumJSON();
        albumJSON.setAlbumName("Arya");

        AlbumReaderConfiguration readerConfiguration = new AlbumReaderConfiguration();
        readerConfiguration.setWikiEnabled(true);
        readerConfiguration.setWikiSearchToken("film");
        WikiHelper.searchWiki(albumJSON, readerConfiguration);
    }
}
