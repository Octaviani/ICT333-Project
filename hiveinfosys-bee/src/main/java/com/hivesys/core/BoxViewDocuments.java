/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hivesys.core;

import com.box.view.BoxViewClient;
import com.box.view.BoxViewException;
import com.hivesys.config.Config;
import com.hivesys.core.db.DocumentDB;
import org.json.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
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
public class BoxViewDocuments {

    private static final BoxViewDocuments singleton = new BoxViewDocuments();
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

    public Document UploadToBoxView(Document doc) throws BoxViewException {
        try {
            CloseableHttpClient httpclient = HttpClientBuilder.create().build();
            HttpPost httppost = new HttpPost("https://upload.view-api.box.com/1/documents");

            HttpEntity request = MultipartEntityBuilder.create()
                    .addPart("file", new FileBody(new File(doc.getContentFilepath())))
                    .build();

            httppost.setEntity(request);
            httppost.addHeader("Authorization", "Token " + Config.getInstance().getBoxViewApiKey());
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

                    if (resEntity.getContentLength() == 0 || content == "") {
                        throw new BoxViewException("BoxViewException", "Wrong API Key?");
                    }

                    System.out.println("Response content: " + content);
                    JSONObject obj = new JSONObject(content);
                    String docID = obj.getString("id");
                    System.out.println("Document ID:" + docID);
                    doc.setBoxViewID(docID);
                    return doc;
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } catch (IOException ex) {
            throw new BoxViewException("BoxViewException", "Wrong API Key?");
        }
        return doc;
    }

    public void storeFileInfo(Document fileinfo) throws SQLException, BoxViewException {
        fileinfo = UploadToBoxView(fileinfo);
        int id = DocumentDB.getInstance().getDocumentIDFromHash(fileinfo.getHash());
        DocumentDB.getInstance().updateBoxID(id, fileinfo.getBoxViewID());
    }

    public static BoxViewDocuments getInstance() {
        return singleton;
    }
}
