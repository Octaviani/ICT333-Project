/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hivesys.dashboard.view.search;

import com.hivesys.core.ElasticSearchContext;
import com.hivesys.core.FileInfoController;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.sql.SQLException;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightField;

/**
 *
 * @author swoorup
 */
public class TextualView extends Panel {

    CssLayout css;

    public TextualView() {
        this.setSizeFull();
        css = new CssLayout();
        css.setStyleName("search-textual-view");

        this.setContent(css);
    }

    public void UpdateSearchPane(String searchString) {
        css.removeAllComponents();

        SearchResponse response = ElasticSearchContext.getInstance().searchSimpleQuery(searchString);
        logResponse(response);
        SearchHits results = response.getHits();

        Label labelResultSummary = new Label("About " + results.getHits().length + " results found <br><br>", ContentMode.HTML);
        css.addComponent(labelResultSummary);

        for (SearchHit hit : results) {
            CssLayout cssResult = new CssLayout();
            cssResult.setStyleName("search-result");

            try {
                String filename = FileInfoController.getInstance().getFileNameFromHash(hit.getId());
                String boxviewID = FileInfoController.getInstance().getBoxViewIDFromHash(hit.getId());

                String highlight = "";
                HighlightField objhighlight = hit.highlightFields().get("file");
                if (objhighlight != null) {
                    for (Text fgmt : objhighlight.getFragments()) {
                        highlight += fgmt.string() + "<br>";
                    }
                }

                Button lblfileName = new Button(filename);
                lblfileName.addClickListener((Button.ClickEvent event) -> {
                  if (boxviewID != null) {
                      String url = FileInfoController.getInstance().getViewURL(boxviewID);
                      if (url != null || !url.equals(""))
                      {
                        String url = FileInfoController.getInstance().getViewURL(boxviewID);
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
                        return;
                      }
                      Notification.show("Preview not available for this document!");
                    }
                });
                lblfileName.setPrimaryStyleName("filename");


                Label lblHighlight = new Label(highlight, ContentMode.HTML);
                lblHighlight.setStyleName("highlight");

                cssResult.addComponent(lblfileName);
                cssResult.addComponent(lblHighlight);

                css.addComponent(cssResult);
                css.addComponent(new Label("<br>", ContentMode.HTML));

            } catch (SQLException ex) {
            }

        }

    }

    public static void logResponse(SearchResponse response) {
        SearchHits results = response.getHits();
        System.out.println("Current results: " + results.getHits().length);

        for (SearchHit hit : results) {
            System.out.println("hit ID: " + hit.getId());
        }
    }
}
