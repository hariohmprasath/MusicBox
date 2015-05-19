package com.personal.music.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by hrajagopal on 5/15/15.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public enum SplitterType {

    DELIMITER(1), REPLACEMENT(2), REGEX(3);

    private int value;

    SplitterType(int value){
        this.value = value;
    }
}
