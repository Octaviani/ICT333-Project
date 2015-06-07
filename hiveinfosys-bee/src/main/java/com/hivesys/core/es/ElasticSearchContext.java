package com.hivesys.core.es;

import com.hivesys.core.Document;
import static com.hivesys.core.es.TikaInstance.tika;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.io.stream.BytesStreamInput;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.hibernate.validator.internal.util.privilegedactions.GetMethod;

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

    public void indexFile(Document doc) throws IOException {
        String data64 = org.elasticsearch.common.Base64.encodeFromFile(doc.getContentFilepath());

        File file = new File(doc.getContentFilepath());
        InputStream fileReader = new FileInputStream(file);
        double bytes = file.length();

        int indexedChars = 1000000;
        Metadata metadata = new Metadata();

        byte[] buffer = new byte[1024 * 8];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int i;
        while (-1 != (i = fileReader.read(buffer))) {
            bos.write(buffer, 0, i);
        }
        byte[] data = bos.toByteArray();
        String parsedContent;
        try {

            // Set the maximum length of strings returned by the parseToString method, -1 sets no limit
            parsedContent = tika().parseToString(new BytesStreamInput(data, false), metadata, indexedChars);
        } catch (IOException | TikaException e) {
            e.printStackTrace();
            parsedContent = "";
        }

        XContentBuilder source = jsonBuilder().startObject()
                .field("file", data64)
                .field("filename", doc.getRootFileName())
                .field("title", doc.getTitle())
                .field("author", doc.getAuthor())
                .field("created_date", doc.getDateCreated())
                .field("content_type", FilenameUtils.getExtension(doc.getRootFileName()))
                .field("content_length", bytes)
                .field("content", parsedContent)
                .endObject();

        IndexResponse idxResp = mClient.prepareIndex().setIndex(idxName).setType(idxType).setId(doc.getHash())
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

    public SearchResponse searchClusterQuery(String queryString, String query_hint) throws IOException {
        String json = "{ \n"
                + "   \"search_request\":{  \n"
                + "      \"query\":{  \n"
                + "         \"match\":{  \n"
                + "            \"_all\":\"" + queryString + "\"}},\n"
                + "            \"size\":100\n"
                + "         },\n"
                + "         \"max_hits\":0,\n"
                + "         \"query_hint\":\"" + query_hint + "\",\n"
                + "         \"field_mapping\":{  \n"
                + "            \"title\":[\"_source.title\"],\n"
                + "            \"content\":[\"_source.content\"],\n"
                + "            \"filename\":[\"_source.filename\"]\n"
                + "         }\n"
                + "}";

        QueryBuilder query = QueryBuilders.matchQuery("_all", queryString);

        SearchRequestBuilder searchBuilder = mClient.prepareSearch(idxName, idxType, "_search_with_clusters")
                .setQuery(query)
                .setSize(100)
                .setIndices(idxName) // restrict the search to our indice
                .addField("title")
                .addHighlightedField("file");

        
        
        SearchResponse response =  mClient.prepareSearch(idxName, idxType, "_search_with_clusters").setSource(json).execute().actionGet();
        ProcessBuilder p = new ProcessBuilder("curl", "-XPOST", "http://localhost:9200/" + idxName + "/" + idxType + "/_search_with_clusters", "-d", json);
        final Process shell = p.start();

        InputStream shellIn = shell.getInputStream();
        StringWriter writer = new StringWriter();
        IOUtils.copy(shellIn, writer, Charset.defaultCharset());
        String theString = writer.toString();
        System.out.println(theString);
        System.out.println(response.toString());

        return response;
        

    }

    // return the default instance of the client
    /* Static 'instance' method */
    public static ElasticSearchContext getInstance() {
        return singleton;
    }

}
