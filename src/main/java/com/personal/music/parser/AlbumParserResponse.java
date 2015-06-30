package com.personal.music.parser;

import com.personal.music.pojo.AlbumJSON;

import java.util.List;

/**
 * Created by hrajagopal on 5/19/15.
 */
public class AlbumParserResponse {

    private List<String> indexNameCollection;
    private List<AlbumJSON> albumCollection;

    public AlbumParserResponse() {
        super();
    }

    public List<String> getIndexNameCollection() {
        return indexNameCollection;
    }

    public void setIndexNameCollection(List<String> indexNameCollection) {
        this.indexNameCollection = indexNameCollection;
    }

    public List<AlbumJSON> getAlbumCollection() {
        return albumCollection;
    }

    public void setAlbumCollection(List<AlbumJSON> albumCollection) {
        this.albumCollection = albumCollection;
    }
}
