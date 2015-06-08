/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hivesys.dashboard.view.search;

import com.hivesys.core.es.ElasticSearchContext;
import com.hivesys.core.BoxViewDocuments;
import com.hivesys.core.db.DocumentDB;
import com.hivesys.core.es.Carrot.ClusterResult;
import com.vaadin.ui.CssLayout;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.vaadin.visjs.networkDiagram.Color;
import org.vaadin.visjs.networkDiagram.Edge;
import org.vaadin.visjs.networkDiagram.NetworkDiagram;
import org.vaadin.visjs.networkDiagram.Node;
import org.vaadin.visjs.networkDiagram.event.node.ClickEvent;
import org.vaadin.visjs.networkDiagram.event.node.DoubleClickEvent;
import org.vaadin.visjs.networkDiagram.options.Options;
import org.vaadin.visjs.networkDiagram.options.edges.Edges;

/**
 *
 * @author swoorup
 */
public class GraphView extends CssLayout {

    static int IDs;

    ArrayList<Node> mNodelist;
    ArrayList<Edge> mEdgelist;

    public GraphView() {
        this.setSizeUndefined();
        this.setSizeFull();

        mNodelist = new ArrayList<>();
        mEdgelist = new ArrayList<>();
    }

    public void clearDiagram() {
        mNodelist.clear();
        mEdgelist.clear();

        GraphView.IDs = 0;
    }

    public String splitter(String src) {
        String dest = "";

        if (src.length() <= 1) {
            return src;
        }

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

    public class MyThread implements Runnable {

        GraphView pView;
        String mSearch;

        public MyThread(GraphView parameter, String search) {
            pView = parameter;
            mSearch = search;
        }

        @Override
        public void run() {
            pView.FillNodeWithResults(this.mSearch);
            pView.BuildGraph();
        }
    };

    public void UpdateRootSearch(String rootSearch) {
        // upload to box view in a thread

        FillNodeWithResults(rootSearch);
        BuildGraph();
    }

    void FillNodeWithResults(String rootSearch) {
        this.clearDiagram();

        Node rootNode = new Node(GraphView.IDs++, rootSearch);
        this.mNodelist.add(rootNode);

        FillNodeWithResults(rootNode, rootSearch);

    }

    void FillNodeWithResults(Node rootNode, String search) {
        try {
            rootNode.setColor(new Color("#FA7D7D"));
            rootNode.setShape(Node.Shape.circle);
            
            ClusterResult c = ElasticSearchContext.getInstance().searchClusterQuery(search, search);
            
            for (ClusterResult.Cluster cluster : c.clusters) {
                
                Node clusterNode = new Node(cluster.label);
                clusterNode.setShape(Node.Shape.dot);
                
                Edge edge = new Edge(rootNode.getId(), clusterNode.getId(), Edge.Style.arrowCenter, new Color("green"));
                edge.setValue(cluster.score.intValue());
                edge.setLength(200);
                
                this.mNodelist.add(clusterNode);
                this.mEdgelist.add(edge);
                
                for (String document : cluster.documents) {
                    Node documents = new Node(DocumentDB.getInstance().getDocumentNameFromHash(document));
                    documents.setShape(Node.Shape.dot);
                    documents.setColor(new Color("yellow"));
                    documents.setFontColor("#FAF7F7");
                    Edge edge2 = new Edge(clusterNode, documents, Edge.Style.arrowCenter, new Color("#B2B72A"));
                    edge2.setLength(200);
                    
                    this.mNodelist.add(documents);
                    this.mEdgelist.add(edge2);
                }
            }
            /*for (SearchHit hit : results) {
            try {
            String filename = splitter(DocumentDB.getInstance().getDocumentNameFromHash(hit.getId()));
            Node child = new Node(GraphView.IDs++, filename, "/NetworkGraph/VAADIN/Company.png");
            child.setShape(Node.Shape.circle);
            Edge edge = new Edge(rootNode.getId(), child.getId(), Edge.Style.arrowCenter, new Color("white", "yellow", "blue"));
            edge.setWidth(Math.round(hit.getScore() * 4));
            edge.setValue(1000);
            edge.setLength(200);
            this.mNodelist.add(child);
            this.mEdgelist.add(edge);
            String childSearch = "*"; // TODO Only a test
            if (GraphView.IDs < 30) {
            FillNodeWithResults(child, childSearch);
            }
            } catch (SQLException ex) {
            }
            }*/
        } catch (IOException ex) {
            Logger.getLogger(GraphView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GraphView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void BuildGraph() {
        this.removeAllComponents();
        Options option = new Options();
        Edges edgesOption = new Edges();
        edgesOption.setLength(2000);
        option.setEdges(edgesOption);
        //option.setPhysics(new Physics().);
        NetworkDiagram networkDiagram = new NetworkDiagram(option);
        networkDiagram.setSizeUndefined();
        networkDiagram.setSizeFull();

        for (Edge edge : this.mEdgelist) {
            networkDiagram.addEdge(edge);
        }

        for (Node node : this.mNodelist) {
            networkDiagram.addNode(node);
        }

        networkDiagram.addDoubleClickListener(new NetworkDiagram.DoubleClickListener() {

            @Override
            public void onFired(DoubleClickEvent event) {
                if (event.getNodeIds().size() > 0) {
                    Node node2 = new Node("Hello");
                    Edge edge1 = new Edge(event.getNodes().get(0), node2);
                    networkDiagram.addNode(node2);
                    networkDiagram.addEdge(edge1);
                }
            }
        }
        );
        this.addComponent(networkDiagram);
    }
}
