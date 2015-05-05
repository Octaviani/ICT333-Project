package com.HiveSys.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;


public class SolrConnection {
	private SolrClient mSolr = null;
	private String mUrl = "";
	
	SolrConnection()
	{
	}
	
	public void connect(String serverUrl)
	{
		mUrl = serverUrl;
		mSolr = new HttpSolrClient(mUrl);
	}
	
	public void close()
	{
		try {
			mSolr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public SolrDocumentList query(String queryString) 
	{
		SolrQuery parameters = new SolrQuery();
		parameters.set("q", queryString);
		QueryResponse query = null;
		try {
			query = mSolr.query(parameters);
			return query.getResults();
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public SolrClient getSolrContext() 
	{
		return this.mSolr;
	}
	
	@SuppressWarnings("deprecation")
	public void indexFiles(String filename) throws IOException
	
	{
		 HttpPost method;
		 String URLfilename = URLEncoder.encode(filename, "UTF-8");
		 method = new HttpPost(this.mUrl + "update/extract?literal.id=" + URLfilename + "&commit=true");

		    MultipartEntity me = new MultipartEntity();
		    FileInputStream fs = new FileInputStream(new File(filename));
		    
		    me.addPart("myfile", new InputStreamBody(fs, filename));

		    method.setEntity(me);
		    //method.setHeader("Content-Type", "multipart/form-data");
		    HttpClient httpClient = new DefaultHttpClient();
		    HttpResponse hr = httpClient.execute(method);
		    System.out.println(hr);
		
		/*
		SimplePostTool post = new SimplePostTool();
		
		String id = filename.substring(filename.lastIndexOf('/')+1);
		File fd = new File(filename);
		MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        String mimeType = mimeTypesMap.getContentType(fd);
		 ContentStreamUpdateRequest req = new ContentStreamUpdateRequest("/update/extract/");
		 
		 //req.addFile(fd, mimeType);
		 ContentStreamBase.FileStream fs = new FileStream(new File(filename));
		 req.addContentStream(fs);
		 ModifiableSolrParams out = new ModifiableSolrParams();
		 try {
			FieldUtils.writeField(out, "literals.stream_size", 23);
			req.setParams(out);
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 //req.setParam("literal.id", filename);
		 req.setParam("extractOnly", "true");
		 System.out.println("Request to solr: \n" + fs.getName() + fs.getSize() + "\n" + req.getResponseParser());
		 //req.process(mSolr);
		 //req.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
		 NamedList<Object> result;
		try {
			result = this.mSolr.request(req);
			System.out.println("Response from Solr: ");
			System.out.println(result);
			assert result != null : "Can't upload the damn file";
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 */
	}
	
	// return the default instance of the client
	public static SolrConnection getDefault() {
		return StaticSolrConnection.DEFAULT;
	}
}
