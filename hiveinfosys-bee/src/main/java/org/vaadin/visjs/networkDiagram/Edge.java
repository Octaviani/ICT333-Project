/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vaadin.visjs.networkDiagram;

import org.vaadin.visjs.networkDiagram.entity.EdgeEntity;

/**
 *
 * @author swoorup
 */
public class Edge extends EdgeEntity {

    public Edge(Node from, Node to) {
        super(from, to);
    }

    public Edge(Node from, Node to, int width) {
        super(from, to, width);
    }

    public Edge(Node from, Node to, Color color) {
        super(from, to, color);
    }

    public Edge(Node from, Node to, Color color, int width) {
        super(from, to, color, width);
    }

    public Edge(Node from, Node to, Edge.Style style) {
        super(from, to, style);

    }

    public Edge(Node from, Node to, Edge.Style style, int width) {
        super(from, to, style, width);
    }

    public Edge(Node from, Node to, Edge.Style style, Color color) {
        super(from, to, style, color);
    }

    public Edge(Node from, Node to, Edge.Style style, Color color, int width) {
        super(from, to, style, color, width);
    }

    @Deprecated
    public Edge(int from, int to) {
        super(from, to);
    }

    @Deprecated
    public Edge(String from, String to) {
        super(from, to);
    }

    @Deprecated
    public Edge(int from, int to, int width) {
        super(from, to, width);
    }

    @Deprecated
    public Edge(String from, String to, int width) {
        super(from, to, width);
    }

    @Deprecated
    public Edge(int from, int to, Color color) {
        super(from, to, color);
    }

    @Deprecated
    public Edge(String from, String to, Color color) {
        super(from, to, color);
    }

    @Deprecated
    public Edge(int from, int to, Color color, int width) {
        super(from, to, width);
    }

    @Deprecated
    public Edge(String from, String to, Color color, int width) {
        super(from, to, width);
    }

    @Deprecated
    public Edge(int from, int to, Edge.Style style) {
        super(from, to, style);

    }

    @Deprecated
    public Edge(String from, String to, Edge.Style style) {
        super(from, to, style);

    }

    @Deprecated
    public Edge(int from, int to, Edge.Style style, int width) {
        super(from, to, style, width);
    }

    @Deprecated
    public Edge(String from, String to, Edge.Style style, int width) {
        super(from, to, style, width);

    }

    @Deprecated
    public Edge(int from, int to, Edge.Style style, Color color) {
        super(from, to, style, color);
    }

    @Deprecated
    public Edge(String from, String to, Edge.Style style, Color color) {
        super(from, to, style, color);
    }

    @Deprecated
    public Edge(int from, int to, Edge.Style style, Color color, int width) {
        super(from, to, style, color, width);
    }

    @Deprecated
    public Edge(String from, String to, Edge.Style style, Color color, int width) {
        super(from, to, style, color, width);
    }
}
