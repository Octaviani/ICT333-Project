package HiveSys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;

import HiveSys.core.SolrConnection;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

/**
 *	
 */
@Theme("mytheme")
@Widgetset("HiveSys.MyAppWidgetset")


public class MyUI extends UI {
	SearchForm searchview = new SearchForm();
	FileUpload fileuploadview = new FileUpload();
	MainView mainview = new MainView();
	
	Navigator nav = new Navigator(this, this);
	
	Connection dbconnection;
	
	Window main = new Window("My application");
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	initDatabaseConnection();
    	initSolrConnection();
    	initNavigator();
    	
    	main.setContent(searchview);
    	main.center();
    	addWindow(main);
    	VerticalLayout content = new VerticalLayout();
    	setContent(content);


    	// Add the topmost component.
    	content.addComponent(new Label("The Ultimate Cat Finder"));


    	// Add a horizontal layout for the bottom part.
    	HorizontalLayout bottom = new HorizontalLayout();
    	content.addComponent(bottom);


    	bottom.addComponent(new Tree("Major Planets and Their Moons"));
    	bottom.addComponent(new Panel());
    	
    }
    
    public void initNavigator() {
    	nav.addView(SearchForm.NAME, searchview);
    	nav.addView(FileUpload.NAME, fileuploadview);
    	nav.addView(MainView.NAME, mainview);
    }
    
    public void initSolrConnection(){
    	SolrConnection solr = SolrConnection.getDefault();
    	solr.connect("http://localhost:8983/solr/test/");
    }
    
    public void initDatabaseConnection() {
    	try {
			Class.forName("org.mariadb.jdbc.Driver");
			dbconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "password");
			Statement stmt;
			stmt = dbconnection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users;");
			while (rs.next())
		      {
		        String username = rs.getString("username");
		        String password = rs.getString("password");		         
		        // print the results
		        System.out.println("Username: " + username +"\nPassword: "+ password + "\n\n\n\n\n\n");
		      }
			stmt.close();
			
			dbconnection.close();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
    }
    

    @WebServlet(value = {"/myui/*", "/VAADIN/*"},asyncSupported = true)
    //@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    
    public static class MyUIServlet extends VaadinServlet {
    }
}