package com.personal.music.elastic;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.springframework.stereotype.Service;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * Created by hrajagopal on 5/18/15.
 */
@Service
public class ElasticSearchServer {
    public static final String ALBUM = "ALBUM";
    public static final String PLAYLIST = "PLAYLIST";
    public static final String ALBUM_INDEX_TYPE = "ALBUM_COLLECTION";
    public static final String PLAYLIST_INDEX_TYPE = "PLAYLIST_COLLECTION";

    private static Client elasticSearchClient = null;

    private ElasticSearchServer() {
        super();

        if (elasticSearchClient == null) {
            Node node = nodeBuilder().node();
            elasticSearchClient = node.client();
        }
    }

    public static Client getElasticSearchClient() {
        if (elasticSearchClient == null)
            new ElasticSearchServer();

        return elasticSearchClient;
    }
}
