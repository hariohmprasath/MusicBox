package com.personal.music;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by hrajagopal on 5/15/15.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public enum PageType {
    ALBUM(1), PLAYLIST(2);

    private int value;

    PageType(int value) {
        this.value = value;
    }
}
