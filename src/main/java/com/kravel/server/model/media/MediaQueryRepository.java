package com.kravel.server.model.media;

import com.kravel.server.enums.Speech;
import com.kravel.server.model.place.QPlace;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MediaQueryRepository {

    private final JPAQueryFactory queryFactory;

    QMedia media = QMedia.media;
    QMediaInfo mediaInfo = QMediaInfo.mediaInfo;
    QPlace place = QPlace.place;

    public Optional<Media> findById(long mediaId, Speech speech) {
        Media media = queryFactory.selectFrom(this.media)
                .innerJoin(this.media.mediaInfos, mediaInfo)
                .on(mediaInfo.speech.eq(speech))
                .where(this.media.id.eq(mediaId)).fetchOne();
        return Optional.ofNullable(media);
    }

}
