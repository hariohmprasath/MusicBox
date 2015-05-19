package com.personal.music.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by hrajagopal on 5/15/15.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PageConfiguration {
    private String name;
    private PageType pageType;
    private String url;

    @XmlElement(name = "albumReaderConfigurations")
    private List<AlbumReaderConfiguration> albumReaderConfigurations;
    private SongReaderConfiguration songReaderConfiguration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PageType getPageType() {
        return pageType;
    }

    public void setPageType(PageType pageType) {
        this.pageType = pageType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<AlbumReaderConfiguration> getAlbumReaderConfigurations() {
        return albumReaderConfigurations;
    }

    public void setAlbumReaderConfigurations(List<AlbumReaderConfiguration> albumReaderConfigurations) {
        this.albumReaderConfigurations = albumReaderConfigurations;
    }

    public SongReaderConfiguration getSongReaderConfiguration() {
        return songReaderConfiguration;
    }

    public void setSongReaderConfiguration(SongReaderConfiguration songReaderConfiguration) {
        this.songReaderConfiguration = songReaderConfiguration;
    }
}
