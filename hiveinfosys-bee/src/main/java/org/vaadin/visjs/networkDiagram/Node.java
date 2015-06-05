package org.vaadin.visjs.networkDiagram;

import com.google.gson.annotations.SerializedName;
import org.vaadin.visjs.networkDiagram.event.node.*;
import org.vaadin.visjs.networkDiagram.listener.NodeListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by roshans on 10/10/14.
 */
public class Node {

    private String id;
    private int level;
    private int value;
    //private int x;
    //private int y;
    private int mass = 1;
    private int widthMin = 16;
    private int widthMax = 64;
    private int radius = 10;
    private int radiusMin = 10;
    private int radiusMax = 30;
    private int fontSize = 14;
    private int borderWidth = 1;

    private boolean allowedToMoveX = true;
    private boolean allowedToMoveY = true;

    private String title;
    private String label;
    private String group;
    private String image;
    private String brokenImage;
    private String fontColor;
    private String fontFace;

    private Color color;
    private Shape shape = Shape.ellipse;

    private transient List<Edge> edgeList = new ArrayList<>();;
    private transient Map<String, Edge> edgeMap = new HashMap<>();;
    
    private static AtomicLong counter = new AtomicLong(0);

    // my additions
    public Node(String label) {
        this.id= Long.toString(counter.getAndIncrement()); 
        this.label = label;
    }


    public Node(String label,String image) {
        this.id= Long.toString(counter.getAndIncrement());
        this.label = label;
        this.image = image;
        this.shape = Shape.image;
    }


    public Node(String label, Node.Shape shape,String group){
        this.id= Long.toString(counter.getAndIncrement());
        this.label = label;
        this.shape = shape;
        this.group = group;
    }

    public Node(String label, Node.Shape shape,String group,String image){
        this.id = Long.toString(counter.getAndIncrement());
        this.label = label;
        this.shape = shape;
        this.group = group;
        this.image = image;
        this.shape = Shape.image;
    }
    
    @Deprecated
    public Node(int id, String label) {
        this.id = Integer.toString(id);
        this.label = label;
    }

    @Deprecated
    public Node(int id, String label,String image) {
        this.id = Integer.toString(id);
        this.label = label;
        this.image = image;
        this.shape = Shape.image;
    }

    @Deprecated
    public Node(String id, String label,String image) {
        this.id = id;
        this.label = label;
        this.image = image;
        this.shape = Shape.image;
    }

    @Deprecated
    public Node(int id, String label, Node.Shape shape,String group){
        this.id = Integer.toString(id);;
        this.label = label;
        this.shape = shape;
        this.group = group;
    }

    @Deprecated
    public Node(String id, String label, Node.Shape shape,String group){
        this.id = id;
        this.label = label;
        this.shape = shape;
        this.group = group;
    }

    @Deprecated
    public Node(int id, String label, Node.Shape shape,String group,String image){
        this.id = Integer.toString(id);;
        this.label = label;
        this.shape = shape;
        this.group = group;
        this.image = image;
        this.shape = Shape.image;
    }

    @Deprecated
    public Node(String id, String label, Node.Shape shape,String group,String image){
        this.id = id;
        this.label = label;
        this.shape = shape;
        this.group = group;
        this.image = image;
        this.shape = Shape.image;
    }

    public String getId() {
        return id;
    }

    @Deprecated
    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    /*public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }*/

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public int getWidthMin() {
        return widthMin;
    }

    public void setWidthMin(int widthMin) {
        this.widthMin = widthMin;
    }

    public int getWidthMax() {
        return widthMax;
    }

    public void setWidthMax(int widthMax) {
        this.widthMax = widthMax;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRadiusMin() {
        return radiusMin;
    }

    public void setRadiusMin(int radiusMin) {
        this.radiusMin = radiusMin;
    }

    public int getRadiusMax() {
        return radiusMax;
    }

    public void setRadiusMax(int radiusMax) {
        this.radiusMax = radiusMax;
    }

    public boolean isAllowedToMoveX() {
        return allowedToMoveX;
    }

    public void setAllowedToMoveX(boolean allowedToMoveX) {
        this.allowedToMoveX = allowedToMoveX;
    }

    public boolean isAllowedToMoveY() {
        return allowedToMoveY;
    }

    public void setAllowedToMoveY(boolean allowedToMoveY) {
        this.allowedToMoveY = allowedToMoveY;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        setShape(Shape.image);
        this.image = image;
    }

    public String getBrokenImage() {
        return brokenImage;
    }

    public void setBrokenImage(String brokenImage) {
        setShape(Shape.image);
        this.brokenImage = brokenImage;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontFace() {
        return fontFace;
    }

    public void setFontFace(String fontFace) {
        this.fontFace = fontFace;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public List<Edge> getConnectedEdges() {
        return edgeList;
    }

    public void setConnectedEdges(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    public void addEdgeToList(Edge edge) {
        edgeList.add(edge);
    }

    public void removeEdgeFromList(Edge edge) {
        edgeList.remove(edge);
    }

    public Map<String, Edge> getEdgeMap() {
        return edgeMap;
    }

    public void setEdgeMap(Map<String, Edge> edgeMap) {
        this.edgeMap = edgeMap;
    }

    public static enum Shape {
        @SerializedName("ellipse")
        ellipse,
        @SerializedName("circle")
        circle,
        @SerializedName("box")
        box,
        @SerializedName("database")
        database,
        @SerializedName("image")
        image,
        @SerializedName("label")
        label,
        @SerializedName("dot")
        dot,
        @SerializedName("star")
        star,
        @SerializedName("triangle")
        triangle,
        @SerializedName("triangleDown")
        triangleDown,
        @SerializedName("square")
        square;
    }

    public static abstract class NodeSelectListener extends NodeListener {
        public NodeSelectListener(Node node) {
            super(node);
        }
        public abstract void onFired(SelectEvent event);
    }

    public static abstract class NodeClickListener extends NodeListener{
        public NodeClickListener(Node node) {
            super(node);
        }
        public abstract void onFired(ClickEvent event);
    }

    public static abstract class NodeDoubleClickListener extends NodeListener{
        public NodeDoubleClickListener(Node node) {
            super(node);
        }
        public abstract void onFired(DoubleClickEvent event);
    }

    public static abstract class NodeHoverListener extends NodeListener{
        public NodeHoverListener(Node node) {
            super(node);
        }
        public abstract void onFired(HoverEvent event);
    }

    public static abstract class NodeBlurListener extends NodeListener{
        public NodeBlurListener(Node node) {
            super(node);
        }
        public abstract void onFired(BlurEvent event);
    }

    public static abstract class NodeDragStartListener extends NodeListener{
        public NodeDragStartListener(Node node) {
            super(node);
        }
        public abstract void onFired(DragStartEvent event);
    }

    public static abstract class NodeDragEndListener extends NodeListener{
        public NodeDragEndListener(Node node) {
            super(node);
        }
        public abstract void onFired(DragEndEvent event);
    }
}
