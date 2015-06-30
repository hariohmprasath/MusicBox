package com.personal.music.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hrajagopal on 5/21/15.
 */
@Service
public class SolrOperationWrapper {

    public static final String ID = "id";
    public static final String IDENTIFIER = "identifier";
    public static final String CONTENT = "content";

    public boolean addPrimitivesToDataStore(List<String> dataCollection, String identifier) {
        if (dataCollection != null && dataCollection.size() > 0 && identifier != null) {
            try {
                List<SolrInputDocument> solrInputDocuments = new ArrayList<>();
                for (String x : dataCollection) {
                    SolrInputDocument doc = new SolrInputDocument();
                    doc.addField(ID, x);
                    doc.addField(CONTENT, x);
                    doc.addField(IDENTIFIER, identifier);
                    solrInputDocuments.add(doc);
                }

                SolrService.getSolrServer().add(solrInputDocuments);
                SolrService.getSolrServer().commit();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public <T> boolean addObjectsToDataStore(List<T> dataCollection) {
        if (dataCollection != null && dataCollection.size() > 0) {
            try {
                SolrService.getSolrServer().addBeans(dataCollection);
                SolrService.getSolrServer().commit();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public List<String> getPrimitivesFromDataStore(String identifier, int start, int limit) {
        if (identifier != null && identifier.trim().length() > 0) {
            try {
                SolrQuery solrQuery = new SolrQuery();
                solrQuery.setQuery(IDENTIFIER + ":" + identifier);
                if (limit != -1) {
                    solrQuery.setStart(start);
                    solrQuery.setRows(limit);
                }
                QueryResponse queryResponse = SolrService.getSolrServer().query(solrQuery);
                if (limit == -1 && queryResponse != null && queryResponse.getResults() != null) {
                    solrQuery.setRows((int) queryResponse.getResults().getNumFound());
                    queryResponse = SolrService.getSolrServer().query(solrQuery);
                }


                if (queryResponse != null) {
                    List<String> response = new ArrayList<>();
                    for (SolrDocument resultDoc : queryResponse.getResults()) {
                        Object data = resultDoc.getFieldValue(CONTENT);
                        if (data != null && data.toString().trim().length() > 0)
                            response.add(data.toString());
                    }

                    return response;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
