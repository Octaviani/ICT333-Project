package com.hivesys.database.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;

import com.mysema.query.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QUser is a Querydsl query type for QUser
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QUser extends com.mysema.query.sql.RelationalPathBase<QUser> {

    private static final long serialVersionUID = -417012185;

    public static final QUser User = new QUser("User");

    public final BooleanPath admin = createBoolean("admin");

    public final StringPath email = createString("email");

    public final StringPath firstName = createString("firstName");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DateTimePath<java.sql.Timestamp> lastLogin = createDateTime("lastLogin", java.sql.Timestamp.class);

    public final StringPath lastName = createString("lastName");

    public final StringPath password = createString("password");

    public final BooleanPath sensitiv = createBoolean("sensitiv");

    public final StringPath userName = createString("userName");

    public final com.mysema.query.sql.PrimaryKey<QUser> userPK = createPrimaryKey(id);

    public QUser(String variable) {
        super(QUser.class, forVariable(variable), "null", "User");
        addMetadata();
    }

    public QUser(String variable, String schema, String table) {
        super(QUser.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QUser(Path<? extends QUser> path) {
        super(path.getType(), path.getMetadata(), "null", "User");
        addMetadata();
    }

    public QUser(PathMetadata<?> metadata) {
        super(QUser.class, metadata, "null", "User");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(admin, ColumnMetadata.named("Admin").withIndex(5).ofType(Types.BIT).withSize(3));
        addMetadata(email, ColumnMetadata.named("Email").withIndex(7).ofType(Types.VARCHAR).withSize(200));
        addMetadata(firstName, ColumnMetadata.named("FirstName").withIndex(2).ofType(Types.CHAR).withSize(50).notNull());
        addMetadata(id, ColumnMetadata.named("id").withIndex(1).ofType(Types.INTEGER).withSize(10).notNull());
        addMetadata(lastLogin, ColumnMetadata.named("LastLogin").withIndex(8).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastName, ColumnMetadata.named("LastName").withIndex(3).ofType(Types.CHAR).withSize(50).notNull());
        addMetadata(password, ColumnMetadata.named("Password").withIndex(4).ofType(Types.VARCHAR).withSize(100).notNull());
        addMetadata(sensitiv, ColumnMetadata.named("Sensitiv").withIndex(6).ofType(Types.BIT).withSize(3));
        addMetadata(userName, ColumnMetadata.named("UserName").withIndex(9).ofType(Types.VARCHAR).withSize(45).notNull());
    }

}

