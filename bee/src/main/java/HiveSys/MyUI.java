package HiveSys;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.annotation.WebServlet;

import HiveSys.core.SolrConnection;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;

/**
 *	
 */
@Theme("mytheme")
@Widgetset("HiveSys.MyAppWidgetset")
public class MyUI extends UI {
	
	static int i = 0;
	SearchForm searchUI = new SearchForm();
	FileUpload fileUploadUI = new FileUpload();
	CustomLayout custom;
	
	
	private SolrConnection solr;
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	
    	System.out.println("Initialized " + i + "times");
    	solr = SolrConnection.getDefault();
    	solr.connect("http://localhost:8983/solr/test/");
    	
    	final SearchForm searchUI = new SearchForm();
    	searchUI.addComponent(new Upload());
    	setContent(searchUI);
    	setContent(fileUploadUI);
			//custom = new CustomLayout(new FileInputStream(new File("/home/swoorup/KBLSys/bee/src/main/webapp/VAADIN/themes/layouts/Search.html")));
		custom = new CustomLayout("html_pages/index/home");
    	setContent(custom);
    	
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}