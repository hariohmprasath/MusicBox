package com.personal.music.pojo;

import org.apache.solr.client.solrj.beans.Field;

import java.util.List;

/**
 * Created by hrajagopal on 5/18/15.
 */
public class AlbumJSON {

    @Field
    public static final String STORE_TYPE = "album";

    @Field
    private String albumName;
    @Field
    private String albumUrl;

    @Field("songList")
    private List<SongJSON> songList;

    public List<SongJSON> getSongList() {
        return songList;
    }

    public void setSongList(List<SongJSON> songList) {
        this.songList = songList;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumUrl() {
        return albumUrl;
    }

    public void setAlbumUrl(String albumUrl) {
        this.albumUrl = albumUrl;
    }

    @Override
    public int hashCode() {
        return albumName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof AlbumJSON)
            return albumName.equals(((AlbumJSON) (obj)).albumName);
        return false;
    }
}
