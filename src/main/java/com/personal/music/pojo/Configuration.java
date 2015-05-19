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
public class Configuration {
    @XmlElement(name = "pageConfiguration")
    private List<PageConfiguration> pageConfiguration;

    public List<PageConfiguration> getPageConfiguration() {
        return pageConfiguration;
    }

    public void setPageConfiguration(List<PageConfiguration> pageConfiguration) {
        this.pageConfiguration = pageConfiguration;
    }
}
