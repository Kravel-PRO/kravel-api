package com.kravel.server.dto.update.media;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@ToString
public class MediaUpdateDTO {

    private MultipartFile image;
    private String year;
    private String korInfo;
    private String engInfo;
}
