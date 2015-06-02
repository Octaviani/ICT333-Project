/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hivesys.dashboard.view.search;

import com.hivesys.core.SolrConnection;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Notification;
import java.io.IOException;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;
import org.vaadin.visjs.networkDiagram.Edge;
import org.vaadin.visjs.networkDiagram.NetworkDiagram;
import org.vaadin.visjs.networkDiagram.Node;
import org.vaadin.visjs.networkDiagram.options.Options;

/**
 *
 * @author swoorup
 */
public class GraphView extends CssLayout {

    public GraphView() {
        this.setSizeUndefined();
        this.setSizeFull();
    }
    
    public void RebuildGraph(String rootSearch) {
        this.removeAllComponents();
        NetworkDiagram networkDiagram = new NetworkDiagram(new Options());
        networkDiagram.setSizeUndefined();
        networkDiagram.setSizeFull();

        Node rootNode = new Node(rootSearch);
        networkDiagram.addNode(rootNode);

        SolrDocumentList doclist = null;
        try {

            doclist = SolrConnection.getInstance().query(rootSearch);

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

        for (int i = 0; i < doclist.size(); i++) {
            Object streamName = doclist.get(i).getFieldValue("stream_name");
            if (streamName != null) {
                Node docChild = new Node(i, "hello\nWorld\n.com\nsdasasa\n", Node.Shape.circle, "");
                Edge childEdge = new Edge(docChild, rootNode);
                networkDiagram.addNode(docChild);
                networkDiagram.addEdge(childEdge);
            }
        }

        this.addComponent(networkDiagram);
    }
}
