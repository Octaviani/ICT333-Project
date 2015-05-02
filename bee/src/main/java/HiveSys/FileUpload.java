package HiveSys;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.solr.client.solrj.SolrServerException;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

import HiveSys.core.SolrConnection;
import HiveSys.layout.FileUploadLayout;

public class FileUpload extends FileUploadLayout{
	public FileUpload() {
		
		// Set the handler to upload the files to the server first
		class DocumentUploader implements Receiver, SucceededListener {
			public File file;
			
			public OutputStream receiveUpload(String filename, String mimeType) {
				// Create upload stream
		        FileOutputStream fos = null; // Stream to write to
		        try {
		            // Open the file for writing.
		            file = new File("/tmp/" + filename);
		            fos = new FileOutputStream(file);
		        } catch (final java.io.FileNotFoundException e) {
		            new Notification("Could not open file<br/>", e.getMessage(), Notification.Type.ERROR_MESSAGE)
		                .show(Page.getCurrent());
		            return null;
		        }
		        return fos; // Return the output stream to write to
		    }

		    public void uploadSucceeded(SucceededEvent event) {
		    	System.out.println(file.getAbsolutePath());
		    	// now index to solr
		    	try {
					SolrConnection.getDefault().indexFiles(file.getAbsolutePath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        // Show the uploaded file in the image viewer
		        //image.setVisible(true);
		        //image.setSource(new FileResource(file));
		    }
		};
		
		DocumentUploader receiver=  new DocumentUploader();
		this.boxUpload.setReceiver(receiver);
		this.boxUpload.addSucceededListener(receiver);
		String currentDir = System.getProperty("user.dir");
		
		//SolrInputDocument sd = new SolrInputDocument()
		
	}
	
}
