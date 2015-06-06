package org.vaadin.visjs.networkDiagram;

import org.vaadin.visjs.networkDiagram.event.node.*;
import elemental.json.JsonArray;
import elemental.json.JsonException;

/**
 * Created by roshans on 11/30/14.
 */
public class EventGenerator {

    public static SelectEvent getNodeSelectEvent(JsonArray properties, NetworkDiagram nd) {
        SelectEvent selectEvent = null;
        try {
            selectEvent = new SelectEvent(properties, nd);

        } catch (JsonException e) {
            e.printStackTrace();
        }
        return selectEvent;
    }

    public static ClickEvent getNodeClickEvent(JsonArray properties, NetworkDiagram nd) {
        ClickEvent clickEvent = null;
        try {
            clickEvent = new ClickEvent(properties, nd);
        } catch (JsonException e) {
            e.printStackTrace();
        }
        return clickEvent;
    }

    public static DoubleClickEvent getNodeDoubleClickEvent(JsonArray properties, NetworkDiagram nd) {
        DoubleClickEvent doubleClickEvent = null;
        try {
            doubleClickEvent = new DoubleClickEvent(properties, nd);
        } catch (JsonException e) {
            e.printStackTrace();
        }
        return doubleClickEvent;
    }

    public static HoverEvent getNodeHoverEvent(JsonArray properties, NetworkDiagram nd) {
        HoverEvent hoverEvent = null;
        try {
            hoverEvent = new HoverEvent(properties, nd);
        } catch (JsonException e) {
            e.printStackTrace();
        }
        return hoverEvent;
    }

    public static BlurEvent getNodeBlurEvent(JsonArray properties, NetworkDiagram nd) {
        BlurEvent blurEvent = null;
        try {
            blurEvent = new BlurEvent(properties, nd);
        } catch (JsonException e) {
            e.printStackTrace();
        }
        return blurEvent;
    }

    public static DragStartEvent getNodeDragStartEvent(JsonArray properties, NetworkDiagram nd) {
        DragStartEvent dragStartEvent = null;
        try {
            dragStartEvent = new DragStartEvent(properties, nd);
        } catch (JsonException e) {
            e.printStackTrace();
        }
        return dragStartEvent;
    }

    public static DragEndEvent getNodeDragEndEvent(JsonArray properties, NetworkDiagram nd) {
        DragEndEvent dragEndEvent = null;
        try {
            dragEndEvent = new DragEndEvent(properties, nd);
        } catch (JsonException e) {
            e.printStackTrace();
        }
        return dragEndEvent;
    }
}
