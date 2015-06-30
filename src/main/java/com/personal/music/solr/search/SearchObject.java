package com.personal.music.solr.search;

import javax.ws.rs.QueryParam;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by hrajagopal on 6/14/15.
 */
@XmlRootElement(name = "searchObject")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchObject {
    @QueryParam("searchType")
    private String searchType;

    @QueryParam("searchTerm")
    private String searchTerm;

    @QueryParam("start")
    private int start;

    @QueryParam("numberOfRecords")
    private int numberOfRecords;

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getNumberOfRecords() {
        return numberOfRecords;
    }

    public void setNumberOfRecords(int numberOfRecords) {
        this.numberOfRecords = numberOfRecords;
    }
}
