package com.personal.music.rest;

import com.personal.music.pojo.AlbumJSON;
import com.personal.music.solr.search.SearchObject;
import com.personal.music.solr.search.SearchService;
import com.personal.music.util.ApplicationContextUtils;
import com.sun.jersey.api.core.InjectParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by hrajagopal on 6/15/15.
 */
@Path("/searchService")
public class SearchWebService {

    private SearchService searchService;

    @GET
    @Path("/list")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public List<AlbumJSON> getDataFromServer(@InjectParam SearchObject searchObject) {
        return getSearchService().search(searchObject);
    }

    public SearchService getSearchService() {
        if (searchService == null)
            searchService = ApplicationContextUtils.getApplicationContext().getBean(SearchService.class);

        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }
}
