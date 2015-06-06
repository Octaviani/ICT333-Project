package com.hivesys.dashboard.view.repository;

import com.box.view.BoxViewException;
import com.box.view.Document;
import com.hivesys.core.db.DBConnectionPool;
import com.hivesys.core.BoxViewDocuments;
import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author swoorup
 */
public final class RepositoryView extends Panel implements View {

    public RepositoryView() {
        try {
            final VerticalLayout root = new VerticalLayout();

            //css.addComponent(new Threejs());
            root.setSizeFull();
            root.setMargin(true);
            //root.addStyleName("dashboard-view");
            setContent(root);
            addStyleName(ValoTheme.PANEL_BORDERLESS);
            setSizeFull();
            //css.addComponent(GridRendererSample());

            // Create a grid
            SQLContainer container = new SQLContainer(new FreeformQuery(
                    "SELECT Document.ID as ID, concat(User.FirstName, ' ', User.LastName) as 'Uploaded By' , \n"
                    + "Document.BoxViewID, \n"
                    + "Document.filename as 'File Name', Document.UploadDate as 'Uploaded Date', \n"
                    + "Document.Author, \n"
                    + "Document.CreatedDate as 'Created Date', \n"
                    + "Document.Description FROM Document \n"
                    + "LEFT JOIN User ON Document.UserId = User.ID;", DBConnectionPool.getInstance().getConnectionPool()));

            Grid grid = new Grid(container);
            grid.removeColumn("ID");
            grid.removeColumn("BoxViewID");
            //Grid.Column idcolumn = grid.getColumn("ID");
            //grid.addColumn("ID");

            grid.addItemClickListener(new ItemClickEvent.ItemClickListener() {

                @Override
                public void itemClick(ItemClickEvent event) {
                    if (event.isDoubleClick()) {
                        Object boxviewID = container.getContainerProperty(event.getItemId(), "BoxViewID").getValue();
                        if (boxviewID != null) {
                            Notification.show("Opening Preview!");
                            String url = BoxViewDocuments.getInstance().getViewURL(boxviewID.toString());
                            if (url.equals("")) {
                                Notification.show("Preview site is down for the document!");
                                return;
                            }
                            Object objfilename = container.getContainerProperty(event.getItemId(), "File Name").getValue();
                            String filename = "";

                            if (objfilename != null) {
                                filename = objfilename.toString();
                            }

                            BrowserFrame bframe = new BrowserFrame(filename, new ExternalResource(url));
                            VerticalLayout vlayout = new VerticalLayout(bframe);

                            final Window w = new Window();
                            w.setSizeFull();
                            w.setModal(true);
                            w.setWindowMode(WindowMode.MAXIMIZED);
                            w.setContent(vlayout);
                            vlayout.setSizeFull();
                            vlayout.setMargin(true);
                            w.setResizable(false);
                            w.setDraggable(false);

                            UI.getCurrent().addWindow(w);
                            bframe.setSizeFull();

                        } else {
                            Notification.show("Cannot open preview for the document!");
                        }
                    }
                }
            }
            );


            /*
             grid.addColumn("index", Integer.class)
             .setRenderer(new NumberRenderer("%02d")).setHeaderCaption("##")
             .setExpandRatio(0);

             grid.getColumn("index").setWidth(50);

             grid.addColumn("name", String.class)
             .setRenderer(new BoldLastNameRenderer()).setExpandRatio(1);

             grid.addColumn("progress", Double.class)
             .setRenderer(new ProgressBarRenderer()).setExpandRatio(2);

             grid.addColumn("edit", String.class).setWidth(35)
             .setRenderer(new IconRender());

             grid.addColumn("delete", String.class).setWidth(35)
             .setRenderer(new ButtonRenderer(new RendererClickListener() {
             @Override
             public void click(RendererClickEvent e) {
             Notification.show("Deleted item " + e.getItemId());
             }
             }));

             grid.getDefaultHeaderRow().join("edit", "delete").setText("Tools");

             Random r = new Random();
             for (int i = 0; i < 100; ++i) {
             String[] name = {"sasa", "sasa"};
             grid.addRow(
             i,
             name[0] + ' ' + name[1],
             Math.sin(i / 3.0) / 2.0 + 0.5,
             Icon.pencil,
             " " + Icon.trash);
             }*/
            grid.setSizeFull();

            root.addComponent(grid);
        } catch (SQLException ex) {
            Logger.getLogger(RepositoryView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // TODO
    }

}
