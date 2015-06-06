/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hivesys.dashboard;

import com.hivesys.config.Config;
import com.hivesys.core.db.DBConnectionPool;
import com.hivesys.core.es.ElasticSearchContext;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
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

        ElasticSearchContext es = ElasticSearchContext.getInstance();
        try {
            es.initClient("localhost", 9300);
        } catch (Exception ex) {
            System.out.println("Cannot Connect to elastic search Node. Exiting!!!");
            System.exit(1);
        }
        Config.getInstance().loadConfig();
        // …
    }

    // Vaadin app un-deploying/shutting down.
    @Override
    public void contextDestroyed ( ServletContextEvent contextEvent )
    {
        ServletContext context = contextEvent.getServletContext();
        
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
            }

        }
    }

}