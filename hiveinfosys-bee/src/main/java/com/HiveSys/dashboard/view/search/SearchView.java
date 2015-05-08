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
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;

@SuppressWarnings("serial")
public class SearchView extends SearchLayout implements View {
	public static final String NAME = "search";

	public SearchView() {
		this.tfSearch.setImmediate(true);
		this.panel.setSizeFull();
		this.vertLayout.setSizeFull();

		// handle enter key shortcut
		ShortcutListener shortcut = new ShortcutListener("Enter",
				ShortcutAction.KeyCode.ENTER, null) {
			@Override
			public void handleAction(Object sender, Object target) {
				SubmitQuery();
			}
		};

		this.tfSearch.addShortcutListener(shortcut);
	}

	public void SubmitQuery() {
		try {
			SolrDocumentList doclist = null;
			doclist = SolrConnection.getDefault().query(tfSearch.getValue());

			System.out.println("Number of Search results: " + doclist.size());
			for (int i = 0; i < doclist.size(); i++) {
				this.vertLayout.addComponent(new Label(
						doclist.get(i).getFieldNames().toString())
				);
			}
			
		} catch (SolrServerException | IOException e) {
			Notification notification = new Notification("Solr Server Error!");
	        notification.setDelayMsec(8000);
	        notification.setDescription("It looks the Solr server is not running. Please consult your IT Administrator");
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
