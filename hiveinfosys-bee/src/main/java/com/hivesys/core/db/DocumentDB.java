/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hivesys.core.db;

import com.hivesys.core.Document;
import com.hivesys.dashboard.domain.User;
import com.hivesys.database.domain.QDocument;
import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.vaadin.server.VaadinSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author swoorup
 */
public class DocumentDB {

    private static final DocumentDB singleton = new DocumentDB();

    public void storeDocumentToDatabase(Document doc) throws SQLException {
        int fileid = getDocumentIDFromHash(doc.getHash());

        if (fileid == -1) {
            createNewDocumentRecord(doc);
        }

    }

    public Tuple getDocumentFromID(int id) throws SQLException {
        Connection conn = DBConnectionPool.getInstance().reserveConnection();
        SQLTemplates config = DBConnectionPool.getInstance().getSQLTemplates();

        QDocument qdoc = QDocument.Document;

        SQLQuery query = new SQLQuery(conn, config);
        List<Tuple> result = query.from(qdoc)
                .where(qdoc.id.eq(id))
                .list();

        DBConnectionPool.getInstance().releaseConnection(conn);

        if (result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }

    public Tuple getDocumentFromHash(String hash) throws SQLException {
        Connection conn = DBConnectionPool.getInstance().reserveConnection();
        SQLTemplates config = DBConnectionPool.getInstance().getSQLTemplates();

        QDocument qdoc = QDocument.Document;

        SQLQuery query = new SQLQuery(conn, config);
        List<Tuple> result = query.from(qdoc)
                .where(qdoc.hash.eq(hash))
                .list();

        DBConnectionPool.getInstance().releaseConnection(conn);

        if (result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }

    public void updateBoxID(int id, String boxid) throws SQLException {

        Connection conn = DBConnectionPool.getInstance().reserveConnection();
        SQLTemplates config = DBConnectionPool.getInstance().getSQLTemplates();

        new SQLUpdateClause(conn, config, QDocument.Document)
                .where(QDocument.Document.id.eq(id))
                .set(QDocument.Document.boxViewID, boxid)
                .execute();

        DBConnectionPool.getInstance().releaseConnection(conn);
    }

    //public List<Document> getDocumentsFromName
    public int getDocumentIDFromName(String filename) throws SQLException {
        Connection conn = DBConnectionPool.getInstance().reserveConnection();
        SQLTemplates config = DBConnectionPool.getInstance().getSQLTemplates();

        QDocument qdoc = QDocument.Document;

        SQLQuery query = new SQLQuery(conn, config);
        List<Integer> result = query.from(qdoc)
                .where(qdoc.fileName.eq(filename))
                .list(qdoc.id);

        DBConnectionPool.getInstance().releaseConnection(conn);

        if (result.isEmpty()) {
            return -1;
        }

        return result.get(0);

    }

    public int getDocumentIDFromHash(String Hash) throws SQLException {
        Connection conn = DBConnectionPool.getInstance().reserveConnection();
        SQLTemplates config = DBConnectionPool.getInstance().getSQLTemplates();

        QDocument qdoc = QDocument.Document;

        SQLQuery query = new SQLQuery(conn, config);
        List<Integer> result = query.from(qdoc)
                .where(qdoc.hash.eq(Hash))
                .list(qdoc.id);

        DBConnectionPool.getInstance().releaseConnection(conn);

        if (result.isEmpty()) {
            return -1;
        }

        return result.get(0);

    }

    public String getDocumentNameFromHash(String hash) throws SQLException {
        int fileID = getDocumentIDFromHash(hash);

        Connection conn = DBConnectionPool.getInstance().reserveConnection();
        SQLTemplates config = DBConnectionPool.getInstance().getSQLTemplates();

        QDocument qdoc = QDocument.Document;

        SQLQuery query = new SQLQuery(conn, config);
        List<String> result = query.from(qdoc)
                .where(qdoc.id.eq(fileID))
                .list(qdoc.fileName);

        DBConnectionPool.getInstance().releaseConnection(conn);

        if (result.isEmpty()) {
            return "";
        }

        return result.get(0);

    }

    public String getBoxViewIDFromID(int id) throws SQLException {

        Connection conn = DBConnectionPool.getInstance().reserveConnection();
        SQLTemplates config = DBConnectionPool.getInstance().getSQLTemplates();

        QDocument qversioninfo = QDocument.Document;

        SQLQuery query = new SQLQuery(conn, config);
        List<String> result = query.from(qversioninfo)
                .where(qversioninfo.id.eq(id))
                .list(qversioninfo.boxViewID);

        DBConnectionPool.getInstance().releaseConnection(conn);

        if (result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }
    
    public String getBoxViewIDFromHash(String hash) throws SQLException {
        int versionID = getDocumentIDFromHash(hash);

        Connection conn = DBConnectionPool.getInstance().reserveConnection();
        SQLTemplates config = DBConnectionPool.getInstance().getSQLTemplates();

        QDocument qversioninfo = QDocument.Document;

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

    public int createNewDocumentRecord(Document doc) throws SQLException {
        Connection conn = DBConnectionPool.getInstance().reserveConnection();
        SQLTemplates config = DBConnectionPool.getInstance().getSQLTemplates();

        QDocument qdocument = QDocument.Document;
        Timestamp stampUploaded = new Timestamp(doc.getDateUploaded().getTime());
        Timestamp stampCreated = new Timestamp(doc.getDateCreated().getTime());
        final User user = (User) VaadinSession.getCurrent().getAttribute(
                User.class.getName());

        new SQLInsertClause(conn, config, qdocument)
                .set(qdocument.uploadDate, stampUploaded)
                .set(qdocument.createdDate, stampCreated)
                .set(qdocument.hash, doc.getHash())
                .set(qdocument.description, doc.getDescription())
                .set(qdocument.author, doc.getAuthor())
                .set(qdocument.userId, user.getId())
                .set(qdocument.fileName, doc.getRootFileName())
                .set(qdocument.filePath, doc.getContentFilepath())
                .set(qdocument.boxViewID, doc.getBoxViewID())
                .execute();

        int docID = getDocumentIDFromHash(doc.getHash());
        DBConnectionPool.getInstance().releaseConnection(conn);

        return docID;
    }

    public static DocumentDB getInstance() {
        return singleton;
    }
}
