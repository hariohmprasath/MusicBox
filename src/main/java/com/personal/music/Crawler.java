package com.personal.music;

import com.personal.music.parser.AlbumParserConfiguration;
import com.personal.music.parser.AlbumParserResponse;
import com.personal.music.parser.AlbumParserUnit;
import com.personal.music.pojo.AlbumJSON;
import com.personal.music.pojo.AlbumReaderConfiguration;
import com.personal.music.pojo.Configuration;
import com.personal.music.pojo.PageConfiguration;
import com.personal.music.solr.SolrOperationWrapper;
import com.personal.music.util.JAXBUtils;
import com.personal.music.util.ParserConfigurationUtil;
import com.personal.music.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by hrajagopal on 5/18/15.
 */
@Service
public class Crawler {
    public static final int TIMEOUT = 1000000000;
    public static final String ALBUM_NAME_INDEX = "ALBUM_NAME_INDEX";

    private static final Logger logger = LoggerFactory.getLogger(Crawler.class);

    @Autowired
    private SolrOperationWrapper solrOperationWrapper;
    @Autowired
    private JAXBUtils jaxbUtils;

    public void parseAndIndexData() {
        Configuration configuration = jaxbUtils.convertStringToObject(ParserConfigurationUtil.getParserConfigStream(), Configuration.class);
        if (configuration != null) {

            // Get the existing album name index information from data store
            List<String> albumIndexNameCollection = solrOperationWrapper.getPrimitivesFromDataStore(Crawler.ALBUM_NAME_INDEX, 0, -1);
            if (albumIndexNameCollection == null)
                albumIndexNameCollection = new LinkedList<>();

            logger.debug("Album name index collection has " + albumIndexNameCollection.size() + " record(s)");

            for (PageConfiguration pageConfiguration : configuration.getPageConfiguration()) {
                try {
                    if (pageConfiguration != null && pageConfiguration.getAlbumReaderConfigurations() != null && pageConfiguration.getAlbumReaderConfigurations().size() > 0) {
                        List<AlbumJSON> albumCollection = new LinkedList<>();
                        logger.info("Indexing page configuration: " + pageConfiguration.getName() + " with URL " + pageConfiguration.getUrl());
                        ExecutorService executorService = Executors.newCachedThreadPool();
                        List<Callable<AlbumParserResponse>> listOfParsers = new ArrayList<>(pageConfiguration.getAlbumReaderConfigurations().size());
                        for (AlbumReaderConfiguration albumReaderConfiguration : pageConfiguration.getAlbumReaderConfigurations()) {
                            AlbumParserConfiguration parserConfiguration = new AlbumParserConfiguration();
                            parserConfiguration.setAlbumIndexNameCollection(new ArrayList<>(albumIndexNameCollection));
                            parserConfiguration.setAlbumReaderConfiguration(albumReaderConfiguration);
                            parserConfiguration.setSongReaderConfiguration(pageConfiguration.getSongReaderConfiguration());

                            AlbumParserUnit parserUnit = new AlbumParserUnit(parserConfiguration);
                            listOfParsers.add(parserUnit);
                        }

                        // Invoke them all and merge the results back before calling save to data store
                        logger.info("Invoking all the threads for " + pageConfiguration.getName());
                        List<Future<AlbumParserResponse>> results = executorService.invokeAll(listOfParsers, TIMEOUT, TimeUnit.MINUTES);
                        if (results.size() != 0) {
                            logger.info("Merging the results back");
                            albumIndexNameCollection.clear();
                            for (Future<AlbumParserResponse> x : results) {
                                if (x.get() != null) {
                                    AlbumParserResponse response = x.get();
                                    if (response != null && response.getAlbumCollection() != null && response.getIndexNameCollection() != null) {
                                        albumIndexNameCollection.addAll(response.getIndexNameCollection());
                                        albumCollection.addAll(response.getAlbumCollection());
                                    }
                                }
                            }
                        }

                        // Save the album collections back to data store
                        if (albumCollection.size() > 0) {
                            logger.info("Saving album information to data store for " + pageConfiguration.getName());
                            if (!solrOperationWrapper.addObjectsToDataStore(albumCollection))
                                Utils.logFailureInDataStoreOperation("Error while saving album collection for " + pageConfiguration.getName() + " " + albumCollection.size() + " albums not indexed");
                        }

                        // Save the album name index back to data store
                        if (albumIndexNameCollection.size() > 0) {
                            logger.info("Saving album name index information to data store for " + pageConfiguration.getName());
                            if (!solrOperationWrapper.addPrimitivesToDataStore(albumIndexNameCollection, Crawler.ALBUM_NAME_INDEX))
                                Utils.logFailureInDataStoreOperation("Error while saving album names for " + pageConfiguration.getName() + " " + albumCollection.size() + " names not indexed");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("Error while indexing data for " + pageConfiguration.getName() + " with error :" + e.getMessage());
                }
            }
        }

    }
}


