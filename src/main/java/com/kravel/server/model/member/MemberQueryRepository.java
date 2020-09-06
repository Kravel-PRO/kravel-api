package com.kravel.server.model.member;

import com.kravel.server.common.util.exception.NotFoundException;
import com.kravel.server.model.mapping.QScrap;
import com.kravel.server.model.place.Place;
import com.kravel.server.model.place.QPlace;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    QMember member = QMember.member;
    QScrap scrap = QScrap.scrap;
    QPlace place = QPlace.place;


    public Optional<Member> findMemberByLoginEmail(String loginEmail) throws NotFoundException {
        return Optional.ofNullable(queryFactory.selectFrom(member)
                .where(member.loginEmail.eq(loginEmail)).fetchOne());
    }

    public Page<Place> findAllScrapById(long memberId, Pageable pageable) {
        QueryResults<Place> placeQueryResults = queryFactory.selectFrom(place)
                .innerJoin(place.scraps, scrap)
                .innerJoin(scrap.member, member)
                .where(scrap.member.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(placeQueryResults.getResults(), pageable, placeQueryResults.getTotal());
    }
}
