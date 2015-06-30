package com.personal.music.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by hrajagopal on 5/19/15.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MediaConfiguration {
    private SplitterType splitterType;
    private String splitterValue;

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
