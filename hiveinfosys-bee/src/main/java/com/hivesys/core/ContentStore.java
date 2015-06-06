package com.hivesys.core;

import com.box.view.BoxViewException;
import com.hivesys.core.es.ElasticSearchContext;
import com.google.common.io.Files;
import com.hivesys.core.db.DocumentDB;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tika.exception.TikaException;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class ContentStore {

    private static final ContentStore singleton = new ContentStore();

    BodyContentHandler textHandler = new BodyContentHandler(999999999);
    ParseContext context = new ParseContext();
    AutoDetectParser _autoParser = new AutoDetectParser();

    private String contentdir = "";

    public ContentStore() {
    }

    public Document parseFileInfoFromFile(String tmpfile, Document doc) {
        try {
            Metadata md = new Metadata();

            FileInputStream inFile = new FileInputStream(tmpfile);
            _autoParser.parse(inFile, textHandler, md, context);
            inFile.close();

            _dumpMetadata(tmpfile, md);
            String author = "";
            try {
                author = md.get("Author").trim();
            } catch (NullPointerException e) {

            }

            String title = "";

            try {
                title = md.get("title").trim();
            } catch (NullPointerException e) {

            }

            if (author.equalsIgnoreCase("null")) {
                author = "";
            }

            if (title.equalsIgnoreCase("null")) {
                title = "";
            }

            doc.setAuthor(author);
            doc.setTitle(title);
            doc.setDescription("");

            SimpleDateFormat dateFormatUTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
            
            Date dateCreated = new Date();
            
            try {
                dateFormatUTC.parse(md.get("Last-Modified"));
            } catch (NullPointerException e)
            {
                
            }

            doc.setDateCreated(dateCreated);
            doc.setDateUploaded(new Date());

            doc.setHash(Utilities.getSHAHash(tmpfile));

        } catch (IOException | SAXException | TikaException | ParseException ex) {
            Logger.getLogger(ContentStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doc;
    }


    public int storeFileToRepository(Document doc) throws SQLException, IOException, BoxViewException {
        String tmpFilepath = doc.getContentFilepath();

        // crc hash to remove duplicate content
        String newFilename = "" + doc.getHash() + "" + doc.getRootFileName();

        File contentFile = new File(getContentdir() + File.separator + newFilename);
        // already exists
        if (contentFile.exists()) {
            System.out.println(contentFile.getAbsoluteFile() + " Already Exist. Overwriting the file");
        }

        try {
            // move from the temporary directory to the content folder
            Files.move(new File(tmpFilepath), contentFile);
        } catch (IOException ex) {
            Logger.getLogger(ContentStore.class.getName()).log(Level.SEVERE, null, ex);
        }

        // rewrite the fileinfo
        doc.setContentFilepath(contentFile.getAbsolutePath());

        // send files to solr and database
        ElasticSearchContext.getInstance().indexFile(doc);
        DocumentDB.getInstance().storeDocumentToDatabase(doc);
        BoxViewDocuments.getInstance().storeFileInfo(doc);
        return 0;
    }

    private static void _dumpMetadata(String filename, Metadata md) {
        log("Dumping metadata for file: " + filename);
        for (String name : md.names()) {
            log(name + ":" + md.get(name));
        }
        log("nn");
    }

    private static void log(String msg) {
        System.out.println(msg);
    }

    /**
     * @return the contentdir
     */
    public String getContentdir() {
        return contentdir;
    }

    /**
     * @param contentdir the contentdir to set
     */
    public void setContentdir(String contentdir) {
        this.contentdir = contentdir;
        File fContentFolder = new File(this.contentdir);

        // if the directory does not exist, create it
        if (!fContentFolder.exists()) {

            boolean result = false;

            try {
                fContentFolder.mkdir();
                result = true;
            } catch (SecurityException se) {
                //handle it
            }
            if (result) {
                System.out.println("Created directory: " + fContentFolder);
            }
        }
    }

    public static ContentStore getInstance() {
        return singleton;
    }
}
