package org.vaadin.visjs.networkDiagram.api;

import java.util.ArrayList;
import java.util.List;
import org.vaadin.visjs.networkDiagram.Node;

/**
 * Created by roshans on 11/30/14.
 */
public abstract class Event {
    private List<String> nodeIds;
    private List<String> edgeIds;
    
    private List<Node> nodes;
    
    public Event(){
        nodeIds = new ArrayList<>();
        edgeIds = new ArrayList<>();
        nodes = new ArrayList<>();
    }

    public List<String> getNodeIds() {
        return nodeIds;
    }

    public void setNodeIds(List<String> nodeIds) {
        this.nodeIds = nodeIds;
    }

    public List<String> getEdgeIds() {
        return edgeIds;
    }

    public void setEdgeIds(List<String> edgeIds) {
        this.edgeIds = edgeIds;
    }

    /**
     * @return the nodes
     */
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * @param nodes the nodes to set
     */
    public void _setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
}

