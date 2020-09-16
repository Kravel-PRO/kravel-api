package com.kravel.server.dto.update.media;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@ToString
public class MediaUpdateDTO {

    private MultipartFile image;
    private LocalDate year;
    private String korInfo;
    private String engInfo;
}
