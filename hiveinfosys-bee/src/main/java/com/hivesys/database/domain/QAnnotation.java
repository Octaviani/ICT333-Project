package com.hivesys.database.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;

import com.mysema.query.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QAnnotation is a Querydsl query type for QAnnotation
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QAnnotation extends com.mysema.query.sql.RelationalPathBase<QAnnotation> {

    private static final long serialVersionUID = -1122520949;

    public static final QAnnotation Annotation = new QAnnotation("Annotation");

    public final DateTimePath<java.sql.Timestamp> createdDate = createDateTime("createdDate", java.sql.Timestamp.class);

    public final NumberPath<Integer> fileId = createNumber("fileId", Integer.class);

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<QAnnotation> annotationPK = createPrimaryKey(id);

    public QAnnotation(String variable) {
        super(QAnnotation.class, forVariable(variable), "null", "Annotation");
        addMetadata();
    }

    public QAnnotation(String variable, String schema, String table) {
        super(QAnnotation.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QAnnotation(Path<? extends QAnnotation> path) {
        super(path.getType(), path.getMetadata(), "null", "Annotation");
        addMetadata();
    }

    public QAnnotation(PathMetadata<?> metadata) {
        super(QAnnotation.class, metadata, "null", "Annotation");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(createdDate, ColumnMetadata.named("CreatedDate").withIndex(2).ofType(Types.TIMESTAMP).withSize(19).notNull());
        addMetadata(fileId, ColumnMetadata.named("FileId").withIndex(3).ofType(Types.INTEGER).withSize(10).notNull());
        addMetadata(fileName, ColumnMetadata.named("FileName").withIndex(5).ofType(Types.VARCHAR).withSize(100));
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.INTEGER).withSize(10).notNull());
        addMetadata(userId, ColumnMetadata.named("UserId").withIndex(4).ofType(Types.INTEGER).withSize(10).notNull());
    }

}

