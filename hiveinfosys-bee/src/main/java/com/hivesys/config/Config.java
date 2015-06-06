package com.hivesys.config;

import com.hivesys.core.ContentStore;
import com.hivesys.core.db.DBConnectionPool;
import com.hivesys.core.FileInfoController;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author swoorup
 */
public class Config {

    private static final Config singleton = new Config();
    public static String CONFIG_FILE = "hivesystemconfig.xml";
    public static String CONTENT_FOLDER = "hivesystemcontent";
    
    private String boxViewApiKey = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    

    public void loadConfig() {
        String xmlConfigFile = System.getProperty("user.home") + File.separator + CONFIG_FILE;
        File fConfigXML = new File(xmlConfigFile);

        if (!fConfigXML.exists()) {
            FileWriter fWriter;
            try {
                fWriter = new FileWriter(fConfigXML);

                String newXML
                        = "<?xml version=\"1.0\"?>\n"
                        + "<hivesystemconfig>\n"
                        + "\t<database>\n"
                        + "\t\t<host>localhost</host>\n"
                        + "\t\t<port>3306</port>\n"
                        + "\t\t<username></username>\n"
                        + "\t\t<password></password>\n"
                        + "\t\t<databasename>Hive</databasename>\n"
                        + "\t</database>\n"
                        + "\t<solr>\n"
                        + "\t\t<host>localhost</host>\n"
                        + "\t\t<port>8983</port>\n"
                        + "\t\t<core>hive-solr-schema</core>\n"
                        + "\t</solr>\n"
                        + "\t<contentstore>\n"
                        + "\t\t<path>" + System.getProperty("user.home") + File.separator + CONTENT_FOLDER + "</path>\n"
                        + "\t</contentstore>\n"
                        + "\t<boxview>\n"
                        + "\t\t<apikey>ABCDEFGHIJKLMNOPQRSTUVWXYZ</apikey>\n"
                        + "\t</boxview>\n"
                        + "</hivesystemconfig>\n";

                fWriter.write(newXML);
                fWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Wrote sample configuration file to: " + xmlConfigFile);
            System.out.println("Please set the correct setting and restart the server!!!");
            System.exit(1);
            return;
        }
        loadConfig(xmlConfigFile);
    }

    public void loadConfig(String xmlconfigfile) {
        File fConfigXML = new File(xmlconfigfile);
        if (!fConfigXML.exists()) {
            System.out.println("Configuration file not found!");
            System.exit(1);
        }

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fConfigXML);

            doc.getDocumentElement().normalize();

            String rootElement = doc.getDocumentElement().getNodeName();
            if (!rootElement.equalsIgnoreCase("hivesystemconfig")) {
                throw new ParserConfigurationException("Invalid XML File");
            }

            Element dbNode = (Element) doc.getElementsByTagName("database").item(0);
            Element solrNode = (Element) doc.getElementsByTagName("solr").item(0);
            Element contentstoreNode = (Element) doc.getElementsByTagName("contentstore").item(0);
            Element boxviewNode = (Element) doc.getElementsByTagName("boxview").item(0);

            String dbURL = dbNode.getElementsByTagName("host").item(0).getTextContent() + ":"
                    + dbNode.getElementsByTagName("port").item(0).getTextContent() + "/"
                    + dbNode.getElementsByTagName("databasename").item(0).getTextContent();

            String dbUser = dbNode.getElementsByTagName("username").item(0).getTextContent();
            String dbPassword = dbNode.getElementsByTagName("password").item(0).getTextContent();

            String solrConn = "http://" + solrNode.getElementsByTagName("host").item(0).getTextContent() + ":"
                    + solrNode.getElementsByTagName("port").item(0).getTextContent() + "/solr/"
                    + solrNode.getElementsByTagName("core").item(0).getTextContent();
            
            String contentStore = contentstoreNode.getElementsByTagName("path").item(0).getTextContent();
            
            String boxviewpikey = boxviewNode.getElementsByTagName("apikey").item(0).getTextContent();
            
            System.out.println(
                    "Connecting to database: " + dbURL + 
                    " with username: " + dbUser + " and password: " + dbPassword);
            
            System.out.println(
            "Connecting to solr: " + solrConn);
            
            System.out.println(
                    "Content is stored in: " + contentStore);
            
            System.out.println(
            "Box View API key: " + boxviewpikey);
            
            this.setBoxViewApiKey(boxviewpikey);
            
            DBConnectionPool.getInstance().setUrl(dbURL);
            DBConnectionPool.getInstance().setUser(dbUser);
            DBConnectionPool.getInstance().setPassword(dbPassword);
            DBConnectionPool.getInstance().initializeConnectionPool();
            
            ContentStore.getInstance().setContentdir(contentStore);
            FileInfoController.getInstance().initializeBoxViewClient(boxviewpikey);

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.out.println("Error reading configuration file: " + xmlconfigfile);
            System.exit(1);
        } catch (SQLException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static Config getInstance() {
        return singleton;
    }

    /**
     * @return the boxViewApiKey
     */
    public String getBoxViewApiKey() {
        return boxViewApiKey;
    }

    /**
     * @param boxViewApiKey the boxViewApiKey to set
     */
    public void setBoxViewApiKey(String boxViewApiKey) {
        this.boxViewApiKey = boxViewApiKey;
    }

}
