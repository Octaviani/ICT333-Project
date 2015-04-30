package HiveSys;

import org.apache.solr.common.SolrDocumentList;

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;

import HiveSys.core.*;
import HiveSys.layout.SearchFormLayout;

public class SearchForm extends SearchFormLayout {
	 
	public SearchForm()
	{
		this.tfSearch.setImmediate(true);
		
		// handle enter key shortcut
		ShortcutListener shortcut = new ShortcutListener("Enter", ShortcutAction.KeyCode.ENTER, null) {
			@Override
			public void handleAction(Object sender, Object target){
				SubmitQuery();
			}
		};
		
		this.tfSearch.addShortcutListener(shortcut);
	}
	
	public void SubmitQuery()
	{
		Notification.show(this.tfSearch.getValue(), Notification.Type.HUMANIZED_MESSAGE);
		
		// query the solr
		SolrDocumentList doclist = SolrConnection.getDefault().query(tfSearch.getValue());
		
		System.out.println(doclist.size());
		for (int i=0; i<doclist.size();i++)
		{
			this.vertlayout.addComponent(new Label(doclist.get(i).getFieldValue("resourcename").toString()));
		}
	}
}
