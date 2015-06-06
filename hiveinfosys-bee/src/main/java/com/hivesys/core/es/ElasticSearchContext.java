package com.hivesys.core.es;

import com.hivesys.core.Document;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class ElasticSearchContext {

    private static final ElasticSearchContext singleton = new ElasticSearchContext();
    Client mClient;

    String idxName = "datahive-es-store";
    String idxType = "document";

    ElasticSearchContext() {
    }

    public void initClient(String host, int port) throws Exception {

        mClient = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress(host, port));

        //Node node = nodeBuilder().local(true).node();
        //mClient = node.client();
        String settings = IOUtils.toString(this.getClass().getResourceAsStream("settings.json"));
        String mapping = IOUtils.toString(this.getClass().getResourceAsStream("document_mapping.json"));

        if (mClient.admin().indices().prepareExists(idxName).execute().actionGet().isExists()) {
            //TODO make sure old and the new are not same

            mClient.admin().indices().prepareClose(idxName).execute().actionGet();
            //mClient.admin().indices().prepareUpdateSettings(idxName).setSettings(settings).execute().actionGet();
            //mClient.admin().indices().prepareUpdateSettings(idxName).execute().actionGet();
            mClient.admin().indices().prepareOpen(idxName).execute().actionGet();

            // Delete the mapping only when needed
            //mClient.admin().indices().prepareDeleteMapping(idxName).setType("attachment").execute().actionGet();
            //mClient.admin().indices().preparePutMapping(idxName).setType("attachment").setSource(map).execute().actionGet();
        } else {

            mClient.admin().indices().prepareCreate(idxName)
                    .addMapping(idxType, mapping)
                    .setSettings(settings)
                    .execute()
                    .actionGet();
        }
    }

    public void indexFile(Document fInfo) throws IOException {
        String data64 = org.elasticsearch.common.Base64.encodeFromFile(fInfo.getContentFilepath());
        XContentBuilder source = jsonBuilder().startObject()
                .field("file", data64)
                .endObject();

        IndexResponse idxResp = mClient.prepareIndex().setIndex(idxName).setType(idxType).setId(fInfo.getCrcHash())
                .setSource(source).setRefresh(true).execute().actionGet();
    }

    public SearchResponse searchSimpleQuery(String queryString) {
        QueryBuilder query = QueryBuilders.queryStringQuery(queryString);

        SearchRequestBuilder searchBuilder = mClient.prepareSearch().setQuery(query)
                .setIndices(idxName) // restrict the search to our indice
                .addField("title")
                .addHighlightedField("file");
        SearchResponse response = searchBuilder.execute().actionGet();

        return response;
    }

    // return the default instance of the client
    /* Static 'instance' method */
    public static ElasticSearchContext getInstance() {
        return singleton;
    }

}
