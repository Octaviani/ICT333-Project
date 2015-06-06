package org.vaadin.visjs.networkDiagram.event.node;

import org.vaadin.visjs.networkDiagram.api.Event;
import elemental.json.JsonArray;
import elemental.json.JsonException;
import org.vaadin.visjs.networkDiagram.NetworkDiagram;

/**
 * Created by roshans on 11/30/14.
 */
public class BlurEvent extends Event {

    public BlurEvent(JsonArray properties, NetworkDiagram nd) throws JsonException {
        super();
        String nodeID = properties.getObject(0).getString("node");
        getNodeIds().add(nodeID);

        getNodes().add(nd.mNodes.get(nodeID));
    }
}
