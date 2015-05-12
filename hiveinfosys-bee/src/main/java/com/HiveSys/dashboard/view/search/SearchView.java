package com.HiveSys.dashboard.view.search;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;

import com.HiveSys.core.SolrConnection;
import com.HiveSys.dashboard.layout.SearchLayout;
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

	public SearchView() {
		this.tfSearch.setImmediate(true);
		//this.panel.setSizeFull();

		// handle enter key shortcut
		ShortcutListener shortcut = new ShortcutListener("Enter",
				ShortcutAction.KeyCode.ENTER, null) {
			@Override
			public void handleAction(Object sender, Object target) {
				submitQuery();
			}
		};

		this.tfSearch.addShortcutListener(shortcut);
	}

	public void updateSearchResultsView(SolrDocumentList docs) {
		panel.setContent(null);

		// use csslayout and only override label, because labels has the bare
		// minimum content defined for styles. See styles.css
		CssLayout csslayout = new CssLayout() {
			@Override
			protected String getCss(Component c) {
				if (c.getStyleName().contains("hellome")) {
				//if (c instanceof Label) {
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
		for (int i = 0; i < nresults; i++) {

			Object lastModified = docs.get(i).getFieldValue("last_modified");
			if (lastModified != null) {
				com.vaadin.ui.
				CustomComponent hello = new CustomComponent();
				hello.setCaption("<p>Fuck</p>");
				
				Link linkDocument = new Link("Hello.docs", new ExternalResource("http://www.google.com"));
				Label lblDate = new Label(
						"<span class='v-button-wrap'>" +
						"<span class='v-button-caption'>" +
						lastModified.toString() + "</span>" + "</span>",
						ContentMode.HTML);
				
				lblDate.setStyleName("hellome");
				Label lblDate2 = new Label(
						"<span class='v-button-wrap'>" +
						"<span class='v-button-caption'>" +
						lastModified.toString() + "<br><br></span>" + "</span>",
						ContentMode.HTML);
				
				lblDate.setStyleName("hellome");
				csslayout.addComponent(linkDocument);
				csslayout.addComponent(lblDate);
				csslayout.addComponent(lblDate2);
				csslayout.addComponent(hello);
			}
		}
		System.out.println("hello");

		//panel.setWidth("300px");
		//panel.setHeight("300px");
		panel.setContent(csslayout);
	}

	public void submitQuery() {
		try {
			SolrDocumentList doclist = null;
			doclist = SolrConnection.getDefault().query(tfSearch.getValue());

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
