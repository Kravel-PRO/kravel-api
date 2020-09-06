package com.kravel.server.dto.update;

import com.kravel.server.enums.InquireCategory;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InquireUploadDTO {

    private String title;
    private String contents;
    private String address;
    private String tags;
    private InquireCategory inquireCategory;
}
