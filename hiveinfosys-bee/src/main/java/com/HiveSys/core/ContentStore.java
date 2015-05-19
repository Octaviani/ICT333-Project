package com.HiveSys.core;

import com.HiveSys.dashboard.domain.FileInfo;
import com.HiveSys.dashboard.view.repository.UploadView;
import com.google.common.io.Files;
import com.HiveSys.exception.ContentAlreadyExistException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.CRC32;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;

public class ContentStore {

    private static final long serialVersionUID = 1L;

    private static final ContentStore singleton = new ContentStore();

    BodyContentHandler textHandler = new BodyContentHandler();
    ParseContext context = new ParseContext();
    AutoDetectParser _autoParser = new AutoDetectParser();

    String strContentFolder = System.getProperty("user.home") + File.separator + "HiveSysContent";

    public ContentStore() {
        File contentFolder = new File(strContentFolder);

        // if the directory does not exist, create it
        if (!contentFolder.exists()) {
            System.out.println("creating directory: " + contentFolder);
            boolean result = false;

            try {
                contentFolder.mkdir();
                result = true;
            } catch (SecurityException se) {
                //handle it
            }
            if (result) {
                System.out.println("DIR created");
            }
        }
    }

    public int storeFileToRepository(String tmpFilepath, String oldFilename, FileInfo fileInfo) throws Exception {
        
        FileInputStream inFile;
        try {
            inFile = new FileInputStream(tmpFilepath);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ContentStore.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        
        CRC32 crcMaker = new CRC32();
        byte[] buffer = new byte[2 * 1024 * 1024]; // stream 2 MB and compute the hash instead of loading full file
        int bytesRead;
        while ((bytesRead = inFile.read(buffer)) != -1) {
            crcMaker.update(buffer, 0, bytesRead);
        }

        String crcHash = Long.toHexString(crcMaker.getValue());
        // crc hash to remove duplicate content
        String newFilename = "<" + crcHash + ">" + oldFilename;

        File contentFile = new File(strContentFolder + File.separator + newFilename);
        System.out.println(contentFile);
        // already exists
        if (contentFile.exists())
            throw new ContentAlreadyExistException("File Already Exists!");
        
        // copy from the temporary directory to the content folder
        Files.copy(new File(tmpFilepath), contentFile);
        
        // rewrite the fileinfo
        fileInfo.setContentFilepath(contentFile.getAbsolutePath());
        
        // send files to solr and database
        SolrConnection.getInstance().indexFile(tmpFilepath, fileInfo);
        storeFileInfoToDatabase(fileInfo);
        
        return 0;
    }

    public FileInfo parseFileInfoFromFile(String filename, FileInfo fileInfo) {
        Metadata md = new Metadata();

        try {
            InputStream input = new FileInputStream(filename);
            _autoParser.parse(input, textHandler, md, context);
        } catch (Exception e) {
            // log(String.format("File %s failed", filename));
            e.printStackTrace();
        }
        _dumpMetadata(filename, md);

        fileInfo.setAuthor(md.get("Author"));
        fileInfo.setTitle(md.get("title"));
        fileInfo.setFiletype(md.get("Content-Type"));

        SimpleDateFormat dateFormatUTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            fileInfo.setDateModified(dateFormatUTC.parse(md.get("Last-Modified")));
            fileInfo.setDateCreated(dateFormatUTC.parse(md.get("Creation-Date")));
        } catch (Exception ex) {
            Logger.getLogger(UploadView.class.getName()).log(Level.SEVERE, null, ex);
        }

        return fileInfo;
    }

    private void storeFileInfoToDatabase(FileInfo fileInfo) 
    {
    
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
}
