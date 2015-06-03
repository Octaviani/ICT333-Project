package com.hivesys.core;

import com.hivesys.dashboard.domain.FileInfo;
import com.hivesys.dashboard.view.repository.UploadView;
import com.google.common.io.Files;
import com.hivesys.database.domain.QFileInfo;
import com.hivesys.database.domain.QVersionInfo;
import com.hivesys.exception.ContentAlreadyExistException;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.CRC32;
import org.apache.tika.exception.TikaException;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class ContentStore {

    private static final ContentStore singleton = new ContentStore();

    BodyContentHandler textHandler = new BodyContentHandler();
    ParseContext context = new ParseContext();
    AutoDetectParser _autoParser = new AutoDetectParser();

    private String contentdir = "";

    public ContentStore() {
    }

    public FileInfo parseFileInfoFromFile(String tmpfile, FileInfo fInfo) {
        Metadata md = new Metadata();

        FileInputStream inFile = null;
        try {
            inFile = new FileInputStream(tmpfile);
            _autoParser.parse(inFile, textHandler, md, context);
            inFile.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ContentStore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | SAXException | TikaException ex) {
            Logger.getLogger(ContentStore.class.getName()).log(Level.SEVERE, null, ex);
        }

        //_dumpMetadata(tmpfile, md);
        fInfo.setAuthor(md.get("Author"));
        fInfo.setTitle(md.get("title"));

        SimpleDateFormat dateFormatUTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            fInfo.setDateCreated(dateFormatUTC.parse(md.get("Last-Modified")));
            fInfo.setDateUploaded(new Date());
        } catch (Exception ex) {
            Logger.getLogger(UploadView.class.getName()).log(Level.SEVERE, null, ex);
        }

        fInfo.setCrcHash(getCrcHash(tmpfile));

        return fInfo;
    }

    private void storeFileInfoToDatabase(FileInfo fInfo) {
        try {
            Connection conn = DatabaseSource.getInstance().getConnection();
            SQLTemplates config = DatabaseSource.getInstance().getConfig();

            QFileInfo fileInfo = QFileInfo.FileInfo;
            QVersionInfo versionInfo = QVersionInfo.VersionInfo;

            // insert into file info
            new SQLInsertClause(conn, config, fileInfo)
                    .set(fileInfo.fileName, fInfo.getRootFileName())
                    .execute();

            // always close the connection (performance optimization)
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ContentStore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int storeFileToRepository(String tmpFilepath, FileInfo fInfo) throws ContentAlreadyExistException {

        // crc hash to remove duplicate content
        String newFilename = "[[" + fInfo.getCrcHash() + "]]" + fInfo.getRootFileName();
        fInfo.setFullFileName(newFilename);

        File contentFile = new File(getContentdir() + File.separator + newFilename);
        System.out.println(contentFile);
        // already exists
        if (contentFile.exists()) {
            throw new ContentAlreadyExistException("File Already Exists!");
        }

        try {
            // copy from the temporary directory to the content folder
            Files.copy(new File(tmpFilepath), contentFile);
        } catch (IOException ex) {
            Logger.getLogger(ContentStore.class.getName()).log(Level.SEVERE, null, ex);
        }

        // rewrite the fileinfo
        fInfo.setContentFilepath(contentFile.getAbsolutePath());

        // send files to solr and database
        SolrConnection.getInstance().indexFile(fInfo);
        storeFileInfoToDatabase(fInfo);

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

    public static ContentStore getInstance() {
        return singleton;
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

    public static String getCrcHash(String filename) {

        String crcHash = "";
        try {
            FileInputStream inFile = new FileInputStream(new File(filename));
            CRC32 crcMaker = new CRC32();
            byte[] buffer = new byte[2 * 1024 * 1024]; // stream 2 MB and compute the hash instead of loading full file
            int bytesRead;
            while ((bytesRead = inFile.read(buffer)) != -1) {
                crcMaker.update(buffer, 0, bytesRead);
            }
            crcHash = Long.toHexString(crcMaker.getValue());

        } catch (FileNotFoundException ex) {
            return "";
        } catch (IOException ex) {
            return "";
        }

        return crcHash;
    }
}
