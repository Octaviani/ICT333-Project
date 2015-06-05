package org.vaadin.visjs.networkDiagram;

import org.vaadin.visjs.networkDiagram.entity.NodeEntity;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.vaadin.visjs.networkDiagram.event.node.BlurEvent;
import org.vaadin.visjs.networkDiagram.event.node.ClickEvent;
import org.vaadin.visjs.networkDiagram.event.node.DoubleClickEvent;
import org.vaadin.visjs.networkDiagram.event.node.DragEndEvent;
import org.vaadin.visjs.networkDiagram.event.node.DragStartEvent;
import org.vaadin.visjs.networkDiagram.event.node.HoverEvent;
import org.vaadin.visjs.networkDiagram.event.node.SelectEvent;

public class Node extends NodeEntity {

    public final List<Node.NodeSelectListener> nodeSelectListeners = new CopyOnWriteArrayList<>();
    public final List<Node.NodeClickListener> nodeClickListeners = new CopyOnWriteArrayList<>();
    public final List<Node.NodeDoubleClickListener> nodeDoubleClickListeners = new CopyOnWriteArrayList<>();
    public final List<Node.NodeHoverListener> nodeHoverListeners = new CopyOnWriteArrayList<>();
    public final List<Node.NodeBlurListener> nodeBlurListeners = new CopyOnWriteArrayList<>();
    public final List<Node.NodeDragStartListener> nodeDragStartListeners = new CopyOnWriteArrayList<>();
    public final List<Node.NodeDragEndListener> nodeDragEndListeners = new CopyOnWriteArrayList<>();

    public Node(String label) {
        super(label);
    }

    public Node(String label, String image) {
        super(label, image);
    }

    public Node(String label, NodeEntity.Shape shape, String group) {
        super(label, shape, group);
    }

    public Node(String label, NodeEntity.Shape shape, String group, String image) {
        super(label, shape, group, image);
    }

    @Deprecated
    public Node(int id, String label) {
        super(id, label);
    }

    @Deprecated
    public Node(int id, String label, String image) {
        super(id, label, image);
    }

    @Deprecated
    public Node(String id, String label, String image) {
        super(id, label, image);
    }

    @Deprecated
    public Node(int id, String label, NodeEntity.Shape shape, String group) {
        super(id, label, shape, group);
    }

    @Deprecated
    public Node(String id, String label, NodeEntity.Shape shape, String group) {
        super(id, label, shape, group);
    }

    @Deprecated
    public Node(int id, String label, NodeEntity.Shape shape, String group, String image) {
        super(id, label, shape, group, image);
    }

    @Deprecated
    public Node(String id, String label, NodeEntity.Shape shape, String group, String image) {
        super(id, label, shape, group, image);
    }

    public abstract class NodeSelectListener {

        public abstract void onFired(SelectEvent event);
    }

    public static abstract class NodeClickListener {

        public abstract void onFired(ClickEvent event);
    }

    public static abstract class NodeDoubleClickListener {

        public abstract void onFired(DoubleClickEvent event);
    }

    public static abstract class NodeHoverListener {

        public abstract void onFired(HoverEvent event);
    }

    public static abstract class NodeBlurListener {

        public abstract void onFired(BlurEvent event);
    }

    public static abstract class NodeDragStartListener {

        public abstract void onFired(DragStartEvent event);
    }

    public static abstract class NodeDragEndListener {

        public abstract void onFired(DragEndEvent event);
    }

    public void addSelectListener(Node.NodeSelectListener listener) {
        nodeSelectListeners.add(listener);
    }

    public void removeSelectListener(Node.NodeSelectListener listener) {
        nodeSelectListeners.remove(listener);
    }

    public void removeClickListeners(Node.NodeClickListener listener) {
        nodeClickListeners.remove(listener);
    }

    public void addClickListener(Node.NodeClickListener nodeClickListener) {
        this.nodeClickListeners.add(nodeClickListener);
    }

    public void removeDoubleClickListener(Node.NodeDoubleClickListener listener) {
        nodeDoubleClickListeners.remove(listener);
    }

    public void addDoubleClickListener(Node.NodeDoubleClickListener listener) {
        nodeDoubleClickListeners.add(listener);
    }

    public void removeHoverListener(Node.NodeHoverListener listener) {
        nodeHoverListeners.remove(listener);
    }

    public void addHoverListener(Node.NodeHoverListener listener) {
        this.nodeHoverListeners.add(listener);
    }

    public void removeBlurListener(Node.NodeBlurListener listener) {
        nodeBlurListeners.remove(listener);
    }

    public void addBlurListener(Node.NodeBlurListener listener) {
        this.nodeBlurListeners.add(listener);
    }

    public void removeDragStartListener(Node.NodeDragStartListener listener) {
        nodeDragStartListeners.remove(listener);
    }

    public void addDragStartListener(Node.NodeDragStartListener listener) {
        this.nodeDragStartListeners.add(listener);
    }

    public void removeDragEndListener(Node.NodeDragEndListener listener) {
        nodeDragEndListeners.remove(listener);
    }

    public void addDragEndListener(Node.NodeDragEndListener listener) {
        this.nodeDragEndListeners.add(listener);
    }
}
