package com.personal.music.test;

import com.personal.music.solr.search.SearchObject;
import com.personal.music.solr.search.SearchService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by hrajagopal on 6/14/15.
 */
public class TestSearchService extends BaseJUniteTester {

    @Autowired
    private SearchService searchService;

    @Test
    public void testSearch() {
        SearchObject searchObject = new SearchObject();
        searchObject.setStart(1);
        searchObject.setNumberOfRecords(20);
        searchObject.setSearchTerm(null);
        searchObject.setSearchType("album");
        searchService.search(searchObject);
    }
}
