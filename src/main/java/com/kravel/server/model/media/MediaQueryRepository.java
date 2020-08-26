package com.kravel.server.model.media;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MediaQueryRepository {

    private final JPAQueryFactory queryFactory;

}
