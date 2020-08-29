package com.kravel.server.model.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInquireImage is a Querydsl query type for InquireImage
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QInquireImage extends EntityPathBase<InquireImage> {

    private static final long serialVersionUID = 1097445578L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInquireImage inquireImage = new QInquireImage("inquireImage");

    public final com.kravel.server.model.QBaseTimeEntity _super = new com.kravel.server.model.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final QInquire inquire;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public QInquireImage(String variable) {
        this(InquireImage.class, forVariable(variable), INITS);
    }

    public QInquireImage(Path<? extends InquireImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInquireImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInquireImage(PathMetadata metadata, PathInits inits) {
        this(InquireImage.class, metadata, inits);
    }

    public QInquireImage(Class<? extends InquireImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.inquire = inits.isInitialized("inquire") ? new QInquire(forProperty("inquire"), inits.get("inquire")) : null;
    }

}

