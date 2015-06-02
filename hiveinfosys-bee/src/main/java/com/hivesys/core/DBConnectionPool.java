package com.hivesys.core;

import com.mysema.query.sql.MySQLTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnectionPool {

    static int INITIAL_CONNECTIONS = 5;
    static int MAX_CONNECTIONS = 125;

    private static final DBConnectionPool singleton = new DBConnectionPool();
    SimpleJDBCConnectionPool mConnectionPool;
    private SQLTemplates mSQLTemplates = null;

    private String url;
    private String user;
    private String password;

    DBConnectionPool() {
        try {
            ///* Does not seem to have the need for this
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
        }

        // postgres by default without quotes converts to lowercase hence causing errors. Always use quotations
        mSQLTemplates = new MySQLTemplates(true);
        /*mSQLTemplates = MySQLTemplates.builder()
                .quote() // to quote names
                .newLineToSingleSpace() // to replace new lines with single space in the output
                .build();      // to get the customized SQLTemplates instance*/
    }

    public void initializeConnectionPool() throws SQLException {
        this.mConnectionPool = new SimpleJDBCConnectionPool(
                "org.mariadb.jdbc.Driver",
                "jdbc:mariadb://" + this.getUrl(), this.getUser(), this.getPassword(), INITIAL_CONNECTIONS, MAX_CONNECTIONS);
    }

    public JDBCConnectionPool getConnectionPool() {
        return this.mConnectionPool;
    }

    public SQLTemplates getSQLTemplates() {
        return mSQLTemplates;
    }

    public Connection reserveConnection() throws SQLException {
        Connection conn = this.mConnectionPool.reserveConnection();
        conn.setAutoCommit(true);
        
        return conn;
    }

    public void releaseConnection(Connection conn) {
        this.mConnectionPool.releaseConnection(conn);
    }

    public static DBConnectionPool getInstance() {
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
