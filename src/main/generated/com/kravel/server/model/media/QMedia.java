package com.kravel.server.model.media;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMedia is a Querydsl query type for Media
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMedia extends EntityPathBase<Media> {

    private static final long serialVersionUID = -1357080152L;

    public static final QMedia media = new QMedia("media");

    public final com.kravel.server.model.QBaseEntity _super = new com.kravel.server.model.QBaseEntity(this);

    public final ListPath<com.kravel.server.model.mapping.CelebrityMedia, com.kravel.server.model.mapping.QCelebrityMedia> celebrityMedias = this.<com.kravel.server.model.mapping.CelebrityMedia, com.kravel.server.model.mapping.QCelebrityMedia>createList("celebrityMedias", com.kravel.server.model.mapping.CelebrityMedia.class, com.kravel.server.model.mapping.QCelebrityMedia.class, PathInits.DIRECT2);

    public final StringPath contents = createString("contents");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    public final StringPath name = createString("name");

    public final ListPath<com.kravel.server.model.review.Review, com.kravel.server.model.review.QReview> reviews = this.<com.kravel.server.model.review.Review, com.kravel.server.model.review.QReview>createList("reviews", com.kravel.server.model.review.Review.class, com.kravel.server.model.review.QReview.class, PathInits.DIRECT2);

    public final StringPath useAt = createString("useAt");

    public final DatePath<java.time.LocalDate> year = createDate("year", java.time.LocalDate.class);

    public QMedia(String variable) {
        super(Media.class, forVariable(variable));
    }

    public QMedia(Path<? extends Media> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMedia(PathMetadata metadata) {
        super(Media.class, metadata);
    }

}

