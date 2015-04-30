package HiveSys.core;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

public class SolrConnection {
	private SolrClient mSolr = null;
	
	SolrConnection()
	{
	}
	
	public void connect(String serverUrl)
	{
		mSolr = new HttpSolrClient(serverUrl);
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
	
	// return the default instance of the client
	public static SolrConnection getDefault() {
		return StaticSolrConnection.DEFAULT;
	}
}
