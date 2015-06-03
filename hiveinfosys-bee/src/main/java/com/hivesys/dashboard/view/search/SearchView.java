package com.hivesys.dashboard.view.search;

import static com.hivesys.dashboard.view.repository.UploadView.TITLE_ID;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.themes.ValoTheme;
import java.util.List;
import org.vaadin.visjs.networkDiagram.Color;
import org.vaadin.visjs.networkDiagram.Edge;
import org.vaadin.visjs.networkDiagram.NetworkDiagram;
import org.vaadin.visjs.networkDiagram.Node;
import org.vaadin.visjs.networkDiagram.event.node.ClickEvent;
import org.vaadin.visjs.networkDiagram.event.node.DoubleClickEvent;
import org.vaadin.visjs.networkDiagram.options.Options;

@SuppressWarnings("serial")
public class SearchView extends Panel implements View {

    public static final String NAME = "search";
    private VerticalLayout root;
    private CssLayout dashboardPanels;
    private Label labelTitle;
    public static int swi = 0;

    TextField mSearchBox;
    
    TextualView textualview;
    GraphView graphview;
    

    public SearchView() {
        final VerticalLayout root = new VerticalLayout();
        final CssLayout css = new CssLayout();

        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();

        root.setSizeFull();
        root.setMargin(true);
        root.addStyleName("dashboard-view");
        setContent(root);

        //css.addComponent(new Threejs());   
        Component content = buildContent();
        root.addComponent(content);
        root.setExpandRatio(content, 1);
    }

    Component buildContent() {
        VerticalLayout vLayout = new VerticalLayout();

        this.mSearchBox = new TextField("Search Box");
        this.mSearchBox.setWidth("100%");

        // refresh the tab on click vaadin bug?
        TabSheet tabs = new TabSheet();

        tabs.setHeight(100.0f, Unit.PERCENTAGE);
        tabs.addStyleName(ValoTheme.TABSHEET_FRAMED);
        tabs.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        tabs.setImmediate(true);

        Component graph2d = buildGraph();
        graph2d.setId("graph2dbuggy");

        this.graphview = new GraphView();
        this.textualview = new TextualView();
        
        ShortcutListener shortcut = new ShortcutListener("Enter",
                ShortcutAction.KeyCode.ENTER, null) {
                    @Override
                    public void handleAction(Object sender, Object target) {
                        textualview.UpdateSearchPane(mSearchBox.getValue());
                        graphview.RebuildGraph(mSearchBox.getValue());
                    }
                };

        this.mSearchBox.addShortcutListener(shortcut);

        Component graphcameron = build2dGraph();
        graphcameron.setId("cameron-graph");

        tabs.addTab(this.textualview, "Search");
        tabs.addTab(graph2d, "2D Visualization");
        tabs.addTab(this.graphview, "Actual Visualization");
        tabs.addTab(graphcameron, "Cameron's Visualization");

        tabs.addSelectedTabChangeListener((TabSheet.SelectedTabChangeEvent event) -> {
            Component selected = tabs.getSelectedTab();
            Tab cur = tabs.getTab(selected);
            // vaadin tabsheet bugs on refresh
            
            if (selected == this.graphview)
            {
                this.graphview.RebuildGraph(this.mSearchBox.getValue());
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
        vLayout.addComponent(this.mSearchBox);
        vLayout.addComponent(new Label("<br>", ContentMode.HTML));
        vLayout.addComponent(tabs);
        vLayout.setSizeFull();
        vLayout.setExpandRatio(tabs, 1);
        return vLayout;
    }

    class MyNodeDoubleClickListener extends Node.NodeDoubleClickListener {

        NetworkDiagram networkDiagram;

        public MyNodeDoubleClickListener(Node node, NetworkDiagram nd) {
            super(node);
            networkDiagram = nd;
        }

        public void setNode(Node node) {

        }

        @Override
        public void onFired(DoubleClickEvent event) {
            List<String> nodeid = event.getNodeIds();
            for (int i = 0; i < nodeid.size(); i++) {
                Notification.show(nodeid.get(i));
            }

            Node node2 = new Node("jksdjksd");
            Edge edge1 = new Edge(nodeid.get(0), node2.getId());
            networkDiagram.addNode(node2);
            networkDiagram.addEdge(edge1);

            networkDiagram.addNodeDoubleClickListener(new MyNodeDoubleClickListener(node2, networkDiagram));
        }

    }

    Component build2dGraph() {
        NetworkDiagram networkDiagram = new NetworkDiagram(new Options());
        networkDiagram.setSizeUndefined();
        networkDiagram.setSizeFull();

        Node node1 = new Node("dncjsdcs");
        networkDiagram.addNode(node1);
        networkDiagram.addNodeDoubleClickListener(new MyNodeDoubleClickListener(node1, networkDiagram));

        return networkDiagram;
    }

    Component buildGraph() {
        NetworkDiagram networkDiagram = new NetworkDiagram(new Options());
        networkDiagram.setSizeUndefined();
        networkDiagram.setSizeFull();

        Node node1 = new Node(1, "circle", Node.Shape.circle, "group_x");
        Node node2 = new Node(2, "ellipse", Node.Shape.ellipse, "group_x");
        Node node3 = new Node(3, "database", Node.Shape.database, "group_x");
        Node node4 = new Node(4, "box", Node.Shape.box, "group_x");
        Node node5 = new Node(5, "shapes\nand\nsizes", Node.Shape.box, "group_main");

        Edge edge1 = new Edge(3, 1, Edge.Style.arrow);
        Edge edge2 = new Edge(1, 4, Edge.Style.dashLine);
        Edge edge3 = new Edge(1, 2, Edge.Style.arrowCenter);

        networkDiagram.addNode(node1, node2, node3, node4, node5);
        networkDiagram.addEdge(edge1, edge2, edge3);

        int id = 6;
        int mainId = 5;

        for (int size = 1; size < 4; size++) {
            int groupId = id;
            Node node = new Node(id, "size " + size, Node.Shape.box, "group" + size);
            node.setMass(size);
            networkDiagram.addNode(node);
            networkDiagram.addEdge(new Edge(mainId, id, new Color("gray"), size));
            id++;

            for (Node.Shape shape : Node.Shape.values()) {
                if (shape != Node.Shape.image) {
                    node = new Node(id, shape.toString(), shape, "group" + size);
                    networkDiagram.addNode(node);
                    networkDiagram.addEdge(new Edge(groupId, id, new Color("red"), size));
                    id++;
                }
            }
        }

        networkDiagram.addNodeClickListener(new Node.NodeClickListener(node5) {

            @Override
            public void onFired(ClickEvent event) {
                List<String> nodeid = event.getNodeIds();
                for (int i = 0; i < nodeid.size(); i++) {
                    Notification.show(nodeid.get(i));
                }
            }
        });

        return networkDiagram;
    }

    @Override
    public void enter(ViewChangeEvent event) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
