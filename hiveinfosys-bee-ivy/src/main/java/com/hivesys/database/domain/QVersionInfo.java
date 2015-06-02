package com.hivesys.database.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;

import com.mysema.query.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QVersionInfo is a Querydsl query type for QVersionInfo
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QVersionInfo extends com.mysema.query.sql.RelationalPathBase<QVersionInfo> {

    private static final long serialVersionUID = 1212067786;

    public static final QVersionInfo VersionInfo = new QVersionInfo("VersionInfo");

    public final StringPath author = createString("author");

    public final StringPath crcHash = createString("crcHash");

    public final DateTimePath<java.sql.Timestamp> createdDate = createDateTime("createdDate", java.sql.Timestamp.class);

    public final NumberPath<Integer> fileInfoId = createNumber("fileInfoId", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath title = createString("title");

    public final DateTimePath<java.sql.Timestamp> uploadDate = createDateTime("uploadDate", java.sql.Timestamp.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<QVersionInfo> versionInfoPK = createPrimaryKey(id);

    public final com.mysema.query.sql.ForeignKey<QUser> userFileFK = createForeignKey(userId, "ID");

    public final com.mysema.query.sql.ForeignKey<QFileInfo> versionInfoIbfk1 = createForeignKey(fileInfoId, "ID");

    public final com.mysema.query.sql.ForeignKey<QViewHistory> _fileViewFk = createInvForeignKey(id, "FileId");

    public final com.mysema.query.sql.ForeignKey<QAnnotation> _fileAnnoFk = createInvForeignKey(id, "FileId");

    public QVersionInfo(String variable) {
        super(QVersionInfo.class, forVariable(variable), "null", "VersionInfo");
        addMetadata();
    }

    public QVersionInfo(String variable, String schema, String table) {
        super(QVersionInfo.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QVersionInfo(Path<? extends QVersionInfo> path) {
        super(path.getType(), path.getMetadata(), "null", "VersionInfo");
        addMetadata();
    }

    public QVersionInfo(PathMetadata<?> metadata) {
        super(QVersionInfo.class, metadata, "null", "VersionInfo");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(author, ColumnMetadata.named("Author").withIndex(5).ofType(Types.VARCHAR).withSize(1024));
        addMetadata(crcHash, ColumnMetadata.named("CrcHash").withIndex(8).ofType(Types.VARCHAR).withSize(767));
        addMetadata(createdDate, ColumnMetadata.named("CreatedDate").withIndex(7).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(fileInfoId, ColumnMetadata.named("FileInfoId").withIndex(2).ofType(Types.INTEGER).withSize(10));
        addMetadata(id, ColumnMetadata.named("ID").withIndex(1).ofType(Types.INTEGER).withSize(10).notNull());
        addMetadata(title, ColumnMetadata.named("Title").withIndex(4).ofType(Types.VARCHAR).withSize(1024));
        addMetadata(uploadDate, ColumnMetadata.named("UploadDate").withIndex(6).ofType(Types.TIMESTAMP).withSize(19).notNull());
        addMetadata(userId, ColumnMetadata.named("UserId").withIndex(3).ofType(Types.INTEGER).withSize(10).notNull());
    }

}

