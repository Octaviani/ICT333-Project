package org.vaadin.visjs.networkDiagram;

import org.vaadin.visjs.networkDiagram.options.edges.Dash;
import com.google.gson.annotations.SerializedName;

/**
 * Created by roshans on 10/10/14.
 */
public class Edge {

    private String from;
    private String to;
    private int value;
    private int stabilizationIterations = 1000;

    private boolean allowedToMove;
    private boolean parseColor;
    private boolean clickToUse = false;
    private boolean configurePhysics = false;
    private boolean freezeForStabilization = false;
    private boolean hover = false;
    private boolean dragNetwork = true;
    private boolean dragNodes = true;
    private boolean hideNodesOnDrag = false;
    private boolean hideEdgesOnDrag = false;
    private boolean selectable = true;
    private boolean stabilize = true;
    private boolean zoomable = true;

    private String label;
    private String title;
    private int width;
    private Color color;

    private Edge.Style style = Style.line;
    private Dash dash;

    // my additions
    public Edge(Node from, Node to) {
        this.from = from.getId();
        this.to = to.getId();
    }

    public Edge(Node from, Node to, int width) {
        this.from = from.getId();
        this.to = to.getId();
        this.width = width;
    }

    public Edge(Node from, Node to, Color color) {
        this.from = from.getId();
        this.to = to.getId();
        this.color = color;
    }

    public Edge(Node from, Node to, Color color, int width) {
        this.from = from.getId();
        this.to = to.getId();
        this.color = color;
        this.width = width;
    }

    public Edge(Node from, Node to, Edge.Style style) {
        this.from = from.getId();
        this.to = to.getId();
        this.style = style;

    }

    public Edge(Node from, Node to, Edge.Style style, int width) {
        this.from = from.getId();
        this.to = to.getId();
        this.style = style;
        this.width = width;
    }

    public Edge(Node from, Node to, Edge.Style style, Color color) {
        this.from = from.getId();
        this.to = to.getId();
        this.style = style;
        this.color = color;
    }

    public Edge(Node from, Node to, Edge.Style style, Color color, int width) {
        this.from = from.getId();
        this.to = to.getId();
        this.style = style;
        this.color = color;
        this.width = width;
    }

    @Deprecated
    public Edge(int from, int to) {
        this.from = Integer.toString(from);
        this.to = Integer.toString(to);
    }

    @Deprecated
    public Edge(String from, String to) {
        this.from = from;
        this.to = to;
    }

    @Deprecated
    public Edge(int from, int to, int width) {
        this.from = Integer.toString(from);
        this.to = Integer.toString(to);
        this.width = width;
    }

    @Deprecated
    public Edge(String from, String to, int width) {
        this.from = from;
        this.to = to;
        this.width = width;
    }

    @Deprecated
    public Edge(int from, int to, Color color) {
        this.from = Integer.toString(from);
        this.to = Integer.toString(to);
        this.color = color;
    }

    @Deprecated
    public Edge(String from, String to, Color color) {
        this.from = from;
        this.to = to;
        this.color = color;
    }

    @Deprecated
    public Edge(int from, int to, Color color, int width) {
        this.from = Integer.toString(from);
        this.to = Integer.toString(to);
        this.color = color;
        this.width = width;
    }

    @Deprecated
    public Edge(String from, String to, Color color, int width) {
        this.from = from;
        this.to = to;
        this.color = color;
        this.width = width;
    }

    @Deprecated
    public Edge(int from, int to, Edge.Style style) {
        this.from = Integer.toString(from);
        this.to = Integer.toString(to);
        this.style = style;

    }

    @Deprecated
    public Edge(String from, String to, Edge.Style style) {
        this.from = from;
        this.to = to;
        this.style = style;

    }

    @Deprecated
    public Edge(int from, int to, Edge.Style style, int width) {
        this.from = Integer.toString(from);
        this.to = Integer.toString(to);
        this.style = style;
        this.width = width;
    }

    @Deprecated
    public Edge(String from, String to, Edge.Style style, int width) {
        this.from = from;
        this.to = to;
        this.style = style;
        this.width = width;

    }

    @Deprecated
    public Edge(int from, int to, Edge.Style style, Color color) {
        this.from = Integer.toString(from);
        this.to = Integer.toString(to);
        this.style = style;
        this.color = color;
    }

    @Deprecated
    public Edge(String from, String to, Edge.Style style, Color color) {
        this.from = from;
        this.to = to;
        this.style = style;
        this.color = color;
    }

    @Deprecated
    public Edge(int from, int to, Edge.Style style, Color color, int width) {
        this.from = Integer.toString(from);
        this.to = Integer.toString(to);
        this.style = style;
        this.color = color;
        this.width = width;
    }

    @Deprecated
    public Edge(String from, String to, Edge.Style style, Color color, int width) {
        this.from = from;
        this.to = to;
        this.style = style;
        this.color = color;
        this.width = width;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getStabilizationIterations() {
        return stabilizationIterations;
    }

    public void setStabilizationIterations(int stabilizationIterations) {
        this.stabilizationIterations = stabilizationIterations;
    }

    public boolean isAllowedToMove() {
        return allowedToMove;
    }

    public void setAllowedToMove(boolean allowedToMove) {
        this.allowedToMove = allowedToMove;
    }

    public boolean isParseColor() {
        return parseColor;
    }

    public void setParseColor(boolean parseColor) {
        this.parseColor = parseColor;
    }

    public boolean isClickToUse() {
        return clickToUse;
    }

    public void setClickToUse(boolean clickToUse) {
        this.clickToUse = clickToUse;
    }

    public boolean isConfigurePhysics() {
        return configurePhysics;
    }

    public void setConfigurePhysics(boolean configurePhysics) {
        this.configurePhysics = configurePhysics;
    }

    public boolean isFreezeForStabilization() {
        return freezeForStabilization;
    }

    public void setFreezeForStabilization(boolean freezeForStabilization) {
        this.freezeForStabilization = freezeForStabilization;
    }

    public boolean isHover() {
        return hover;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }

    public boolean isDragNetwork() {
        return dragNetwork;
    }

    public void setDragNetwork(boolean dragNetwork) {
        this.dragNetwork = dragNetwork;
    }

    public boolean isDragNodes() {
        return dragNodes;
    }

    public void setDragNodes(boolean dragNodes) {
        this.dragNodes = dragNodes;
    }

    public boolean isHideNodesOnDrag() {
        return hideNodesOnDrag;
    }

    public void setHideNodesOnDrag(boolean hideNodesOnDrag) {
        this.hideNodesOnDrag = hideNodesOnDrag;
    }

    public boolean isHideEdgesOnDrag() {
        return hideEdgesOnDrag;
    }

    public void setHideEdgesOnDrag(boolean hideEdgesOnDrag) {
        this.hideEdgesOnDrag = hideEdgesOnDrag;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public boolean isStabilize() {
        return stabilize;
    }

    public void setStabilize(boolean stabilize) {
        this.stabilize = stabilize;
    }

    public boolean isZoomable() {
        return zoomable;
    }

    public void setZoomable(boolean zoomable) {
        this.zoomable = zoomable;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Dash getDash() {
        return dash;
    }

    public void setDash(Dash dash) {
        this.dash = dash;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public static enum Style {

        @SerializedName("line")
        line,
        @SerializedName("arrow")
        arrow,
        @SerializedName("arrow-center")
        arrowCenter,
        @SerializedName("dash-line")
        dashLine;
    }
}
