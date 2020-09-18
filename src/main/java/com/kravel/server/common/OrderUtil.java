package com.kravel.server.common;

import com.kravel.server.model.mapping.QReviewLike;
import com.kravel.server.model.place.QPlace;
import com.kravel.server.model.review.QReview;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.CaseBuilder;
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

    public static OrderUtil byLatitude(double latitude, Pageable pageable, String qclass) {
        QPlace place = QPlace.place;
        QReview review = QReview.review;

        boolean ascending = pageable.getSort().stream().findFirst().isPresent() && pageable.getSort().stream().findFirst().get().isAscending();
        String property = pageable.getSort().stream().findFirst().isPresent() ? pageable.getSort().stream().findFirst().get().getProperty() : "";

        if (latitude != 0) {
            return new OrderUtil(Order.ASC, place.latitude.abs().subtract(latitude));

        } else if (property.equals("review-count")) {
            return new OrderUtil(ascending
                    ? Order.ASC : Order.DESC,
                    review.count()
            );
        } else {
            return OrderUtil.sort(pageable, qclass);
        }
    }

    public static OrderUtil byLongitude(double longitude, Pageable pageable, String qclass) {
        QPlace place = QPlace.place;
        QReview review = QReview.review;

        boolean ascending = pageable.getSort().stream().findFirst().isPresent() && pageable.getSort().stream().findFirst().get().isAscending();
        String property = pageable.getSort().stream().findFirst().isPresent() ? pageable.getSort().stream().findFirst().get().getProperty() : "";

        if (property.isEmpty()) {
            return OrderUtil.DEFAULT;

        } else if (longitude != 0) {
            return new OrderUtil(Order.ASC, place.longitude.abs().subtract(longitude));

        } else if (property.equals("review-count")) {
            return new OrderUtil(ascending
                    ? Order.ASC : Order.DESC,
                    review.count()
            );

        } else {
            return OrderUtil.sort(pageable, qclass);
        }
    }

    public static OrderUtil byReviewLikes(Pageable pageable, String qclass) {
        QReviewLike reviewLike = QReviewLike.reviewLike;
        QReview review = QReview.review;

        boolean ascending = pageable.getSort().stream().findFirst().isPresent() && pageable.getSort().stream().findFirst().get().isAscending();
        String property = pageable.getSort().stream().findFirst().isPresent() ? pageable.getSort().stream().findFirst().get().getProperty() : "";

        if (property.isEmpty()) {
            return OrderUtil.DEFAULT;

        } else if (property.equals("reviewLikes-count")) {
            return new OrderUtil(ascending
                    ? Order.ASC : Order.DESC,
                    new CaseBuilder()
                        .when(review.reviewLikes.isNotEmpty())
                        .then(review.reviewLikes.size())
                        .otherwise(0)
            );
        } else {
            return OrderUtil.sort(pageable, qclass);
        }
    }

    public static OrderUtil sort(Pageable pageable, String qclass) {
        if (pageable.getSort().isEmpty()) {
            return OrderUtil.DEFAULT;
        } else {
            boolean ascending = pageable.getSort().stream().findFirst().get().isAscending();
            String property = pageable.getSort().stream().findFirst().get().getProperty();

            PathBuilder orderByExpression = new PathBuilder(Object.class, qclass);
            return new OrderUtil(ascending
                    ? Order.ASC : Order.DESC,
                    orderByExpression.get(property)
            );
        }
    }
}
