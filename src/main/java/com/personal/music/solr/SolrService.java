package com.personal.music.solr;

import com.personal.music.util.ConfigurationProvider;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;

/**
 * Created by hrajagopal on 5/21/15.
 */
public class SolrService {

    private static SolrServer solrServer = null;
    private static String solrConnectionUrl = null;

    private SolrService() {
        try {
            if (solrConnectionUrl == null)
                solrConnectionUrl = ConfigurationProvider.getConfigurationProperties().getProperty(ConfigurationProvider.SOLR_URL);

            if (solrServer == null && solrConnectionUrl != null)
                solrServer = new CommonsHttpSolrServer(solrConnectionUrl);
        } catch (Exception e) {
            System.out.println("Error while connecting to Solr: " + e.getMessage());
        }
    }

    public static SolrServer getSolrServer() {
        if (solrServer == null)
            new SolrService();

        return solrServer;
    }
}
