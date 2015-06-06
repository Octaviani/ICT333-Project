package com.hivesys.database.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;

import com.mysema.query.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QDocument is a Querydsl query type for QDocument
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDocument extends com.mysema.query.sql.RelationalPathBase<QDocument> {

    private static final long serialVersionUID = -1546764937;

    public static final QDocument Document = new QDocument("Document");

    public final StringPath author = createString("author");

    public final StringPath boxViewID = createString("boxViewID");

    public final DateTimePath<java.sql.Timestamp> createdDate = createDateTime("createdDate", java.sql.Timestamp.class);

    public final StringPath description = createString("description");

    public final StringPath fileName = createString("fileName");

    public final StringPath filePath = createString("filePath");

    public final StringPath hash = createString("hash");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DateTimePath<java.sql.Timestamp> uploadDate = createDateTime("uploadDate", java.sql.Timestamp.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<QDocument> documentPK = createPrimaryKey(id);

    public final com.mysema.query.sql.ForeignKey<QUser> document1Fk = createForeignKey(userId, "ID");

    public QDocument(String variable) {
        super(QDocument.class, forVariable(variable), "null", "Document");
        addMetadata();
    }

    public QDocument(String variable, String schema, String table) {
        super(QDocument.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QDocument(Path<? extends QDocument> path) {
        super(path.getType(), path.getMetadata(), "null", "Document");
        addMetadata();
    }

    public QDocument(PathMetadata<?> metadata) {
        super(QDocument.class, metadata, "null", "Document");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(author, ColumnMetadata.named("Author").withIndex(6).ofType(Types.VARCHAR).withSize(1024));
        addMetadata(boxViewID, ColumnMetadata.named("BoxViewID").withIndex(9).ofType(Types.VARCHAR).withSize(1024));
        addMetadata(createdDate, ColumnMetadata.named("CreatedDate").withIndex(3).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(description, ColumnMetadata.named("Description").withIndex(5).ofType(Types.LONGVARCHAR).withSize(2147483647));
        addMetadata(fileName, ColumnMetadata.named("FileName").withIndex(8).ofType(Types.VARCHAR).withSize(1024));
        addMetadata(filePath, ColumnMetadata.named("FilePath").withIndex(10).ofType(Types.VARCHAR).withSize(2048));
        addMetadata(hash, ColumnMetadata.named("hash").withIndex(4).ofType(Types.VARCHAR).withSize(767));
        addMetadata(id, ColumnMetadata.named("ID").withIndex(1).ofType(Types.INTEGER).withSize(10).notNull());
        addMetadata(uploadDate, ColumnMetadata.named("UploadDate").withIndex(2).ofType(Types.TIMESTAMP).withSize(19).notNull());
        addMetadata(userId, ColumnMetadata.named("UserId").withIndex(7).ofType(Types.INTEGER).withSize(10).notNull());
    }

}

