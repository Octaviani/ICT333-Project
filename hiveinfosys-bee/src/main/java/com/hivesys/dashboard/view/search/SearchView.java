package com.hivesys.dashboard.view.search;

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.visjs.networkDiagram.Color;
import org.vaadin.visjs.networkDiagram.Edge;
import org.vaadin.visjs.networkDiagram.NetworkDiagram;
import org.vaadin.visjs.networkDiagram.Node;
import org.vaadin.visjs.networkDiagram.event.node.DoubleClickEvent;
import org.vaadin.visjs.networkDiagram.options.Options;

@SuppressWarnings("serial")
public class SearchView extends Panel implements View {

    public static final String NAME = "search";
    private VerticalLayout root;
    public static int swi = 0;

    TextField mSearchBox;

    TextualView textualview;
    GraphView graphview;

    public SearchView() {
        root = new VerticalLayout();

        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();

        root.setSizeFull();
        root.setMargin(true);
        root.addStyleName("dashboard-view");
        setContent(root);

        
        this.mSearchBox = new TextField("Search Box");
        this.mSearchBox.setWidth("100%");

        // refresh the tab on click vaadin bug?
        TabSheet tabs = new TabSheet();

        tabs.setHeight(100.0f, Unit.PERCENTAGE);
        tabs.addStyleName(ValoTheme.TABSHEET_FRAMED);
        tabs.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        tabs.setImmediate(true);

        this.graphview = new GraphView();
        this.textualview = new TextualView();

        ShortcutListener shortcut = new ShortcutListener("Enter",
                ShortcutAction.KeyCode.ENTER, null) {
                    @Override
                    public void handleAction(Object sender, Object target) {
                        textualview.UpdateSearchPane(mSearchBox.getValue());
                        graphview.UpdateRootSearch(mSearchBox.getValue());
                    }
                };

        this.mSearchBox.addShortcutListener(shortcut);

        Component graphcameron = build2dGraph();
        graphcameron.setId("cameron-graph");

        Component graph2d = buildGraph();
        graph2d.setId("graph2dbuggy");

        tabs.addTab(this.textualview, "Search");
        tabs.addTab(this.graphview, "Visualize");
        
      
        //tabs.addTab(graph2d, "Experimental Static Immersive");
        //tabs.addTab(graphcameron, "Experimental Dynamic Graph");

        tabs.addSelectedTabChangeListener((TabSheet.SelectedTabChangeEvent event) -> {
            Component selected = tabs.getSelectedTab();
            Tab cur = tabs.getTab(selected);
            // vaadin tabsheet bugs on refresh

            if (selected == this.graphview) {
                this.graphview.BuildGraph();
            }

            if (selected == null || selected.getId() == null) {
                return;
            }

            switch (selected.getId()) {
                case "graph2dbuggy": {
                    if (swi == 1) {
                        swi = 0;
                        Component gr2d = buildGraph();
                        gr2d.setId("graph2dbuggy");
                        tabs.replaceComponent(selected, gr2d);
                    } else {
                        swi = 1;
                    }
                    break;
                }

                case "cameron-graph": {
                    if (swi == 1) {
                        swi = 0;
                        Component gr2d = build2dGraph();
                        gr2d.setId("cameron-graph");
                        tabs.replaceComponent(selected, gr2d);
                    } else {
                        swi = 1;
                    }
                    break;
                }
            }

        });

        tabs.setSizeFull();
        root.addComponent(this.mSearchBox);
        root.addComponent(new Label("<br>", ContentMode.HTML));
        root.addComponent(tabs);
        root.setExpandRatio(tabs, 1);
    }

    Component build2dGraph() {
        NetworkDiagram networkDiagram = new NetworkDiagram(new Options());
        networkDiagram.setSizeUndefined();
        networkDiagram.setSizeFull();

        Node node1 = new Node("dncjsdcs");
        networkDiagram.addNode(node1);
        networkDiagram.addDoubleClickListener(new NetworkDiagram.DoubleClickListener() {

            @Override
            public void onFired(DoubleClickEvent event) {
                if (event.getNodeIds().size() > 0) {
                    Node node2 = new Node("Hello");
                    Edge edge1 = new Edge(event.getNodes().get(0), node2);
                    networkDiagram.addNode(node2);
                    networkDiagram.addEdge(edge1);
                }
            }
        });

        return networkDiagram;
    }

    Component buildGraph() {
        NetworkDiagram networkDiagram = new NetworkDiagram(new Options());
        networkDiagram.setSizeUndefined();
        networkDiagram.setSizeFull();

        Node node1 = new Node("circle", Node.Shape.circle, "group_x");
        Node node2 = new Node("ellipse", Node.Shape.ellipse, "group_x");
        Node node3 = new Node("database", Node.Shape.database, "group_x");
        Node node4 = new Node("box", Node.Shape.box, "group_x");
        Node node5 = new Node("shapes\nand\nsizes", Node.Shape.box, "group_main");

        Edge edge1 = new Edge(node3, node1, Edge.Style.arrow);
        Edge edge2 = new Edge(node1, node4, Edge.Style.dashLine);
        Edge edge3 = new Edge(node1, node2, Edge.Style.arrowCenter);

        networkDiagram.addNode(node1, node2, node3, node4, node5);
        networkDiagram.addEdge(edge1, edge2, edge3);

        for (int size = 1; size < 4; size++) {
            Node node = new Node("size " + size, Node.Shape.box, "group" + size);
            node.setMass(size);
            networkDiagram.addNode(node);
            networkDiagram.addEdge(new Edge(node5, node, new Color("gray"), size));

            for (Node.Shape shape : Node.Shape.values()) {
                if (shape != Node.Shape.image) {
                    Node childnode = new Node(shape.toString(), shape, "group" + size);
                    networkDiagram.addNode(childnode);
                    networkDiagram.addEdge(new Edge(node, childnode, new Color("red"), size));
                }
            }
        }

        return networkDiagram;
    }

    @Override
    public void enter(ViewChangeEvent event) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
