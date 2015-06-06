/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hivesys.core;

import com.box.view.BoxViewClient;
import org.json.*;
import com.hivesys.dashboard.domain.User;
import com.hivesys.database.domain.QFileInfo;
import com.hivesys.database.domain.QVersionInfo;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author swoorup
 */
public class FileInfoController {

    private static final FileInfoController singleton = new FileInfoController();
    private String boxviewapi;
    private BoxViewClient boxView;

    public void initializeBoxViewClient(String apikey) {
        boxView = new BoxViewClient(apikey);
        this.boxviewapi = apikey;
        BoxViewSession.getInstance().setBoxClient(boxView);
    }

    private String inputStreamToString(InputStream is) {

        String line = "";
        StringBuilder total = new StringBuilder();

        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        try {
            // Read response until the end
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {

        }

        // Return full string
        return total.toString();
    }

    public String getViewURL(String boxViewID) {
        return BoxViewSession.getInstance().getViewURL(boxViewID);
    }

    public void UploadToBoxVIew(Document fileinfo) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        try {
            HttpPost httppost = new HttpPost("https://upload.view-api.box.com/1/documents");
//            HttpPost httppost = new HttpPost("http://localhost:3000");

            // 
            //httppost.addHeader("Content-type", "multipart/form-data");  
            HttpEntity request = MultipartEntityBuilder.create()
                    .addPart("file", new FileBody(new File(fileinfo.getContentFilepath())))
                    .build();

            httppost.setEntity(request);
            httppost.addHeader("Authorization", "Token mh1axd33atif9wpoojt2wojhxytqdfma");
            httppost.removeHeaders("Accept");
            httppost.addHeader("Accept", "*/*");
            httppost.removeHeaders("Content-Type");

            CloseableHttpResponse response = httpclient.execute(httppost);
            try {

                System.out.println(response.getStatusLine());

                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("Response content length: " + resEntity.getContentLength());

                    String content = inputStreamToString(resEntity.getContent()).trim();
                    System.out.println("Response content: " + content);
                    JSONObject obj = new JSONObject(content);
                    String docID = obj.getString("id");
                    System.out.println("Document ID:" + docID);
                    fileinfo.setBoxViewID(docID);
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }

        } catch (IOException ex) {

        }
    }

    public void storeFileInfo(Document fileinfo) throws SQLException {

        // upload to box view in a thread
        Runnable r = new Runnable() {

            public void run() {
                UploadToBoxVIew(fileinfo);
                try {
                    storeFileInfoToDatabase(fileinfo);
                } catch (SQLException ex) {
                    Notification.show("Error Uploading document" + fileinfo.getRootFileName() +" to database");
                }
                
                Notification.show("Finished Analyzing and Uploading Document " + fileinfo.getRootFileName() +"!");
            }
        };

        new Thread(r).start();
        
    }

    public void storeFileInfoToDatabase(Document fileinfo) throws SQLException {
        int fileid = getFileIDFromName(fileinfo.getRootFileName());

        if (fileid == -1) {
            createNewFileRecord(fileinfo);
        } else {

            // overwrite if exist
            int versionID = getVersionIDFromHash(fileinfo.getCrcHash());
            
            
            
            Connection conn = DBConnectionPool.getInstance().reserveConnection();
            SQLTemplates config = DBConnectionPool.getInstance().getSQLTemplates();

            QVersionInfo qversioninfo = QVersionInfo.VersionInfo;

            new SQLDeleteClause(conn, config, qversioninfo)
                    .where(qversioninfo.id.eq(versionID))
                    .execute();

            DBConnectionPool.getInstance().releaseConnection(conn);

            createNewVersionRecord(fileinfo, fileid);
        }

    }

    //public List<FileInfo> getFilesFromName
    public int getFileIDFromName(String filename) throws SQLException {
        Connection conn = DBConnectionPool.getInstance().reserveConnection();
        SQLTemplates config = DBConnectionPool.getInstance().getSQLTemplates();

        QFileInfo qfileinfo = QFileInfo.FileInfo;

        SQLQuery query = new SQLQuery(conn, config);
        List<Integer> result = query.from(qfileinfo)
                .where(qfileinfo.fileName.eq(filename))
                .list(qfileinfo.id);

        DBConnectionPool.getInstance().releaseConnection(conn);

        if (result.isEmpty()) {
            return -1;
        }

        return result.get(0);

    }

    public int getVersionIDFromCrcHash(int fileID, String crcHash) throws SQLException {
        Connection conn = DBConnectionPool.getInstance().reserveConnection();
        SQLTemplates config = DBConnectionPool.getInstance().getSQLTemplates();

        QVersionInfo qversioninfo = QVersionInfo.VersionInfo;

        SQLQuery query = new SQLQuery(conn, config);
        List<Integer> result = query.from(qversioninfo)
                .where(qversioninfo.crcHash.eq(crcHash).and(qversioninfo.fileInfoId.eq(fileID)))
                .list(qversioninfo.id);

        DBConnectionPool.getInstance().releaseConnection(conn);

        if (result.isEmpty()) {
            return -1;
        }

        return result.get(0);

    }

    public int getVersionIDFromHash(String hash) throws SQLException {
        Connection conn = DBConnectionPool.getInstance().reserveConnection();
        SQLTemplates config = DBConnectionPool.getInstance().getSQLTemplates();

        QVersionInfo qversioninfo = QVersionInfo.VersionInfo;

        SQLQuery query = new SQLQuery(conn, config);
        List<Integer> result = query.from(qversioninfo)
                .where(qversioninfo.crcHash.eq(hash))
                .list(qversioninfo.id);

        DBConnectionPool.getInstance().releaseConnection(conn);

        if (result.isEmpty()) {
            return -1;
        }

        return result.get(0);
    }

    public int getFileIDFromVersionID(int versionID) throws SQLException {
        Connection conn = DBConnectionPool.getInstance().reserveConnection();
        SQLTemplates config = DBConnectionPool.getInstance().getSQLTemplates();

        QVersionInfo qversioninfo = QVersionInfo.VersionInfo;

        SQLQuery query = new SQLQuery(conn, config);
        List<Integer> result = query.from(qversioninfo)
                .where(qversioninfo.id.eq(versionID))
                .list(qversioninfo.fileInfoId);

        DBConnectionPool.getInstance().releaseConnection(conn);

        if (result.isEmpty()) {
            return -1;
        }

        return result.get(0);
    }

    public String getFileNameFromHash(String hash) throws SQLException {
        int versionID = getVersionIDFromHash(hash);
        int fileID = getFileIDFromVersionID(versionID);

        Connection conn = DBConnectionPool.getInstance().reserveConnection();
        SQLTemplates config = DBConnectionPool.getInstance().getSQLTemplates();

        QFileInfo qfileinfo = QFileInfo.FileInfo;

        SQLQuery query = new SQLQuery(conn, config);
        List<String> result = query.from(qfileinfo)
                .where(qfileinfo.id.eq(fileID))
                .list(qfileinfo.fileName);

        DBConnectionPool.getInstance().releaseConnection(conn);

        if (result.isEmpty()) {
            return null;
        }

        return result.get(0);

    }

    public String getBoxViewIDFromHash(String hash) throws SQLException {
        int versionID = getVersionIDFromHash(hash);

        Connection conn = DBConnectionPool.getInstance().reserveConnection();
        SQLTemplates config = DBConnectionPool.getInstance().getSQLTemplates();

        QVersionInfo qversioninfo = QVersionInfo.VersionInfo;

        SQLQuery query = new SQLQuery(conn, config);
        List<String> result = query.from(qversioninfo)
                .where(qversioninfo.id.eq(versionID))
                .list(qversioninfo.boxViewID);

        DBConnectionPool.getInstance().releaseConnection(conn);

        if (result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }

    public int createNewFileRecord(Document fileinfo) throws SQLException {
        Connection conn = DBConnectionPool.getInstance().reserveConnection();
        SQLTemplates config = DBConnectionPool.getInstance().getSQLTemplates();

        QFileInfo qfileinfo = QFileInfo.FileInfo;

        // insert into file info
        new SQLInsertClause(conn, config, qfileinfo)
                .columns(qfileinfo.fileName)
                .values(fileinfo.getRootFileName())
                .execute();

        DBConnectionPool.getInstance().releaseConnection(conn);
        int fileId = getFileIDFromName(fileinfo.getRootFileName());

        if (fileId == -1) {
            throw new SQLException("File not created in FileInfo table");
        }

        int versionid = createNewVersionRecord(fileinfo, fileId);
        return fileId;
    }

    public int createNewVersionRecord(Document fileinfo, int fileid) throws SQLException {
        Connection conn = DBConnectionPool.getInstance().reserveConnection();
        SQLTemplates config = DBConnectionPool.getInstance().getSQLTemplates();

        QVersionInfo qversioninfo = QVersionInfo.VersionInfo;
        Timestamp stampUploaded = new Timestamp(fileinfo.getDateUploaded().getTime());
        Timestamp stampCreated = new Timestamp(fileinfo.getDateCreated().getTime());
        final User user = (User) VaadinSession.getCurrent().getAttribute(
                User.class.getName());

        new SQLInsertClause(conn, config, qversioninfo)
                .set(qversioninfo.uploadDate, stampUploaded)
                .set(qversioninfo.createdDate, stampCreated)
                .set(qversioninfo.crcHash, fileinfo.getCrcHash())
                .set(qversioninfo.description, fileinfo.getDescription())
                .set(qversioninfo.author, fileinfo.getAuthor())
                .set(qversioninfo.userId, user.getId())
                .set(qversioninfo.fileInfoId, fileid)
                .set(qversioninfo.boxViewID, fileinfo.getBoxViewID())
                .execute();

        int versionID = getVersionIDFromCrcHash(fileid, fileinfo.getCrcHash());
        DBConnectionPool.getInstance().releaseConnection(conn);

        return versionID;
    }

    public static FileInfoController getInstance() {
        return singleton;
    }
}
