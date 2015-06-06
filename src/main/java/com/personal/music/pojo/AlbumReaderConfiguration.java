package com.personal.music.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by hrajagopal on 5/15/15.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AlbumReaderConfiguration extends MediaConfiguration {
    private String list;
    private String name;
    private String url;
    private String songUrl;
    private String imageSearchToken;
    private String wikiSearchToken;
    private boolean wikiEnabled;
    private String imageSearchEnabled;
    private String siteImage;

    public String getSiteImage() {
        return siteImage;
    }

    public void setSiteImage(String siteImage) {
        this.siteImage = siteImage;
    }

    public boolean isWikiEnabled() {
        return wikiEnabled;
    }

    public void setWikiEnabled(boolean wikiEnabled) {
        this.wikiEnabled = wikiEnabled;
    }

    public String getImageSearchEnabled() {
        return imageSearchEnabled;
    }

    public void setImageSearchEnabled(String imageSearchEnabled) {
        this.imageSearchEnabled = imageSearchEnabled;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageSearchToken() {
        return imageSearchToken;
    }

    public void setImageSearchToken(String imageSearchToken) {
        this.imageSearchToken = imageSearchToken;
    }

    public String getWikiSearchToken() {
        return wikiSearchToken;
    }

    public void setWikiSearchToken(String wikiSearchToken) {
        this.wikiSearchToken = wikiSearchToken;
    }
}
