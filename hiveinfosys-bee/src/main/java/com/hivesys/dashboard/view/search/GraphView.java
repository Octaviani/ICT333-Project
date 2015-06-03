/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hivesys.dashboard.view.search;

import com.hivesys.core.ElasticSearchContext;
import com.hivesys.core.FileInfoController;
import com.sun.media.jfxmedia.logging.Logger;
import com.vaadin.ui.CssLayout;
import java.sql.SQLException;
import java.util.logging.Level;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.vaadin.visjs.networkDiagram.Color;
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

    public String splitter(String src) {
        String dest = "";

        long chunksize = Math.round(Math.sqrt(src.length()));
        long remainder = src.length() % chunksize;
        chunksize = chunksize + Math.round(chunksize * 0.45);

        for (int i = 0; i < src.length(); i++) {
            if (i % (chunksize) == 0) {
                dest += '\n';
            }
            dest += src.charAt(i);
        }

        return dest;
    }

    public void RebuildGraph(String rootSearch) {
        int NODE_RADIUS = 5;
        int NODE_MASS = 10;
        
        int nodeid = 0;

        this.removeAllComponents();
        NetworkDiagram networkDiagram = new NetworkDiagram(new Options());
        networkDiagram.setSizeUndefined();
        networkDiagram.setSizeFull();

        Node rootNode = new Node(nodeid++, rootSearch);
        rootNode.setRadius(NODE_RADIUS);
        networkDiagram.addNode(rootNode);

        SearchResponse response = ElasticSearchContext.getInstance().searchSimpleQuery(rootSearch);
        SearchHits results = response.getHits();

        for (SearchHit hit : results) {
            try {
                String filename = splitter(FileInfoController.getInstance().getFileNameFromHash(hit.getId()));
                Node childNode = new Node(nodeid++, filename, "/NetworkGraph/VAADIN/Company.png" );
                childNode.setShape(Node.Shape.square);
                
                
                Edge edge = new Edge(rootNode.getId(), childNode.getId(), Edge.Style.dashLine, new Color("#339933"));
                edge.setValue(Math.round(hit.getScore()));
                
                networkDiagram.addNode(childNode);
                networkDiagram.addEdge(edge);
            }  catch (SQLException ex) {
                //java.util.logging.Logger.getLogger(GraphView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.addComponent(networkDiagram);
    }
}
