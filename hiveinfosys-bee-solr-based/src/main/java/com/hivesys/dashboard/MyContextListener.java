/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hivesys.dashboard;

import com.hivesys.config.Config;
import com.hivesys.core.DBConnectionPool;
import com.hivesys.core.ElasticSearchContext;
import com.hivesys.core.SolrConnection;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author swoorup
 * System wide initialization
 */
@WebListener ( "Context listener for doing something or other." )
public class MyContextListener implements ServletContextListener
{

    // Vaadin app deploying/launching.
    @Override
    public void contextInitialized ( ServletContextEvent contextEvent )
    {
        ServletContext context = contextEvent.getServletContext();
        System.out.println("Context Initialized!");
        
        DBConnectionPool dbconn = DBConnectionPool.getInstance();
        SolrConnection solr = SolrConnection.getInstance();
        solr.connect("http://localhost:8983/solr/hive-solr-schema/");
        
        ElasticSearchContext es = ElasticSearchContext.getInstance();
        es.initClient("localhost", 9300);
        
        Config.getInstance().loadConfig();
        // …
    }

    // Vaadin app un-deploying/shutting down.
    @Override
    public void contextDestroyed ( ServletContextEvent contextEvent )
    {
        ServletContext context = contextEvent.getServletContext();
        System.out.println("Destroyed");
    }

}