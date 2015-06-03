package com.hivesys.dashboard.view.search;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;

import com.hivesys.core.SolrConnection;
import com.hivesys.dashboard.layout.SearchLayout;
import com.hivesys.javascript.Threejs;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

@SuppressWarnings("serial")
public class SearchView extends SearchLayout implements View {

    public static final String NAME = "search";
    private VerticalLayout root;
    private CssLayout dashboardPanels;
    private Label labelTitle;
    
    public SearchView() {
        
        this.panel.setContent(new Threejs());
        /*
        this.tfSearch.setImmediate(true);
	this.panel.setSizeFull();

        // handle enter key shortcut
        ShortcutListener shortcut = new ShortcutListener("Enter",
                ShortcutAction.KeyCode.ENTER, null) {
                    @Override
                    public void handleAction(Object sender, Object target) {
                        submitQuery();
                    }
                };

        this.tfSearch.addShortcutListener(shortcut);
        */
    }

    public void updateSearchResultsView(SolrDocumentList docs) {
        panel.setContent(null);

		// use csslayout and only override label, because labels has the bare
        // minimum content defined for styles. See styles.css
        CssLayout csslayout = new CssLayout() {
            @Override
            protected String getCss(Component c) {
                if (c.getStyleName().contains("hellome")) {
					// if (c instanceof Label) {
                    // Color the boxes with random colors
                    int rgb = (int) (Math.random() * (1 << 24));
                    return "display:inline-block; width: #"
                            + Integer.toHexString(rgb) + "width: 99px";
                }
                return null;
            }
        };
        csslayout.setSizeUndefined();
        int nresults = docs.size();

        Label labelResultSummary = new Label("About " + nresults
                + " results found <br><br>", ContentMode.HTML);
        csslayout.addComponent(labelResultSummary);

        for (int i = 0; i < nresults; i++) {

            Object streamName = docs.get(i).getFieldValue("stream_name");
            Object resourcepath = docs.get(i).getFieldValue("resourcename");

            Object lastModified = docs.get(i).getFieldValue("last_modified");
            Object contentType = docs.get(i).getFieldValue("content_type");

            System.out.println(docs.get(i).toString());
            if (lastModified != null) {
                Link linkDocument = new Link(streamName.toString(),
                        new ExternalResource(resourcepath.toString()));
                linkDocument.setCaptionAsHtml(true);
                linkDocument.setCaption("<h3>" + streamName.toString()
                        + "</h3>");
                Label labelLastModifiedDate = new Label(
                        "<span class='v-button-wrap'>"
                        + "<span class='v-button-caption'>"
                        + lastModified.toString() + "</span>"
                        + "</span>", ContentMode.HTML);

                labelLastModifiedDate.setStyleName("hellome");

                Label labelContentType = new Label(contentType.toString()
                        + "<br>", ContentMode.HTML);

                labelLastModifiedDate.setStyleName("hellome");
                csslayout.addComponent(linkDocument);
                csslayout.addComponent(labelLastModifiedDate);
                csslayout.addComponent(labelContentType);
            }
        }
        System.out.println("hello");

		// panel.setWidth("300px");
        // panel.setHeight("300px");
        panel.setContent(csslayout);
    }

    public void submitQuery() {
        try {
            SolrDocumentList doclist = null;
            doclist = SolrConnection.getInstance().query(tfSearch.getValue());

            System.out.println("Number of Search results: " + doclist.size());
            updateSearchResultsView(doclist);

        } catch (SolrServerException | IOException e) {
            Notification notification = new Notification("Solr Server Error!");
            notification.setDelayMsec(8000);
            notification
                    .setDescription("It looks the Solr server is not running. Please consult your IT Administrator");
            notification.setStyleName("tray dark small closable login-help");
            notification.setPosition(Position.TOP_CENTER);
            notification.show(Page.getCurrent());
            e.printStackTrace();
        }
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }
}
