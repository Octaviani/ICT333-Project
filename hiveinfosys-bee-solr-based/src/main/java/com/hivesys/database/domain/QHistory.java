package com.hivesys.database.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;

import com.mysema.query.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QHistory is a Querydsl query type for QHistory
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QHistory extends com.mysema.query.sql.RelationalPathBase<QHistory> {

    private static final long serialVersionUID = -1090421576;

    public static final QHistory History = new QHistory("History");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath keyword = createString("keyword");

    public final DateTimePath<java.sql.Timestamp> searchDate = createDateTime("searchDate", java.sql.Timestamp.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<QHistory> historyPK = createPrimaryKey(id);

    public final com.mysema.query.sql.ForeignKey<QUser> userFk = createForeignKey(userId, "ID");

    public QHistory(String variable) {
        super(QHistory.class, forVariable(variable), "null", "History");
        addMetadata();
    }

    public QHistory(String variable, String schema, String table) {
        super(QHistory.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QHistory(Path<? extends QHistory> path) {
        super(path.getType(), path.getMetadata(), "null", "History");
        addMetadata();
    }

    public QHistory(PathMetadata<?> metadata) {
        super(QHistory.class, metadata, "null", "History");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(id, ColumnMetadata.named("ID").withIndex(1).ofType(Types.INTEGER).withSize(10).notNull());
        addMetadata(keyword, ColumnMetadata.named("Keyword").withIndex(3).ofType(Types.LONGVARCHAR).withSize(65535));
        addMetadata(searchDate, ColumnMetadata.named("SearchDate").withIndex(2).ofType(Types.TIMESTAMP).withSize(19).notNull());
        addMetadata(userId, ColumnMetadata.named("UserId").withIndex(4).ofType(Types.INTEGER).withSize(10).notNull());
    }

}

