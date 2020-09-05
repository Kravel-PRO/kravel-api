package com.kravel.server.common;

import com.kravel.server.model.place.QPlace;
import com.kravel.server.model.review.QReview;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.data.domain.Pageable;

public class OrderUtil extends OrderSpecifier {


    public static final OrderUtil DEFAULT = new OrderUtil();

    private OrderUtil() {
        super(Order.ASC, NullExpression.DEFAULT, NullHandling.Default);
    }

    public OrderUtil(Order order, Expression target) {
        super(order, target);
    }

    public static OrderUtil byLatitude(double latitude) {
        QPlace place = QPlace.place;
        QReview review = QReview.review;

        return latitude != 0
                ? new OrderUtil(Order.ASC, place.latitude.abs().subtract(latitude))
                : new OrderUtil(Order.DESC, review.count());
    }

    public static OrderUtil byLongitude(double longitude) {
        QPlace place = QPlace.place;
        QReview review = QReview.review;

        return longitude != 0
                ? new OrderUtil(Order.ASC, place.latitude.abs().subtract(longitude))
                : new OrderUtil(Order.DESC, review.count());
    }

    private static OrderUtil sort(Pageable pageable, String qclass) {
        PathBuilder orderByExpression = new PathBuilder(Object.class, qclass);
        return new OrderUtil(pageable.getSort().stream().findFirst().get().isAscending()
                ? Order.ASC : Order.DESC,
                orderByExpression.get(pageable.getSort().stream()
                        .findFirst()
                        .get()
                        .getProperty())
        );
    }
}
