/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hivesys.dashboard.view.search;

import com.hivesys.core.SolrConnection;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import java.io.IOException;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;

/**
 *
 * @author swoorup
 */
public class TextualView extends CssLayout{
    
    public TextualView()
    {
        this.setSizeUndefined();
    }
    
    public void UpdateSearchPane(String searchString) {
        this.removeAllComponents();
        SolrDocumentList doclist = null;
        try {
            
            doclist = SolrConnection.getInstance().query(searchString);

            System.out.println("Number of Search results: " + doclist.size());

        } catch (SolrServerException | IOException e) {
            Notification notification = new Notification("Solr Server Error!");
            notification.setDelayMsec(8000);
            notification
                    .setDescription("It looks the Solr server is not running. Please consult your IT Administrator");
            notification.setStyleName("tray dark small closable login-help");
            notification.setPosition(Position.TOP_CENTER);
            notification.show(Page.getCurrent());
            e.printStackTrace();
        }
        
        int nresults = doclist.size();

        Label labelResultSummary = new Label("About " + nresults
                + " results found <br><br>", ContentMode.HTML);
        this.addComponent(labelResultSummary);

        for (int i = 0; i < nresults; i++) {

            Object streamName = doclist.get(i).getFieldValue("stream_name");
            Object resourcepath = doclist.get(i).getFieldValue("resourcename");

            Object lastModified = doclist.get(i).getFieldValue("last_modified");
            Object contentType = doclist.get(i).getFieldValue("content_type");

            System.out.println(doclist.get(i).toString());
            if (lastModified != null) {
                Link linkDocument = new Link(streamName.toString(),
                        new ExternalResource("www.google.com"));
                //linkDocument.s(true);
                linkDocument.setCaption("<h3>" + streamName.toString()
                        + "</h3>");
                Label labelLastModifiedDate = new Label(
                        "<span class='v-button-wrap'>"
                        + "<span class='v-button-caption'>"
                        + lastModified.toString() + "</span>"
                        + "</span>", ContentMode.HTML);

                labelLastModifiedDate.setStyleName("hellome");

                Label labelContentType = new Label(contentType.toString()
                        + "<br>", ContentMode.HTML);

                labelLastModifiedDate.setStyleName("hellome");
                this.addComponent(linkDocument);
                this.addComponent(labelLastModifiedDate);
                this.addComponent(labelContentType);
            }
        }
    }
}
