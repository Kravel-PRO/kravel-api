package com.kravel.server.dto.update;

import com.kravel.server.enums.InquireCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class InquireUploadDTO {

    private String title;
    private String contents;
    private String address;
    private String tags;
    private InquireCategory inquireCategory;
    private List<MultipartFile> files = new ArrayList<>();
}
