package com.hivesys.core;

import com.mysema.query.sql.MySQLTemplates;
import com.mysema.query.sql.SQLTemplates;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.mariadb.jdbc.MySQLDataSource;

public class DatabaseSource {

    private static final DatabaseSource singleton = new DatabaseSource();
    private SQLTemplates mConfig = null;

    private String url;
    private String user;
    private String password;

    DatabaseSource() {
        try {
            ///* Does not seem to have the need for this
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseSource.class.getName()).log(Level.SEVERE, null, ex);
        }

        // postgres by default without quotes converts to lowercase hence causing errors. Always use quotations
        mConfig = new MySQLTemplates(true);
    }

    public DataSource getDataSource() {
        MySQLDataSource mysqlDS = new MySQLDataSource();
        mysqlDS.setURL("jdbc:mariadb://" + this.getUrl());
        mysqlDS.setUser(this.getUser());
        mysqlDS.setPassword(this.getPassword());
        return mysqlDS;
    }

    public SQLTemplates getConfig() {
        return mConfig;
    }

    public Connection getConnection() throws SQLException {
        DataSource ds = getDataSource();
        return ds.getConnection();
    }

    public static DatabaseSource getInstance() {
        return singleton;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
