package HiveSys;

import javax.servlet.annotation.WebServlet;

import HiveSys.core.SolrConnection;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 *	
 */
@Theme("mytheme")
@Widgetset("HiveSys.MyAppWidgetset")
public class MyUI extends UI {
	
	static int i = 0;
	SearchForm searchUI = new SearchForm();
	private SolrConnection solr;
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	
    	System.out.println("Initialized " + i + "times");
    	solr = SolrConnection.getDefault();
    	solr.connect("http://localhost:8983/solr/test/");
    	
    	final SearchForm searchUI = new SearchForm();
    	setContent(searchUI);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}