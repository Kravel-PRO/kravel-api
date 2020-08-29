package com.kravel.server.model.place;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlace is a Querydsl query type for Place
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPlace extends EntityPathBase<Place> {

    private static final long serialVersionUID = -1015102034L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlace place = new QPlace("place");

    public final com.kravel.server.model.QBaseEntity _super = new com.kravel.server.model.QBaseEntity(this);

    public final StringPath bus = createString("bus");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Double> grade = createNumber("grade", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    public final StringPath latitude = createString("latitude");

    public final StringPath location = createString("location");

    public final StringPath longitude = createString("longitude");

    public final com.kravel.server.model.media.QMedia media;

    public final ListPath<com.kravel.server.model.mapping.PlaceCelebrity, com.kravel.server.model.mapping.QPlaceCelebrity> placeCelebrities = this.<com.kravel.server.model.mapping.PlaceCelebrity, com.kravel.server.model.mapping.QPlaceCelebrity>createList("placeCelebrities", com.kravel.server.model.mapping.PlaceCelebrity.class, com.kravel.server.model.mapping.QPlaceCelebrity.class, PathInits.DIRECT2);

    public final ListPath<PlaceInfo, QPlaceInfo> placeInfos = this.<PlaceInfo, QPlaceInfo>createList("placeInfos", PlaceInfo.class, QPlaceInfo.class, PathInits.DIRECT2);

    public final ListPath<com.kravel.server.model.review.Review, com.kravel.server.model.review.QReview> reviews = this.<com.kravel.server.model.review.Review, com.kravel.server.model.review.QReview>createList("reviews", com.kravel.server.model.review.Review.class, com.kravel.server.model.review.QReview.class, PathInits.DIRECT2);

    public final ListPath<com.kravel.server.model.mapping.Scrap, com.kravel.server.model.mapping.QScrap> scraps = this.<com.kravel.server.model.mapping.Scrap, com.kravel.server.model.mapping.QScrap>createList("scraps", com.kravel.server.model.mapping.Scrap.class, com.kravel.server.model.mapping.QScrap.class, PathInits.DIRECT2);

    public final StringPath subway = createString("subway");

    public final StringPath useAt = createString("useAt");

    public final NumberPath<Double> weight = createNumber("weight", Double.class);

    public QPlace(String variable) {
        this(Place.class, forVariable(variable), INITS);
    }

    public QPlace(Path<? extends Place> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlace(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlace(PathMetadata metadata, PathInits inits) {
        this(Place.class, metadata, inits);
    }

    public QPlace(Class<? extends Place> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.media = inits.isInitialized("media") ? new com.kravel.server.model.media.QMedia(forProperty("media")) : null;
    }

}

