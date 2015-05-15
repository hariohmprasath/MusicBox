package com.personal.music;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by hrajagopal on 5/15/15.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SongReaderConfiguration {
    private String list;
    private String name;
    private String url;
    private SplitterType splitterType;
    private String splitterValue;

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

    public SplitterType getSplitterType() {
        return splitterType;
    }

    public void setSplitterType(SplitterType splitterType) {
        this.splitterType = splitterType;
    }

    public String getSplitterValue() {
        return splitterValue;
    }

    public void setSplitterValue(String splitterValue) {
        this.splitterValue = splitterValue;
    }
}
