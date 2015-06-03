package com.hivesys.core;

import com.hivesys.dashboard.domain.FileInfo;
import java.io.IOException;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class ElasticSearchContext {

    private static final ElasticSearchContext singleton = new ElasticSearchContext();
    Client mClient;

    String idxName = "hivesystemcontents";
    String idxType = "attachment";

    ElasticSearchContext() {
    }

    public void initClient(String host, int port) {
        try {
            mClient = new TransportClient()
                    .addTransportAddress(new InetSocketTransportAddress(host, port));

            //Node node = nodeBuilder().local(true).node();
            //mClient = node.client();

            
            
            final XContentBuilder map = jsonBuilder().startObject()
                    .startObject(idxType)
                    .startObject("properties")
                    .startObject("file")
                    .field("type", "attachment")
                    .startObject("fields")
                    .startObject("title")
                    .field("store", "yes")
                    .endObject()
                    .startObject("file")
                    .field("term_vector", "with_positions_offsets")
                    .field("store", "yes")
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject();

            IndicesExistsRequest request = new IndicesExistsRequest(idxName);
            
            mClient.admin().indices().exists(request, new org.elasticsearch.action.ActionListener<IndicesExistsResponse>() {

                @Override
                public void onResponse(IndicesExistsResponse rspns) {
                    if (!rspns.isExists()) {
                        CreateIndexResponse resp = mClient.admin().indices().prepareCreate(idxName).setSettings(
                                ImmutableSettings.settingsBuilder()
                                .put("number_of_shards", 1)
                                .put("index.numberOfReplicas", 1))
                                .addMapping("attachment", map).execute().actionGet();
                    }
                }

                @Override

                public void onFailure(Throwable thrwbl) {

                }
            }
            );
        } catch (IOException ex) {
            //Logger.getLogger(ElasticSearchContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void indexFile(FileInfo fInfo) throws IOException {
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
