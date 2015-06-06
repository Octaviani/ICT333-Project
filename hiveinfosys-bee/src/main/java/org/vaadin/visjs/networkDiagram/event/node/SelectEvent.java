package org.vaadin.visjs.networkDiagram.event.node;

import org.vaadin.visjs.networkDiagram.Node;
import org.vaadin.visjs.networkDiagram.api.Event;
import elemental.json.JsonArray;
import elemental.json.JsonException;

import java.util.ArrayList;
import java.util.List;
import org.vaadin.visjs.networkDiagram.NetworkDiagram;

/**
 * Created by roshans on 11/30/14.
 */
public class SelectEvent extends Event {
    public SelectEvent(JsonArray properties, NetworkDiagram nd) throws JsonException {
        super();
        JsonArray edges = properties.getObject(0).getArray("edges");
        JsonArray nodes = properties.getObject(0).getArray("nodes");
        for(int i = 0 ; i<nodes.length() ; i++ ){
            String nodeID = nodes.getString(i);
            
            getNodes().add(nd.mNodes.get(nodeID));
            getNodeIds().add(nodeID);
        }

        for(int i=0;i<edges.length();i++){
            getEdgeIds().add(edges.getString(i));
        }
    }

}
