package com.HiveSys.dashboard.view.repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.tika.metadata.Metadata;

import com.HiveSys.dashboard.layout.UploadLayout;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.HiveSys.core.SolrConnection;

public class UploadView extends UploadLayout implements View {
	public static final String NAME = "UploadView";

	public UploadView() {

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
					new Notification("Could not open file<br/>",
							e.getMessage(), Notification.Type.ERROR_MESSAGE)
							.show(Page.getCurrent());
					return null;
				}
				return fos; // Return the output stream to write to
			}

			public void uploadSucceeded(SucceededEvent event) {
				// first extract the meta data
				Metadata md = SolrConnection.getDefault().getMetaData(
						file.getAbsolutePath());
				fillForms(md);
				/*
				 * try {
				 * SolrConnection.getDefault().indexFiles(file.getAbsolutePath
				 * ()); } catch (IOException e) { // TODO Auto-generated catch
				 * block e.printStackTrace(); } // Show the uploaded file in the
				 * image viewer //image.setVisible(true); //image.setSource(new
				 * FileResource(file));
				 */
			}
		}
		;

		DocumentUploader receiver = new DocumentUploader();
		this.boxUpload.setReceiver(receiver);
		this.boxUpload.addSucceededListener(receiver);
		String currentDir = System.getProperty("user.dir");

		// SolrInputDocument sd = new SolrInputDocument()

	}

	void fillForms(Metadata md) {
		this.txtTitle.setValue(md.get("title"));
		this.txtFileType.setValue(md.get("Content-Type"));
		this.txtAuthor.setValue(md.get("Author"));

		/*
		 * date:2009-12-03T22:38:43Z pdf:PDFVersion:1.4
		 * X-Parsed-By:org.apache.tika.parser.DefaultParser creator:ebaafi
		 * xmp:CreatorTool:PScript5.dll Version 5.2.2 meta:author:ebaafi
		 * meta:creation-date:2009-12-03T22:38:43Z created:Fri Dec 04 06:38:43
		 * AWST 2009 dc:creator:ebaafi xmpTPg:NPages:36
		 * Creation-Date:2009-12-03T22:38:43Z
		 * dcterms:created:2009-12-03T22:38:43Z
		 * Last-Modified:2009-12-03T22:38:43Z
		 * dcterms:modified:2009-12-03T22:38:43Z dc:format:application/pdf;
		 * version=1.4 title:Monograph 12_09.pdf
		 * Last-Save-Date:2009-12-03T22:38:43Z
		 * meta:save-date:2009-12-03T22:38:43Z pdf:encrypted:false
		 * dc:title:Monograph 12_09.pdf Author:ebaafi producer:Acrobat Distiller
		 * 7.0 (Windows) modified:2009-12-03T22:38:43Z
		 * Content-Type:application/pdf
		 */
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
