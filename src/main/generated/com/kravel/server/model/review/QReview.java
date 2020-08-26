package com.kravel.server.model.review;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = -485559364L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final com.kravel.server.model.QBaseTimeEntity _super = new com.kravel.server.model.QBaseTimeEntity(this);

    public final com.kravel.server.model.celebrity.QCelebrity celebrity;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Float> grade = createNumber("grade", Float.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final com.kravel.server.model.media.QMedia media;

    public final com.kravel.server.model.member.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final com.kravel.server.model.place.QPlace place;

    public final ListPath<com.kravel.server.model.mapping.ReviewLike, com.kravel.server.model.mapping.QReviewLike> reviewLikes = this.<com.kravel.server.model.mapping.ReviewLike, com.kravel.server.model.mapping.QReviewLike>createList("reviewLikes", com.kravel.server.model.mapping.ReviewLike.class, com.kravel.server.model.mapping.QReviewLike.class, PathInits.DIRECT2);

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.celebrity = inits.isInitialized("celebrity") ? new com.kravel.server.model.celebrity.QCelebrity(forProperty("celebrity")) : null;
        this.media = inits.isInitialized("media") ? new com.kravel.server.model.media.QMedia(forProperty("media")) : null;
        this.member = inits.isInitialized("member") ? new com.kravel.server.model.member.QMember(forProperty("member"), inits.get("member")) : null;
        this.place = inits.isInitialized("place") ? new com.kravel.server.model.place.QPlace(forProperty("place"), inits.get("place")) : null;
    }

}

