/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hivesys.dashboard.view.search;

import com.hivesys.core.ElasticSearchContext;
import com.hivesys.core.FileInfoController;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Notification;
import java.sql.SQLException;
import java.util.ArrayList;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.vaadin.visjs.networkDiagram.Color;
import org.vaadin.visjs.networkDiagram.Edge;
import org.vaadin.visjs.networkDiagram.NetworkDiagram;
import org.vaadin.visjs.networkDiagram.Node;
import org.vaadin.visjs.networkDiagram.options.Options;
import org.vaadin.visjs.networkDiagram.options.physics.Physics;

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
        Runnable r = new MyThread(this, rootSearch);
        new Thread(r).start();

    }

    void FillNodeWithResults(String rootSearch) {
        this.clearDiagram();

        Node rootNode = new Node(GraphView.IDs++, rootSearch);
        this.mNodelist.add(rootNode);

        FillNodeWithResults(rootNode, rootSearch);

    }

    void FillNodeWithResults(Node rootNode, String search) {
        rootNode.setColor(new Color("green"));
        rootNode.setShape(Node.Shape.circle);
        
        SearchResponse response = ElasticSearchContext.getInstance().searchSimpleQuery(search);
        SearchHits results = response.getHits();

        for (SearchHit hit : results) {
            try {

                String filename = splitter(FileInfoController.getInstance().getFileNameFromHash(hit.getId()));
                Node child = new Node(GraphView.IDs++, filename, "/NetworkGraph/VAADIN/Company.png");
                child.setShape(Node.Shape.circle);

                Edge edge = new Edge(rootNode.getId(), child.getId(), Edge.Style.arrowCenter, new Color("white", "yellow", "blue"));
                edge.setWidth(Math.round(hit.getScore() * 4));
                edge.setValue(1000);

                this.mNodelist.add(child);
                this.mEdgelist.add(edge);

                String childSearch = "*"; // TODO Only a test
                if (GraphView.IDs < 30) {
                    FillNodeWithResults(child, childSearch);
                }
            } catch (SQLException ex) {
            }
        }
    }

    public void BuildGraph() {
        this.removeAllComponents();
        Options option = new Options();
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

        this.addComponent(networkDiagram);
    }
}
