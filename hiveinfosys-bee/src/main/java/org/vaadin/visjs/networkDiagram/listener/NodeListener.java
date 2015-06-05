package org.vaadin.visjs.networkDiagram.listener;

import org.vaadin.visjs.networkDiagram.entity.NodeEntity;

/**
 * Created by roshans on 11/24/14.
 */
public abstract class NodeListener {
    NodeEntity node;

    public NodeListener(NodeEntity node){
        this.node = node;
    }

    public NodeEntity getNode(){
        return node;
    }
}
