package com.kravel.server.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter @Setter
@ToString
@Alias("place")
public class Place extends BaseEntity{
    private long placeId;
    private String location;

    private double latitude;
    private double longitude;
    private double grade;
    private double weight;

    private List<Review> reviews;
    private PlaceInfo placeInfo;

    private String useAt = "Y";
}
