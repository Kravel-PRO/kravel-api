package com.kravel.server.dto.update;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString
public class PlaceUpdateDTO {

    private String bus;
    private String subway;
    private double latitude;
    private double longitude;
    private String celebrities;
    private long media;

    private MultipartFile image;
    private MultipartFile subImage;
    private MultipartFile filterImage;

    private String korInfo;
    private String engInfo;


}
