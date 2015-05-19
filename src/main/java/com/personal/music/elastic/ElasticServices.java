package com.personal.music.elastic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hrajagopal on 5/18/15.
 */
@Service
public class ElasticServices {
    public <T> List<T> getAllDataFromIndexType(String indexName, String indexType, Class<T> objectClass) {
        if (indexName != null && indexType != null && objectClass != null) {
            try {
                SearchResponse response = ElasticSearchServer.getElasticSearchClient().prepareSearch(indexName)
                        .setTypes(indexType)
                        .execute()
                        .actionGet();
                if (response != null && response.getHits().getTotalHits() > 0) {
                    ObjectMapper mapper = new ObjectMapper();
                    SearchHits searchHits = response.getHits();
                    List<T> responseList = new LinkedList<>();
                    for (SearchHit searchHit : searchHits.getHits()) {
                        responseList.add(mapper.readValue(searchHit.source(), objectClass));
                    }

                    return responseList;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
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
