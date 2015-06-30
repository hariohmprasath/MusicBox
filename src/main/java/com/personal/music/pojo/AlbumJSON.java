package com.personal.music.pojo;

import org.apache.solr.client.solrj.beans.Field;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * Created by hrajagopal on 5/18/15.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AlbumJSON {

    @Field
    @XmlElement
    private String storageType = "album";

    @Field
    private String id;

    @Field
    @XmlElement
    private String albumName;

    @Field
    @XmlElement
    private String producer;

    @Field
    @XmlElement
    private String director;

    @Field
    @XmlElement
    private Date releaseDate;

    @Field
    @XmlElement
    private String writtenBy;

    @Field
    @XmlElement
    private String music;

    @Field
    @XmlElement
    private String snippet;

    @Field
    @XmlElement
    private String albumUrl;

    @Field
    @XmlElement
    private String thumbNail;

    @Field
    @XmlElement
    private String actor;

    @Field
    @XmlElement
    private String wikiUrl;

    private List<String> songNameList;

    private List<String> songUrlList;

    public String getStorageType() {
        return storageType;
    }

    public String getWikiUrl() {
        return wikiUrl;
    }

    public void setWikiUrl(String wikiUrl) {
        this.wikiUrl = wikiUrl;
    }

    public String getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(String writtenBy) {
        this.writtenBy = writtenBy;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
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
    public void setSongNameList(List<String> songNameList) {
        this.songNameList = songNameList;
    }

    public List<String> getSongUrlList() {
        return songUrlList;
    }

    @Field("songUrlList")
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

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof AlbumJSON)
            return albumName.equals(((AlbumJSON) (obj)).albumName);
        return false;
    }

    @Override
    public String toString() {
        return albumName + ":" + thumbNail;
    }
}
