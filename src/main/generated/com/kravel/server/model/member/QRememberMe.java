package com.kravel.server.model.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRememberMe is a Querydsl query type for RememberMe
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRememberMe extends EntityPathBase<RememberMe> {

    private static final long serialVersionUID = -1722754329L;

    public static final QRememberMe rememberMe = new QRememberMe("rememberMe");

    public final DateTimePath<java.util.Date> lastUsed = createDateTime("lastUsed", java.util.Date.class);

    public final StringPath loginEmail = createString("loginEmail");

    public final StringPath series = createString("series");

    public final StringPath token = createString("token");

    public QRememberMe(String variable) {
        super(RememberMe.class, forVariable(variable));
    }

    public QRememberMe(Path<? extends RememberMe> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRememberMe(PathMetadata metadata) {
        super(RememberMe.class, metadata);
    }

}

