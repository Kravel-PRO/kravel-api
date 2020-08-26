package com.kravel.server.model.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1082205564L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final com.kravel.server.model.QBaseTimeEntity _super = new com.kravel.server.model.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath gender = createString("gender");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath langu = createString("langu");

    public final StringPath loginEmail = createString("loginEmail");

    public final StringPath loginPw = createString("loginPw");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath nickName = createString("nickName");

    public final com.kravel.server.model.review.QReview review;

    public final ListPath<com.kravel.server.model.mapping.ReviewLike, com.kravel.server.model.mapping.QReviewLike> reviewLikes = this.<com.kravel.server.model.mapping.ReviewLike, com.kravel.server.model.mapping.QReviewLike>createList("reviewLikes", com.kravel.server.model.mapping.ReviewLike.class, com.kravel.server.model.mapping.QReviewLike.class, PathInits.DIRECT2);

    public final EnumPath<com.kravel.server.auth.model.RoleType> roleType = createEnum("roleType", com.kravel.server.auth.model.RoleType.class);

    public final ListPath<com.kravel.server.model.mapping.Scrap, com.kravel.server.model.mapping.QScrap> scraps = this.<com.kravel.server.model.mapping.Scrap, com.kravel.server.model.mapping.QScrap>createList("scraps", com.kravel.server.model.mapping.Scrap.class, com.kravel.server.model.mapping.QScrap.class, PathInits.DIRECT2);

    public final StringPath useAt = createString("useAt");

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.review = inits.isInitialized("review") ? new com.kravel.server.model.review.QReview(forProperty("review"), inits.get("review")) : null;
    }

}

