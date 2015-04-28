package HiveSys;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 *	
 */
@Theme("mytheme")
@Widgetset("HiveSys.MyAppWidgetset")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        
        setContent(layout);

        Button button = new Button("Click Me");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                layout.addComponent(new Label("Thank you for clicking"));
                String urlString = "http://localhost:8983/solr/test/";
                SolrClient solr = new HttpSolrClient(urlString);
                SolrQuery parameters = new SolrQuery();
                parameters.set("q", "swoorup");
                QueryResponse response ;
                try {
        			response = solr.query(parameters);
        			SolrDocumentList list = response.getResults();
        			for (int i=0; i < list.size(); i++)
        				System.out.println(list.get(i).getFieldNames());
        		} catch (SolrServerException | IOException e) {
        			// TODO Auto-generated catch block
        			//e.printStackTrace();
        		}
            }
        });
        layout.addComponent(button);        
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}