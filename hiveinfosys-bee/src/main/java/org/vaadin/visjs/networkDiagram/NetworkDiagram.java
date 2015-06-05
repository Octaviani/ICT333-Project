package org.vaadin.visjs.networkDiagram;

import org.vaadin.visjs.networkDiagram.entity.NodeEntity;
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
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.vaadin.visjs.networkDiagram.entity.EdgeEntity;

/**
 * Created by roshans on 10/10/14.
 */
@JavaScript({"js/vis.min.js", "js/networkDiagram-connector.js"})
@StyleSheet({"css/vis.css", "css/networkDiagram.css"})
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
    private final List<NodeEntity.NodeSelectListener> nodeSelectListeners = new CopyOnWriteArrayList<>();
    private final List<NodeEntity.NodeClickListener> nodeClickListeners = new CopyOnWriteArrayList<>();
    private final List<NodeEntity.NodeDoubleClickListener> nodeDoubleClickListeners = new CopyOnWriteArrayList<>();
    private final List<NodeEntity.NodeHoverListener> nodeHoverListeners = new CopyOnWriteArrayList<>();
    private final List<NodeEntity.NodeBlurListener> nodeBlurListeners = new CopyOnWriteArrayList<>();
    private final List<NodeEntity.NodeDragStartListener> nodeDragStartListeners = new CopyOnWriteArrayList<>();
    private final List<NodeEntity.NodeDragEndListener> nodeDragEndListeners = new CopyOnWriteArrayList<>();

    private final List<Node> mNodes = new CopyOnWriteArrayList<>();
    private final List<Edge> mEdges = new CopyOnWriteArrayList<>();

    private ResizeListener resizeListener;
    private StabilizationStartListener stabilizationStartListener;
    private StabilizedListener stabilizedListener;
    private ViewChangedListener viewChangedListener;
    private ZoomListener zoomListener;
    private final Gson gson = new Gson();

    public NetworkDiagram(Options options) {
        super();
        addFunction(Constants.ON_SELECT, new JavaScriptFunction() {
            @Override
            public void call(final JsonArray properties) throws JsonException {
                SelectEvent event = EventGenerator.getNodeSelectEvent(properties);
                fireNodeSelectEvent(event);
            }
        });
        addFunction(Constants.ON_CLICK, new JavaScriptFunction() {
            @Override
            public void call(final JsonArray properties) throws JsonException {
                ClickEvent event = EventGenerator.getNodeClickEvent(properties);
                fireNodeClickEvent(event);
            }
        });
        addFunction(Constants.ON_DOUBLE_CLICK, new JavaScriptFunction() {
            @Override
            public void call(final JsonArray properties) throws JsonException {
                DoubleClickEvent event = EventGenerator.getNodeDoubleClickEvent(properties);
                fireNodeDoubleClickEvent(event);
            }
        });
        addFunction(Constants.ON_HOVER_NODE, new JavaScriptFunction() {
            @Override
            public void call(final JsonArray properties) throws JsonException {
                HoverEvent event = EventGenerator.getNodeHoverEvent(properties);
                fireNodeHoverEvent(event);
            }
        });
        addFunction(Constants.ON_BLUR_NODE, new JavaScriptFunction() {
            @Override
            public void call(final JsonArray properties) throws JsonException {
                BlurEvent event = EventGenerator.getNodeBlurEvent(properties);
                fireNodeBlurEvent(event);
            }
        });
        addFunction(Constants.ON_DRAG_START, new JavaScriptFunction() {
            @Override
            public void call(final JsonArray properties) throws JsonException {
                DragStartEvent event = EventGenerator.getNodeDragStartEvent(properties);
                fireNodeDragStartEvent(event);
            }
        });
        addFunction(Constants.ON_DRAG_END, new JavaScriptFunction() {
            @Override
            public void call(final JsonArray properties) throws JsonException {
                DragEndEvent event = EventGenerator.getNodeDragEndEvent(properties);
                fireNodeDragEndEvent(event);
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

        callFunction("init", gson.toJson(options));
    }

    public NetworkDiagramState getState() {
        return (NetworkDiagramState) super.getState();
    }

    public void updateOptions(Options options) {
        getState().updates++;
        callFunction("updateOptions", gson.toJson(options));
    }

    public void addNode(Node... nodes) {

        String json = "[";
        for (Node n : nodes) {
            this.mNodes.add(n);
            json += gson.toJson(n, NodeEntity.class) + ",";
        }
        
        json = json.substring(0,json.length()-1) + "]";

        System.out.println(json);
        getState().updates++;
        callFunction("addNodes", json);
    }

    public void addNodes(List<Node> nodes) {
        String json = "[";
        for (Node n : nodes) {
            this.mNodes.add(n);
            json += gson.toJson(n, NodeEntity.class) + ",";
        }
        json = json.substring(0,json.length()-1) + "]";

        System.out.println(json);
        getState().updates++;
        callFunction("addNodes", json);
    }

    public void addEdges(List<Edge> edges) {
        String json = "[";
        for (Edge e : edges) {
            this.mEdges.add(e);
            json += gson.toJson(e, EdgeEntity.class) + ",";
        }
        json = json.substring(0,json.length()-1) + "]";

        getState().updates++;
        callFunction("addEdges", json);
    }

    public void addEdge(Edge... edges) {
        String json = "[";
        for (Edge e : edges) {
            this.mEdges.add(e);
            json += gson.toJson(e, EdgeEntity.class) + ",";
        }
        json = json.substring(0,json.length()-1) + "]";

        getState().updates++;
        callFunction("addEdges", json);
    }

    public void removeNode(Node... nodes) {
        String json = "[";
        for (Node n : nodes) {
            this.mNodes.remove(n);
            json += gson.toJson(n, NodeEntity.class) + ",";
        }
        json = json.substring(0,json.length()-1) + "]";

        getState().updates++;
        callFunction("removeNode", json);
    }

    public void removeEdge(Edge... edges) {
        String json = "[";
        for (Edge e : edges) {
            this.mEdges.remove(e);
            json += gson.toJson(e, EdgeEntity.class) + ",";
        }
        json = json.substring(0,json.length()-1) + "]";

        getState().updates++;
        callFunction("removeEdge", json);
    }

    public void updateNode(Node... nodes) {
        String json = "[";
        for (Node n : nodes) {
            json += gson.toJson(n, NodeEntity.class) + ",";
        }
        json = json.substring(0,json.length()-1) + "]";

        getState().updates++;
        callFunction("updateNode", json);
    }

    public void updateEdge(Edge... edges) {

        String json = "[";
        for (Edge e : edges) {
            json += gson.toJson(e, EdgeEntity.class) + ",";
        }
        json = json.substring(0,json.length()-1) + "]";
        getState().updates++;
        callFunction("updateEdge", json);
    }

    @Deprecated
    public void updateEdge(List<Edge> edges) {
        updateEdges(edges);
    }

    public void updateEdges(List<Edge> edges) {
        String json = "[";
        for (Edge e : edges) {
            json += gson.toJson(e, EdgeEntity.class) + ",";
        }
        json = json.substring(0,json.length()-1) + "]";
        getState().updates++;
        callFunction("updateEdge", json);
    }

    @Deprecated
    public void updateNode(List<Node> nodes) {
        updateNodes(nodes);
    }

    public void updateNodes(List<Node> nodes) {
        String json = "[";
        for (Node n : nodes) {
            json += gson.toJson(n, NodeEntity.class) + ",";
        }
        json = json.substring(0,json.length()-1) + "]";

        getState().updates++;
        callFunction("updateNode", json);
    }

    public void clearNodes() {
        this.mNodes.clear();
        callFunction("clearNodes");
    }

    public void clearEdges() {
        this.mEdges.clear();
        callFunction("clearEdges");
    }

    public void destroyNetwork() {
        this.mNodes.clear();
        this.mEdges.clear();

        callFunction("destroyNetwork");
    }

    public void clear() {
        clearEdges();
        clearNodes();
    }

    public void drawConnections() {
        callFunction("drawConnections");
    }

    @Deprecated
    public void addNodeSelectListener(NodeEntity.NodeSelectListener listener) {
        nodeSelectListeners.add(listener);
    }

    @Deprecated
    public void removeNodeSelectListener(NodeEntity.NodeSelectListener listener) {
        nodeSelectListeners.remove(listener);
    }

    @Deprecated
    public void removeNodeClickListeners(NodeEntity.NodeClickListener listener) {
        nodeClickListeners.remove(listener);
    }

    @Deprecated
    public void addNodeClickListener(NodeEntity.NodeClickListener nodeClickListener) {
        this.nodeClickListeners.add(nodeClickListener);
    }

    @Deprecated
    public void removeNodeDoubleClickListener(NodeEntity.NodeDoubleClickListener listener) {
        nodeDoubleClickListeners.remove(listener);
    }

    @Deprecated
    public void addNodeDoubleClickListener(NodeEntity.NodeDoubleClickListener listener) {
        nodeDoubleClickListeners.add(listener);
    }

    @Deprecated
    public void removeNodeHoverListener(NodeEntity.NodeHoverListener listener) {
        nodeHoverListeners.remove(listener);
    }

    @Deprecated
    public void addNodeHoverListener(NodeEntity.NodeHoverListener listener) {
        this.nodeHoverListeners.add(listener);
    }

    @Deprecated
    public void removeNodeBlurListener(NodeEntity.NodeBlurListener listener) {
        nodeBlurListeners.remove(listener);
    }

    @Deprecated
    public void addNodeBlurListener(NodeEntity.NodeBlurListener listener) {
        this.nodeBlurListeners.add(listener);
    }

    @Deprecated
    public void removeNodeDragStartListener(NodeEntity.NodeDragStartListener listener) {
        nodeDragStartListeners.remove(listener);
    }

    @Deprecated
    public void addNodeDragStartListener(NodeEntity.NodeDragStartListener listener) {
        this.nodeDragStartListeners.add(listener);
    }

    @Deprecated
    public void removeNodeDragEndListener(NodeEntity.NodeDragEndListener listener) {
        nodeDragEndListeners.remove(listener);
    }

    @Deprecated
    public void addNodeDragEndListener(NodeEntity.NodeDragEndListener listener) {
        this.nodeDragEndListeners.add(listener);
    }

    //adding and removing graph listeners
    @Deprecated

    public void addResizeListener(ResizeListener resizeListener) {
        this.resizeListener = resizeListener;
    }

    @Deprecated
    public void addStabilizationStartListener(StabilizationStartListener stabilizationStartListener) {
        this.stabilizationStartListener = stabilizationStartListener;
    }

    @Deprecated
    public void addStabilizedListener(StabilizedListener stabilizedListener) {
        this.stabilizedListener = stabilizedListener;
    }

    @Deprecated
    public void addViewChangedListener(ViewChangedListener viewChangedListener) {
        this.viewChangedListener = viewChangedListener;
    }

    @Deprecated
    public void addZoomListener(ZoomListener zoomListener) {
        this.zoomListener = zoomListener;
    }

    @Deprecated
    public void removeResizeListener() {
        this.resizeListener = null;
    }

    @Deprecated
    public void removeStabilizationStartListener() {
        this.stabilizationStartListener = null;
    }

    @Deprecated
    public void removeStabilizedListener() {
        this.stabilizedListener = null;
    }

    @Deprecated
    public void removeViewChangedListener() {
        this.viewChangedListener = null;
    }

    @Deprecated
    public void removeZoomListener() {
        this.zoomListener = null;
    }

    //listeners for entire graph
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

    public void fireNodeSelectEvent(SelectEvent event) {
        for (String nodeID : event.getNodeIds()) {
            for (Node n : this.mNodes) {
                if (n.getId().equals(nodeID)) {
                    for (Node.NodeSelectListener l : n.nodeSelectListeners) {
                        l.onFired(event);
                    }
                }
            }

            for (NodeEntity.NodeSelectListener listener : nodeSelectListeners) {
                if (listener.getNode().getId().equals(nodeID)) {
                    listener.onFired(event);
                }
            }

        }
    }

    public void fireNodeClickEvent(ClickEvent event) {
        for (String nodeID : event.getNodeIds()) {
            for (Node n : this.mNodes) {
                if (n.getId().equals(nodeID)) {
                    for (Node.NodeClickListener l : n.nodeClickListeners) {
                        l.onFired(event);
                    }
                }
            }

            for (NodeEntity.NodeClickListener listener : nodeClickListeners) {
                if (listener.getNode().getId().equals(nodeID)) {
                    listener.onFired(event);
                }
            }
        }
    }

    public void fireNodeDoubleClickEvent(DoubleClickEvent event) {
        for (String nodeID : event.getNodeIds()) {
            for (Node n : this.mNodes) {
                if (n.getId().equals(nodeID)) {
                    for (Node.NodeDoubleClickListener l : n.nodeDoubleClickListeners) {
                        l.onFired(event);
                    }
                }
            }

            for (NodeEntity.NodeDoubleClickListener listener : nodeDoubleClickListeners) {
                if (listener.getNode().getId().equals(nodeID)) {
                    listener.onFired(event);
                }
            }
        }
    }

    public void fireNodeHoverEvent(HoverEvent event) {
        for (String nodeID : event.getNodeIds()) {
            for (Node n : this.mNodes) {
                if (n.getId().equals(nodeID)) {
                    for (Node.NodeHoverListener l : n.nodeHoverListeners) {
                        l.onFired(event);
                    }
                }
            }

            for (NodeEntity.NodeHoverListener listener : nodeHoverListeners) {
                if (listener.getNode().getId().equals(nodeID)) {
                    listener.onFired(event);
                }
            }
        }

    }

    public void fireNodeBlurEvent(BlurEvent event) {
        for (String nodeID : event.getNodeIds()) {
            for (Node n : this.mNodes) {
                if (n.getId().equals(nodeID)) {
                    for (Node.NodeBlurListener l : n.nodeBlurListeners) {
                        l.onFired(event);
                    }
                }
            }

            for (NodeEntity.NodeBlurListener listener : nodeBlurListeners) {
                if (listener.getNode().getId().equals(nodeID)) {
                    listener.onFired(event);
                }
            }
        }
    }

    public void fireNodeDragStartEvent(DragStartEvent event) {
        for (String nodeID : event.getNodeIds()) {
            for (Node n : this.mNodes) {
                if (n.getId().equals(nodeID)) {
                    for (Node.NodeDragStartListener l : n.nodeDragStartListeners) {
                        l.onFired(event);
                    }
                }
            }

            for (NodeEntity.NodeDragStartListener listener : nodeDragStartListeners) {
                if (listener.getNode().getId().equals(nodeID)) {
                    listener.onFired(event);
                }
            }
        }
    }

    public void fireNodeDragEndEvent(DragEndEvent event) {
        for (String nodeID : event.getNodeIds()) {
            for (Node n : this.mNodes) {
                if (n.getId().equals(nodeID)) {
                    for (Node.NodeDragEndListener l : n.nodeDragEndListeners) {
                        l.onFired(event);
                    }
                }
            }

            for (NodeEntity.NodeDragEndListener listener : nodeDragEndListeners) {
                if (listener.getNode().getId().equals(nodeID)) {
                    listener.onFired(event);
                }
            }
        }
    }
}
