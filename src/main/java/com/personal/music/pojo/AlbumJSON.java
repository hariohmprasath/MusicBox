package com.personal.music.pojo;

import org.apache.solr.client.solrj.beans.Field;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by hrajagopal on 5/18/15.
 */
@XmlRootElement
public class AlbumJSON {

    @Field
    @XmlElement
    public static final String storageType = "album";

    @Field
    private String id;

    @Field
    @XmlElement
    private String albumName;

    @Field
    @XmlElement
    private String albumUrl;
    private List<String> songNameList;
    private List<String> songUrlList;

    public static String getStorageType() {
        return storageType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getSongNameList() {
        return songNameList;
    }

    @Field("songNameList")
    @XmlElement(name = "songNameList")
    public void setSongNameList(List<String> songNameList) {
        this.songNameList = songNameList;
    }

    public List<String> getSongUrlList() {
        return songUrlList;
    }

    @Field("songUrlList")
    @XmlElement(name = "songUrlList")
    public void setSongUrlList(List<String> songUrlList) {
        this.songUrlList = songUrlList;
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
