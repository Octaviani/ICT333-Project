package com.hivesys.database.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;

import com.mysema.query.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QViewHistory is a Querydsl query type for QViewHistory
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QViewHistory extends com.mysema.query.sql.RelationalPathBase<QViewHistory> {

    private static final long serialVersionUID = 1440666707;

    public static final QViewHistory ViewHistory = new QViewHistory("ViewHistory");

    public final DateTimePath<java.sql.Timestamp> dateViewed = createDateTime("dateViewed", java.sql.Timestamp.class);

    public final NumberPath<Integer> fileId = createNumber("fileId", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<QViewHistory> viewHistoryPK = createPrimaryKey(id);

    public QViewHistory(String variable) {
        super(QViewHistory.class, forVariable(variable), "null", "ViewHistory");
        addMetadata();
    }

    public QViewHistory(String variable, String schema, String table) {
        super(QViewHistory.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QViewHistory(Path<? extends QViewHistory> path) {
        super(path.getType(), path.getMetadata(), "null", "ViewHistory");
        addMetadata();
    }

    public QViewHistory(PathMetadata<?> metadata) {
        super(QViewHistory.class, metadata, "null", "ViewHistory");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(dateViewed, ColumnMetadata.named("DateViewed").withIndex(2).ofType(Types.TIMESTAMP).withSize(19).notNull());
        addMetadata(fileId, ColumnMetadata.named("FileId").withIndex(4).ofType(Types.INTEGER).withSize(10));
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.INTEGER).withSize(10).notNull());
        addMetadata(userId, ColumnMetadata.named("UserId").withIndex(3).ofType(Types.INTEGER).withSize(10).notNull());
    }

}

