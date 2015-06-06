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

    public final StringPath bio = createString("bio");

    public final StringPath email = createString("email");

    public final StringPath firstName = createString("firstName");

    public final BooleanPath gender = createBoolean("gender");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DateTimePath<java.sql.Timestamp> lastLogin = createDateTime("lastLogin", java.sql.Timestamp.class);

    public final StringPath lastName = createString("lastName");

    public final StringPath location = createString("location");

    public final StringPath pword = createString("pword");

    public final StringPath role = createString("role");

    public final BooleanPath sensitiv = createBoolean("sensitiv");

    public final StringPath telephone = createString("telephone");

    public final StringPath title = createString("title");

    public final StringPath userName = createString("userName");

    public final StringPath website = createString("website");

    public final com.mysema.query.sql.PrimaryKey<QUser> userPK = createPrimaryKey(id);

    public final com.mysema.query.sql.ForeignKey<QDocument> _document1Fk = createInvForeignKey(id, "UserId");

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
        addMetadata(bio, ColumnMetadata.named("Bio").withIndex(15).ofType(Types.LONGVARCHAR).withSize(65535));
        addMetadata(email, ColumnMetadata.named("Email").withIndex(7).ofType(Types.VARCHAR).withSize(1024));
        addMetadata(firstName, ColumnMetadata.named("FirstName").withIndex(2).ofType(Types.CHAR).withSize(255));
        addMetadata(gender, ColumnMetadata.named("Gender").withIndex(11).ofType(Types.BIT).withSize(3));
        addMetadata(id, ColumnMetadata.named("ID").withIndex(1).ofType(Types.INTEGER).withSize(10).notNull());
        addMetadata(lastLogin, ColumnMetadata.named("LastLogin").withIndex(8).ofType(Types.TIMESTAMP).withSize(19));
        addMetadata(lastName, ColumnMetadata.named("LastName").withIndex(3).ofType(Types.VARCHAR).withSize(1024));
        addMetadata(location, ColumnMetadata.named("Location").withIndex(12).ofType(Types.CHAR).withSize(255));
        addMetadata(pword, ColumnMetadata.named("Pword").withIndex(4).ofType(Types.VARCHAR).withSize(100).notNull());
        addMetadata(role, ColumnMetadata.named("Role").withIndex(9).ofType(Types.CHAR).withSize(255));
        addMetadata(sensitiv, ColumnMetadata.named("Sensitiv").withIndex(6).ofType(Types.BIT).withSize(3));
        addMetadata(telephone, ColumnMetadata.named("Telephone").withIndex(13).ofType(Types.VARCHAR).withSize(1024));
        addMetadata(title, ColumnMetadata.named("Title").withIndex(10).ofType(Types.CHAR).withSize(4));
        addMetadata(userName, ColumnMetadata.named("UserName").withIndex(16).ofType(Types.VARCHAR).withSize(1024));
        addMetadata(website, ColumnMetadata.named("Website").withIndex(14).ofType(Types.VARCHAR).withSize(1024));
    }

}

