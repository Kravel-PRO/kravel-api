package com.kravel.server.model.mapping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCelebrityMedia is a Querydsl query type for CelebrityMedia
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCelebrityMedia extends EntityPathBase<CelebrityMedia> {

    private static final long serialVersionUID = -1631461421L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCelebrityMedia celebrityMedia = new QCelebrityMedia("celebrityMedia");

    public final com.kravel.server.model.celebrity.QCelebrity celebrity;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.kravel.server.model.media.QMedia media;

    public QCelebrityMedia(String variable) {
        this(CelebrityMedia.class, forVariable(variable), INITS);
    }

    public QCelebrityMedia(Path<? extends CelebrityMedia> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCelebrityMedia(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCelebrityMedia(PathMetadata metadata, PathInits inits) {
        this(CelebrityMedia.class, metadata, inits);
    }

    public QCelebrityMedia(Class<? extends CelebrityMedia> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.celebrity = inits.isInitialized("celebrity") ? new com.kravel.server.model.celebrity.QCelebrity(forProperty("celebrity")) : null;
        this.media = inits.isInitialized("media") ? new com.kravel.server.model.media.QMedia(forProperty("media")) : null;
    }

}

