package com.kravel.server.model.place;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPhotoFilter is a Querydsl query type for PhotoFilter
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPhotoFilter extends EntityPathBase<PhotoFilter> {

    private static final long serialVersionUID = -1945396303L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPhotoFilter photoFilter = new QPhotoFilter("photoFilter");

    public final com.kravel.server.model.QBaseEntity _super = new com.kravel.server.model.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final StringPath guideLine = createString("guideLine");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    public final QPlace place;

    public QPhotoFilter(String variable) {
        this(PhotoFilter.class, forVariable(variable), INITS);
    }

    public QPhotoFilter(Path<? extends PhotoFilter> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPhotoFilter(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPhotoFilter(PathMetadata metadata, PathInits inits) {
        this(PhotoFilter.class, metadata, inits);
    }

    public QPhotoFilter(Class<? extends PhotoFilter> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.place = inits.isInitialized("place") ? new QPlace(forProperty("place"), inits.get("place")) : null;
    }

}

