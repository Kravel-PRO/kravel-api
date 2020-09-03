package com.kravel.server.model.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInquire is a Querydsl query type for Inquire
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QInquire extends EntityPathBase<Inquire> {

    private static final long serialVersionUID = 195513105L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInquire inquire = new QInquire("inquire");

    public final com.kravel.server.model.QBaseTimeEntity _super = new com.kravel.server.model.QBaseTimeEntity(this);

    public final StringPath contents = createString("contents");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.kravel.server.enums.InquireCategory> inquireCategory = createEnum("inquireCategory", com.kravel.server.enums.InquireCategory.class);

    public final ListPath<InquireImage, QInquireImage> inquireImages = this.<InquireImage, QInquireImage>createList("inquireImages", InquireImage.class, QInquireImage.class, PathInits.DIRECT2);

    public final QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath title = createString("title");

    public QInquire(String variable) {
        this(Inquire.class, forVariable(variable), INITS);
    }

    public QInquire(Path<? extends Inquire> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInquire(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInquire(PathMetadata metadata, PathInits inits) {
        this(Inquire.class, metadata, inits);
    }

    public QInquire(Class<? extends Inquire> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

