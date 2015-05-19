package com.hivesys.database.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;

import com.mysema.query.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QFile is a Querydsl query type for QFile
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QFile extends com.mysema.query.sql.RelationalPathBase<QFile> {

    private static final long serialVersionUID = -417468456;

    public static final QFile File = new QFile("File");

    public final StringPath author = createString("author");

    public final DateTimePath<java.sql.Timestamp> createdDate = createDateTime("createdDate", java.sql.Timestamp.class);

    public final StringPath dataType = createString("dataType");

    public final StringPath fileName = createString("fileName");

    public final StringPath filePath = createString("filePath");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DateTimePath<java.sql.Timestamp> uploadDate = createDateTime("uploadDate", java.sql.Timestamp.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<QFile> filePK = createPrimaryKey(id);

    public QFile(String variable) {
        super(QFile.class, forVariable(variable), "null", "File");
        addMetadata();
    }

    public QFile(String variable, String schema, String table) {
        super(QFile.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QFile(Path<? extends QFile> path) {
        super(path.getType(), path.getMetadata(), "null", "File");
        addMetadata();
    }

    public QFile(PathMetadata<?> metadata) {
        super(QFile.class, metadata, "null", "File");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(author, ColumnMetadata.named("Author").withIndex(6).ofType(Types.VARCHAR).withSize(50));
        addMetadata(createdDate, ColumnMetadata.named("CreatedDate").withIndex(4).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(dataType, ColumnMetadata.named("DataType").withIndex(5).ofType(Types.VARCHAR).withSize(20));
        addMetadata(fileName, ColumnMetadata.named("FileName").withIndex(2).ofType(Types.VARCHAR).withSize(150).notNull());
        addMetadata(filePath, ColumnMetadata.named("FilePath").withIndex(7).ofType(Types.VARCHAR).withSize(500).notNull());
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.INTEGER).withSize(10).notNull());
        addMetadata(uploadDate, ColumnMetadata.named("UploadDate").withIndex(3).ofType(Types.TIMESTAMP).withSize(19).notNull());
        addMetadata(userId, ColumnMetadata.named("UserId").withIndex(8).ofType(Types.INTEGER).withSize(10).notNull());
    }

}

