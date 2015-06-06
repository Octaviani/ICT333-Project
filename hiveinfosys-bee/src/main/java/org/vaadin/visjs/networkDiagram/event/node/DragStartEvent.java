package org.vaadin.visjs.networkDiagram.event.node;

import org.vaadin.visjs.networkDiagram.api.Event;
import elemental.json.JsonArray;
import elemental.json.JsonException;
import elemental.json.JsonObject;
import org.vaadin.visjs.networkDiagram.NetworkDiagram;

/**
 * Created by roshans on 11/30/14.
 */
public class DragStartEvent extends Event {
    public DragStartEvent(JsonArray properties, NetworkDiagram nd) throws JsonException {
        super();
        JsonArray nodes = properties.getObject(0).getArray("nodes");
        for (int i = 0; i < nodes.length(); i++) {
            String nodeID = nodes.getString(i);
            
            getNodes().add(nd.mNodes.get(nodeID));
            getNodeIds().add(nodeID);
        }
    }
}
