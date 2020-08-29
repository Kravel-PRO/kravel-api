package com.kravel.server.model.celebrity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCelebrity is a Querydsl query type for Celebrity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCelebrity extends EntityPathBase<Celebrity> {

    private static final long serialVersionUID = 1744478694L;

    public static final QCelebrity celebrity = new QCelebrity("celebrity");

    public final com.kravel.server.model.QBaseEntity _super = new com.kravel.server.model.QBaseEntity(this);

    public final ListPath<com.kravel.server.model.mapping.CelebrityMedia, com.kravel.server.model.mapping.QCelebrityMedia> celebrityMedias = this.<com.kravel.server.model.mapping.CelebrityMedia, com.kravel.server.model.mapping.QCelebrityMedia>createList("celebrityMedias", com.kravel.server.model.mapping.CelebrityMedia.class, com.kravel.server.model.mapping.QCelebrityMedia.class, PathInits.DIRECT2);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    public final StringPath name = createString("name");

    public final ListPath<com.kravel.server.model.mapping.PlaceCelebrity, com.kravel.server.model.mapping.QPlaceCelebrity> placeCelebrities = this.<com.kravel.server.model.mapping.PlaceCelebrity, com.kravel.server.model.mapping.QPlaceCelebrity>createList("placeCelebrities", com.kravel.server.model.mapping.PlaceCelebrity.class, com.kravel.server.model.mapping.QPlaceCelebrity.class, PathInits.DIRECT2);

    public final ListPath<com.kravel.server.model.review.Review, com.kravel.server.model.review.QReview> reviews = this.<com.kravel.server.model.review.Review, com.kravel.server.model.review.QReview>createList("reviews", com.kravel.server.model.review.Review.class, com.kravel.server.model.review.QReview.class, PathInits.DIRECT2);

    public QCelebrity(String variable) {
        super(Celebrity.class, forVariable(variable));
    }

    public QCelebrity(Path<? extends Celebrity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCelebrity(PathMetadata metadata) {
        super(Celebrity.class, metadata);
    }

}

