package com.kravel.server.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter
@ToString
public class Review extends BaseTimeEntity{

    private long reviewId;

    private long memberId;
    private long placeId;
    private long mediaId;
    private long celebrityId;
    private double grade;
    private String imageUrl;

    private String useAt = "Y";

}
