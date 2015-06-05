package org.vaadin.visjs.networkDiagram.event;

import org.vaadin.visjs.networkDiagram.entity.EdgeEntity;
import org.vaadin.visjs.networkDiagram.entity.NodeEntity;

/**
 * Created by roshans on 11/24/14.
 */
public class NetworkEvent {

    int nodeID;
    NodeEntity node;
    EdgeEntity edge;
    int DOMx = 0;
    int DOMy = 0;
    int canvasX = 0;
    int canvasY = 0;

    public int getNodeID() {
        return nodeID;
    }

    public void setNodeID(int nodeID) {
        this.nodeID = nodeID;
    }

    public void setNode(NodeEntity node) {
        this.node = node;
    }

    public EdgeEntity getEdge() {
        return edge;
    }

    public void setEdge(EdgeEntity edge) {
        this.edge = edge;
    }

    public NetworkEvent(NodeEntity node){
        this.node = node;
    }

    public NetworkEvent(EdgeEntity edge){
        this.edge = edge;
    }

    public NodeEntity getNode(){
       return node;
    }

    public EdgeEntity edge(){
        return edge;
    }

    public int getCanvasX() {
        return canvasX;
    }

    public void setCanvasX(int canvasX) {
        this.canvasX = canvasX;
    }

    public int getCanvasY() {
        return canvasY;
    }

    public void setCanvasY(int canvasY) {
        this.canvasY = canvasY;
    }

    public int getDOMy() {
        return DOMy;
    }

    public void setDOMy(int DOMy) {
        this.DOMy = DOMy;
    }

    public int getDOMx() {
        return DOMx;
    }

    public void setDOMx(int DOMx) {
        this.DOMx = DOMx;
    }
}
