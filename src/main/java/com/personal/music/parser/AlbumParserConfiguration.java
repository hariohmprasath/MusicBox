package com.personal.music.parser;

import com.personal.music.pojo.AlbumReaderConfiguration;
import com.personal.music.pojo.SongReaderConfiguration;

import java.util.List;

/**
 * Created by hrajagopal on 5/19/15.
 */
public class AlbumParserConfiguration {

    private AlbumReaderConfiguration albumReaderConfiguration;
    private SongReaderConfiguration songReaderConfiguration;
    private List<String> albumIndexNameCollection;

    public AlbumReaderConfiguration getAlbumReaderConfiguration() {
        return albumReaderConfiguration;
    }

    public void setAlbumReaderConfiguration(AlbumReaderConfiguration albumReaderConfiguration) {
        this.albumReaderConfiguration = albumReaderConfiguration;
    }

    public SongReaderConfiguration getSongReaderConfiguration() {
        return songReaderConfiguration;
    }

    public void setSongReaderConfiguration(SongReaderConfiguration songReaderConfiguration) {
        this.songReaderConfiguration = songReaderConfiguration;
    }

    public List<String> getAlbumIndexNameCollection() {
        return albumIndexNameCollection;
    }

    public void setAlbumIndexNameCollection(List<String> albumIndexNameCollection) {
        this.albumIndexNameCollection = albumIndexNameCollection;
    }
}
