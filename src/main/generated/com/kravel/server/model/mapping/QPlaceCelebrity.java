package com.kravel.server.model.mapping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaceCelebrity is a Querydsl query type for PlaceCelebrity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPlaceCelebrity extends EntityPathBase<PlaceCelebrity> {

    private static final long serialVersionUID = 1523657902L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaceCelebrity placeCelebrity = new QPlaceCelebrity("placeCelebrity");

    public final com.kravel.server.model.QBaseEntity _super = new com.kravel.server.model.QBaseEntity(this);

    public final com.kravel.server.model.celebrity.QCelebrity celebrity;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    public final com.kravel.server.model.place.QPlace place;

    public QPlaceCelebrity(String variable) {
        this(PlaceCelebrity.class, forVariable(variable), INITS);
    }

    public QPlaceCelebrity(Path<? extends PlaceCelebrity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaceCelebrity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaceCelebrity(PathMetadata metadata, PathInits inits) {
        this(PlaceCelebrity.class, metadata, inits);
    }

    public QPlaceCelebrity(Class<? extends PlaceCelebrity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.celebrity = inits.isInitialized("celebrity") ? new com.kravel.server.model.celebrity.QCelebrity(forProperty("celebrity")) : null;
        this.place = inits.isInitialized("place") ? new com.kravel.server.model.place.QPlace(forProperty("place"), inits.get("place")) : null;
    }

}

