package com.kravel.server.model.place;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaceInfo is a Querydsl query type for PlaceInfo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPlaceInfo extends EntityPathBase<PlaceInfo> {

    private static final long serialVersionUID = -1236592772L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaceInfo placeInfo = new QPlaceInfo("placeInfo");

    public final com.kravel.server.model.QBaseEntity _super = new com.kravel.server.model.QBaseEntity(this);

    public final StringPath contents = createString("contents");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath langu = createString("langu");

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    public final QPlace place;

    public final StringPath subject = createString("subject");

    public QPlaceInfo(String variable) {
        this(PlaceInfo.class, forVariable(variable), INITS);
    }

    public QPlaceInfo(Path<? extends PlaceInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaceInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaceInfo(PathMetadata metadata, PathInits inits) {
        this(PlaceInfo.class, metadata, inits);
    }

    public QPlaceInfo(Class<? extends PlaceInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.place = inits.isInitialized("place") ? new QPlace(forProperty("place"), inits.get("place")) : null;
    }

}

