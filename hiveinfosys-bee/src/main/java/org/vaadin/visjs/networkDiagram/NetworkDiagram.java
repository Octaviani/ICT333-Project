package org.vaadin.visjs.networkDiagram;

import org.vaadin.visjs.networkDiagram.event.NetworkEvent;
import org.vaadin.visjs.networkDiagram.event.node.*;
import org.vaadin.visjs.networkDiagram.listener.GraphListener;
import org.vaadin.visjs.networkDiagram.options.Options;
import org.vaadin.visjs.networkDiagram.util.Constants;
import com.google.gson.Gson;
import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;
import elemental.json.JsonArray;
import elemental.json.JsonException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by roshans on 10/10/14.
 */
@JavaScript({"js/vis.min.js", "js/networkDiagram-connector.js"})
@StyleSheet({"css/vis.min.css", "css/networkDiagram.css"})
public class NetworkDiagram extends AbstractJavaScriptComponent {

    // Workaround for ConcurrentModificationException when adding item but at the same time iterations are being done
    /*
     private List<Node.NodeSelectListener> nodeSelectListeners = new ArrayList<>();
     private List<Node.NodeClickListener> nodeClickListeners = new ArrayList<>();
     private List<Node.NodeDoubleClickListener> nodeDoubleClickListeners = new CopyOnWriteArrayList<>();
     private List<Node.NodeHoverListener> nodeHoverListeners = new ArrayList<>();
     private List<Node.NodeBlurListener> nodeBlurListeners = new ArrayList<>();
     private List<Node.NodeDragStartListener> nodeDragStartListeners = new ArrayList<>();
     private List<Node.NodeDragEndListener> nodeDragEndListeners = new ArrayList<>();
     */
    private final List<SelectListener> selectListeners = new CopyOnWriteArrayList<>();
    private final List<ClickListener> clickListeners = new CopyOnWriteArrayList<>();
    private final List<DoubleClickListener> doubleClickListeners = new CopyOnWriteArrayList<>();
    private final List<HoverListener> hoverListeners = new CopyOnWriteArrayList<>();
    private final List<BlurListener> blurListeners = new CopyOnWriteArrayList<>();
    private final List<DragStartListener> dragStartListeners = new CopyOnWriteArrayList<>();
    private final List<DragEndListener> dragEndListeners = new CopyOnWriteArrayList<>();

    private ResizeListener resizeListener;
    private StabilizationStartListener stabilizationStartListener;
    private StabilizedListener stabilizedListener;
    private ViewChangedListener viewChangedListener;
    private ZoomListener zoomListener;
    private final Gson gson = new Gson();
    
    public ConcurrentHashMap<String, Node> mNodes = new ConcurrentHashMap<>();

    public NetworkDiagram(Options options) {
        super();
        addFunction(Constants.ON_SELECT, new JavaScriptFunction() {
            @Override
            public void call(final JsonArray properties) throws JsonException {
                SelectEvent event = EventGenerator.getNodeSelectEvent(properties, NetworkDiagram.this);
                fireSelectEvent(event);
            }
        });
        addFunction(Constants.ON_CLICK, new JavaScriptFunction() {
            @Override
            public void call(final JsonArray properties) throws JsonException {
                ClickEvent event = EventGenerator.getNodeClickEvent(properties, NetworkDiagram.this);
                fireClickEvent(event);
            }
        });
        addFunction(Constants.ON_DOUBLE_CLICK, new JavaScriptFunction() {
            @Override
            public void call(final JsonArray properties) throws JsonException {
                DoubleClickEvent event = EventGenerator.getNodeDoubleClickEvent(properties, NetworkDiagram.this);
                fireDoubleClickEvent(event);
            }
        });
        addFunction(Constants.ON_HOVER_NODE, new JavaScriptFunction() {
            @Override
            public void call(final JsonArray properties) throws JsonException {
                HoverEvent event = EventGenerator.getNodeHoverEvent(properties, NetworkDiagram.this);
                fireHoverEvent(event);
            }
        });
        addFunction(Constants.ON_BLUR_NODE, new JavaScriptFunction() {
            @Override
            public void call(final JsonArray properties) throws JsonException {
                BlurEvent event = EventGenerator.getNodeBlurEvent(properties, NetworkDiagram.this);
                fireBlurEvent(event);
            }
        });
        addFunction(Constants.ON_DRAG_START, new JavaScriptFunction() {
            @Override
            public void call(final JsonArray properties) throws JsonException {
                DragStartEvent event = EventGenerator.getNodeDragStartEvent(properties, NetworkDiagram.this);
                fireDragStartEvent(event);
            }
        });
        addFunction(Constants.ON_DRAG_END, new JavaScriptFunction() {
            @Override
            public void call(final JsonArray properties) throws JsonException {
                DragEndEvent event = EventGenerator.getNodeDragEndEvent(properties, NetworkDiagram.this);
                fireDragEndEvent(event);
            }
        });
        addFunction(Constants.ON_START_STABILIZATION, new JavaScriptFunction() {
            @Override
            public void call(final JsonArray properties) throws JsonException {
                //System.out.println("onStartStabilization" + properties);
                //fireGraphStabilizationStartEvent();
            }
        });
        addFunction(Constants.ON_STABILIZED, new JavaScriptFunction() {
            @Override
            public void call(final JsonArray properties) throws JsonException {
                //System.out.println("onStabilized" + properties);
                //fireGraphStabilizedEvent();
            }
        });
        addFunction(Constants.ON_VIEW_CHANGED, new JavaScriptFunction() {
            @Override
            public void call(final JsonArray properties) throws JsonException {
                //System.out.println("onViewChanged" + properties);
                //fireGraphViewChangedEvent();
            }
        });
        addFunction(Constants.ON_ZOOM, new JavaScriptFunction() {
            @Override
            public void call(final JsonArray properties) throws JsonException {
                //System.out.println("onZoom" + properties);
                //fireGraphZoomEvent();
            }
        });
        addFunction(Constants.ON_RESIZE, new JavaScriptFunction() {
            @Override
            public void call(final JsonArray properties) throws JsonException {
                //System.out.println("onResize" + properties);
                //fireGraphResizeEvent();
            }
        });

        System.out.println("Vis options: " + gson.toJson(options));
        callFunction("init", gson.toJson(options));
    }

    public NetworkDiagramState getState() {
        return (NetworkDiagramState) super.getState();
    }

    public void updateOptions(Options options) {
        getState().updates++;
        callFunction("updateOptions", gson.toJson(options));
    }

    public void addNode(Node... node) {
        for (Node n: node) {
            this.mNodes.put(n.getId(), n);
        }
        
        getState().updates++;
        callFunction("addNodes", gson.toJson(node));
    }

    public void addNodes(List<Node> nodes) {
        for (Node n: nodes) {
            this.mNodes.put(n.getId(), n);
        }
        
        getState().updates++;
        callFunction("addNodes", gson.toJson(nodes));
    }

    public void addEdges(List<Edge> edges) {
        getState().updates++;
        callFunction("addEdges", gson.toJson(edges));
    }

    public void addEdge(Edge... edges) {
        getState().updates++;
        callFunction("addEdges", gson.toJson(edges));
    }

    public void removeNode(Node... node) {
        for (Node n: node) {
            this.mNodes.remove(n.getId());
        }
        
        getState().updates++;
        callFunction("removeNode", gson.toJson(node));
    }

    public void removeEdge(Edge... edges) {
        getState().updates++;
        callFunction("removeEdge", gson.toJson(edges));
    }

    public void updateNode(Node... node) {
        getState().updates++;
        callFunction("updateNode", gson.toJson(node));
    }

    public void updateEdge(Edge... edges) {
        getState().updates++;
        callFunction("updateEdge", gson.toJson(edges));
    }

    @Deprecated
    public void updateEdge(List<Edge> edges) {
        updateEdges(edges);
    }

    public void updateEdges(List<Edge> edges) {
        callFunction("updateEdge", gson.toJson(edges));
    }

    @Deprecated
    public void updateNode(List<Node> nodes) {
        updateNodes(nodes);
    }

    public void updateNodes(List<Node> nodes) {
        callFunction("updateNode", gson.toJson(nodes));
    }

    public void clearNodes() {
        this.mNodes.clear();
        
        callFunction("clearNodes");
    }

    public void clearEdges() {
        callFunction("clearEdges");
    }

    public void destroyNetwork() {
        
        callFunction("destroyNetwork");
    }

    public void clear() {
        clearEdges();
        clearNodes();
    }

    public void drawConnections() {
        callFunction("drawConnections");
    }

    public void addSelectListener(SelectListener listener) {
        selectListeners.add(listener);
    }

    public void removeSelectListener(SelectListener listener) {
        selectListeners.remove(listener);
    }

    public void removeClickListeners(ClickListener listener) {
        clickListeners.remove(listener);
    }

    public void addClickListener(ClickListener nodeClickListener) {
        this.clickListeners.add(nodeClickListener);
    }

    public void removeoubleClickListener(DoubleClickListener listener) {
        doubleClickListeners.remove(listener);
    }

    public void addDoubleClickListener(DoubleClickListener listener) {
        doubleClickListeners.add(listener);
    }

    public void removeHoverListener(HoverListener listener) {
        hoverListeners.remove(listener);
    }

    public void addHoverListener(HoverListener listener) {
        this.hoverListeners.add(listener);
    }

    public void removeBlurListener(BlurListener listener) {
        blurListeners.remove(listener);
    }

    public void addBlurListener(BlurListener listener) {
        this.blurListeners.add(listener);
    }

    public void removeDragStartListener(DragStartListener listener) {
        dragStartListeners.remove(listener);
    }

    public void addDragStartListener(DragStartListener listener) {
        this.dragStartListeners.add(listener);
    }

    public void removeDragEndListener(DragEndListener listener) {
        dragEndListeners.remove(listener);
    }

    public void addDragEndListener(DragEndListener listener) {
        this.dragEndListeners.add(listener);
    }

    //adding and removing graph listeners
    public void addResizeListener(ResizeListener resizeListener) {
        this.resizeListener = resizeListener;
    }

    public void addStabilizationStartListener(StabilizationStartListener stabilizationStartListener) {
        this.stabilizationStartListener = stabilizationStartListener;
    }

    public void addStabilizedListener(StabilizedListener stabilizedListener) {
        this.stabilizedListener = stabilizedListener;
    }

    public void addViewChangedListener(ViewChangedListener viewChangedListener) {
        this.viewChangedListener = viewChangedListener;
    }

    public void addZoomListener(ZoomListener zoomListener) {
        this.zoomListener = zoomListener;
    }

    public void removeResizeListener() {
        this.resizeListener = null;
    }

    public void removeStabilizationStartListener() {
        this.stabilizationStartListener = null;
    }

    public void removeStabilizedListener() {
        this.stabilizedListener = null;
    }

    public void removeViewChangedListener() {
        this.viewChangedListener = null;
    }

    public void removeZoomListener() {
        this.zoomListener = null;
    }

    //listeners for entire graph
    public static abstract class SelectListener {

        public abstract void onFired(SelectEvent event);
    }

    public static abstract class ClickListener {

        public abstract void onFired(ClickEvent event);
    }

    public static abstract class DoubleClickListener {

        public abstract void onFired(DoubleClickEvent event);
    }

    public static abstract class HoverListener {

        public abstract void onFired(HoverEvent event);
    }

    public static abstract class BlurListener {

        public abstract void onFired(BlurEvent event);
    }

    public static abstract class DragStartListener {

        public abstract void onFired(DragStartEvent event);
    }

    public static abstract class DragEndListener {

        public abstract void onFired(DragEndEvent event);
    }

    public static abstract class ResizeListener extends GraphListener {
    }

    public static abstract class StabilizationStartListener extends GraphListener {
    }

    public static abstract class StabilizedListener extends GraphListener {
    }

    public static abstract class ViewChangedListener extends GraphListener {
    }

    public static abstract class ZoomListener extends GraphListener {
    }

    public void fireGraphResizeEvent(NetworkEvent event) {
        if (resizeListener != null) {
            resizeListener.onFired(event);
        }
    }

    public void fireGraphStabilizationStartEvent(NetworkEvent event) {
        if (stabilizationStartListener != null) {
            stabilizationStartListener.onFired(event);
        }
    }

    public void fireGraphStabilizedEvent(NetworkEvent event) {
        if (stabilizedListener != null) {
            stabilizedListener.onFired(event);
        }
    }

    public void fireGraphViewChangedEvent(NetworkEvent event) {
        if (viewChangedListener != null) {
            viewChangedListener.onFired(event);
        }
    }

    public void fireGraphZoomEvent(NetworkEvent event) {
        if (zoomListener != null) {
            zoomListener.onFired(event);
        }
    }

    public void fireSelectEvent(SelectEvent event) {
        for (String nodeID : event.getNodeIds()) {
            for (SelectListener listener : selectListeners) {
                listener.onFired(event);
            }
        }
    }

    public void fireClickEvent(ClickEvent event) {
        for (String nodeID : event.getNodeIds()) {
            for (ClickListener listener : clickListeners) {
                listener.onFired(event);
            }
        }
    }

    public void fireDoubleClickEvent(DoubleClickEvent event) {
        for (String nodeID : event.getNodeIds()) {
            for (DoubleClickListener listener : doubleClickListeners) {
                listener.onFired(event);
            }
        }
    }

    public void fireHoverEvent(HoverEvent event) {
        for (String nodeID : event.getNodeIds()) {
            for (HoverListener listener : hoverListeners) {
                listener.onFired(event);
            }
        }

    }

    public void fireBlurEvent(BlurEvent event) {
        for (String nodeID : event.getNodeIds()) {
            for (BlurListener listener : blurListeners) {
                listener.onFired(event);
            }
        }
    }

    public void fireDragStartEvent(DragStartEvent event) {
        for (String nodeID : event.getNodeIds()) {
            for (DragStartListener listener : dragStartListeners) {
                listener.onFired(event);

            }
        }
    }

    public void fireDragEndEvent(DragEndEvent event) {
        for (String nodeID : event.getNodeIds()) {
            for (DragEndListener listener : dragEndListeners) {

                listener.onFired(event);
            }
        }
    }
}
