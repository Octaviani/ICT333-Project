package com.hivesys.core;

import com.hivesys.dashboard.domain.FileInfo;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.MimetypesFileTypeMap;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.util.ContentStreamBase;
import org.apache.solr.common.util.ContentStreamBase.FileStream;

public class SolrConnection {

    private static final SolrConnection singleton = new SolrConnection();
    private HttpSolrClient mHttpSolrClient = null;
    private String mUrl = "";

    SolrConnection() 
    {
    }

    public void connect(String serverUrl) {
        mUrl = serverUrl;
        mHttpSolrClient = new HttpSolrClient(mUrl);

        // this fixes all the missing stream http header issues. Eg:
        // Content.Length = null causing stream_size=null
        mHttpSolrClient.setUseMultiPartPost(true);
        mHttpSolrClient.setSoTimeout(1000);
        mHttpSolrClient.setConnectionTimeout(1000);

    }

    public void close() {

    }

    public SolrDocumentList query(String queryString)
            throws SolrServerException, IOException {
        SolrQuery parameters = new SolrQuery();
        parameters.set("q", queryString);
        QueryResponse query = mHttpSolrClient.query(parameters);
        return query.getResults();
    }

    public SolrClient getHttpSolrClient() {
        return this.mHttpSolrClient;
    }

    public void indexFile(FileInfo fInfo) {
        ContentStreamUpdateRequest req = new ContentStreamUpdateRequest("/update/extract");

        ContentStreamBase.FileStream fs = new FileStream(new File(fInfo.getContentFilepath()));
        req.addContentStream(fs);
        req.setMethod(METHOD.POST);

        SimpleDateFormat dateFormatUTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date();

        req.setParam("resource.name", fInfo.getContentFilepath());
        req.setParam("literal.id", fInfo.getFullFileName());
        req.setParam("literal.hive_uploaded_date", dateFormatUTC.format(date));

        try {
            UpdateResponse result = req.process(mHttpSolrClient);
            mHttpSolrClient.commit();
        } catch (SolrServerException | IOException ex) {
            Logger.getLogger(SolrConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Done uploading");
    }

    public void indexFiles(String filename) throws IOException {
        String id = filename.substring(filename.lastIndexOf('/') + 1);
        File fd = new File(filename);
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        String mimeType = mimeTypesMap.getContentType(fd);

        ContentStreamUpdateRequest req = new ContentStreamUpdateRequest(
                "/update/extract");

        // req.addFile(fd, mimeType);
        ContentStreamBase.FileStream fs = new FileStream(fd);
        req.addContentStream(fs);
        req.setMethod(METHOD.POST);
        ModifiableSolrParams out = new ModifiableSolrParams();

        SimpleDateFormat dateFormatUTC = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date();
        System.out.println(dateFormatUTC.format(date)); // 2014/08/06 15:59:48

        req.setParam("resource.name", filename);
        req.setParam("literal.id", filename);
        req.setParam("literal.hive_uploaded_date", dateFormatUTC.format(date));
        // req.setParam("extractOnly", "true");

        System.out.println("Request to solr: \n" + "\tName: " + fs.getName()
                + "\n" + "\tFile Size: " + fs.getSize() + "\n"
                + "\tContent Type: " + fs.getContentType() + "\n");

        try {
            // req.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
            UpdateResponse result = req.process(mHttpSolrClient);
            UpdateResponse commit = mHttpSolrClient.commit();
        } catch (SolrServerException ex) {
            Logger.getLogger(SolrConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // return the default instance of the client
    /* Static 'instance' method */
    public static SolrConnection getInstance() {
        return singleton;
    }

}
