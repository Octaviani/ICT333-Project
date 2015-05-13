package com.HiveSys.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.activation.MimetypesFileTypeMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.InputStreamBody;
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
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.ContentStreamBase;
import org.apache.solr.common.util.ContentStreamBase.FileStream;
import org.apache.solr.common.util.NamedList;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;

public class SolrConnection {
	private HttpSolrClient mHttpSolrClient = null;
	private String mUrl = "";

	SolrConnection() {
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
		QueryResponse query = null;
		query = mHttpSolrClient.query(parameters);
		return query.getResults();
	}

	public SolrClient getHttpSolrClient() {
		return this.mHttpSolrClient;
	}

	public Metadata getMetaData(String filename) {
		BodyContentHandler textHandler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		ParseContext context = new ParseContext();

		// Try parsing the file. Note we haven't checked at all to
		// see whether this file is a good candidate.
		try {
			InputStream input = new FileInputStream(filename);
			AutoDetectParser _autoParser = new AutoDetectParser();
			_autoParser.parse(input, textHandler, metadata, context);
		} catch (Exception e) {
			// Needs better logging of what went wrong in
			// order to track down "bad" documents.
			// log(String.format("File %s failed", filename));
			e.printStackTrace();
			// continue;
		}
		_dumpMetadata(filename, metadata);
		return metadata;
	}

	public void indexFiles(String filename) throws IOException {
		String id = filename.substring(filename.lastIndexOf('/') + 1);
		File fd = new File(filename);
		MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
		String mimeType = mimeTypesMap.getContentType(fd);
		Metadata md = getMetaData(filename);
		
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

		// req.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);

		try {
			UpdateResponse result = req.process(mHttpSolrClient);
			mHttpSolrClient.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println("Response from Solr: ");
		// System.out.println(result.getResponse());

	}

	// return the default instance of the client
	public static SolrConnection getDefault() {
		return StaticSolrConnection.DEFAULT;
	}

	public void _dumpMetadata(String filename, Metadata md) {
		log("Dumping metadata for file: " + filename);
		for (String name : md.names()) {
			log(name + ":" + md.get(name));
		}
		log("nn");
	}

	private static void log(String msg) {
		System.out.println(msg);
	}
}
