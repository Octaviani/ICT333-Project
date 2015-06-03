package com.hivesys.database.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;

import com.mysema.query.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QFileInfo is a Querydsl query type for QFileInfo
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QFileInfo extends com.mysema.query.sql.RelationalPathBase<QFileInfo> {

    private static final long serialVersionUID = 1150622886;

    public static final QFileInfo FileInfo = new QFileInfo("FileInfo");

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<QFileInfo> fileInfoPK = createPrimaryKey(id);

    public final com.mysema.query.sql.ForeignKey<QVersionInfo> _versionInfoIbfk1 = createInvForeignKey(id, "FileInfoId");

    public QFileInfo(String variable) {
        super(QFileInfo.class, forVariable(variable), "Hive", "FileInfo");
        addMetadata();
    }

    public QFileInfo(String variable, String schema, String table) {
        super(QFileInfo.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QFileInfo(Path<? extends QFileInfo> path) {
        super(path.getType(), path.getMetadata(), "Hive", "FileInfo");
        addMetadata();
    }

    public QFileInfo(PathMetadata<?> metadata) {
        super(QFileInfo.class, metadata, "Hive", "FileInfo");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(fileName, ColumnMetadata.named("FileName").withIndex(2).ofType(Types.VARCHAR).withSize(767).notNull());
        addMetadata(id, ColumnMetadata.named("ID").withIndex(1).ofType(Types.INTEGER).withSize(10).notNull());
    }

}

