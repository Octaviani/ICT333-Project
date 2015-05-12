package com.HiveSys.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.activation.MimetypesFileTypeMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.DefaultHttpClient;
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
import org.apache.solr.common.util.NamedList;

public class SolrConnection {
	private HttpSolrClient mHttpSolrClient = null;
	private String mUrl = "";

	SolrConnection() {
	}

	public void connect(String serverUrl) {
		mUrl = serverUrl;
		mHttpSolrClient = new HttpSolrClient(mUrl);
		
		// this fixes all the missing strean http header issues. Eg: Content.Length = null causing stream_size=null 
		mHttpSolrClient.setUseMultiPartPost(true);
	}

	public void close() {

	}

	public SolrDocumentList query(String queryString)
			throws SolrServerException, IOException {
		SolrQuery parameters = new SolrQuery();
		parameters.set("q", queryString);
		QueryResponse query = null;
		query = mHttpSolrClient.query(parameters);
		return query.getResults();
	}

	public SolrClient getHttpSolrClient() {
		return this.mHttpSolrClient;
	}

	public void indexFiles(String filename) throws IOException {
		String id = filename.substring(filename.lastIndexOf('/') + 1);
		File fd = new File(filename);
		MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
		String mimeType = mimeTypesMap.getContentType(fd);
		ContentStreamUpdateRequest req = new ContentStreamUpdateRequest(
				"/update/extract");

		//req.addFile(fd, mimeType);
		ContentStreamBase.FileStream fs = new FileStream(fd);
		req.addContentStream(fs);
		//req.setMethod(METHOD.POST);
		ModifiableSolrParams out = new ModifiableSolrParams();
		req.setParam("resource.name", filename);
		req.setParam("literal.id", filename);
		req.setParam("extractOnly", "true");
		System.out.println("Request to solr: \n" + "\tName: " + fs.getName()
				+ "\n" + "\tFile Size: " + fs.getSize() + "\n"
				+ "\tContent Type: " + fs.getContentType() + "\n"
				);

		// req.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
		UpdateResponse result;
		try {
			
			result = req.process(mHttpSolrClient);
			mHttpSolrClient.commit();
			//System.out.println("Response from Solr: ");
			//System.out.println(result.getResponse());

		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// return the default instance of the client
	public static SolrConnection getDefault() {
		return StaticSolrConnection.DEFAULT;
	}
}
