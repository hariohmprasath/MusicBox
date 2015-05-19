package com.personal.music.elastic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hrajagopal on 5/18/15.
 */
@Service
public class ElasticServices {
    public void getAllAlbums() {
        try {
            SearchResponse response = ElasticSearchServer.getElasticSearchClient().prepareSearch(ElasticSearchServer.ALBUM)
                    .setTypes(ElasticSearchServer.ALBUM_INDEX_TYPE)
                    .execute().actionGet();
            if (response != null) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean insertBulkData(List<?> listOfData, String indexName, String indexType) {
        try {
            if (listOfData != null && listOfData.size() > 0) {
                ObjectMapper mapper = new ObjectMapper();
                BulkRequestBuilder bulkRequest = ElasticSearchServer.getElasticSearchClient().prepareBulk();
                for (Object x : listOfData) {
                    byte[] json = mapper.writeValueAsBytes(x);
                    bulkRequest.add(ElasticSearchServer.getElasticSearchClient().
                            prepareIndex(indexName, indexType, String.valueOf(Math.random()))
                            .setSource(json));
                }

                BulkResponse bulkResponse = bulkRequest.execute().actionGet();
                return !bulkResponse.hasFailures();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
