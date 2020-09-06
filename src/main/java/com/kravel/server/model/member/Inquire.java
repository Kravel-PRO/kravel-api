package com.kravel.server.model.member;

import com.kravel.server.common.S3Uploader;
import com.kravel.server.dto.update.InquireUploadDTO;
import com.kravel.server.model.BaseTimeEntity;
import com.kravel.server.enums.InquireCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor
public class Inquire extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquire_id")
    private long id;

    private String title;
    private String tags;
    private String address;

    @Lob
    private String contents;

    @Enumerated(EnumType.STRING)
    private InquireCategory inquireCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "inquire", cascade = CascadeType.ALL)
    private List<InquireImage> inquireImages = new ArrayList<>();

    @Builder
    public Inquire(String title, String tags, String address, String contents, InquireCategory inquireCategory, Member member, List<InquireImage> inquireImages) {
        this.title = title;
        this.tags = tags;
        this.address = address;
        this.contents = contents;
        this.inquireCategory = inquireCategory;
        this.member = member;
        this.inquireImages = inquireImages;
    }

    public Inquire(InquireUploadDTO inquireUploadDTO, Member member) {
        this.title = inquireUploadDTO.getTitle();
        this.contents = inquireUploadDTO.getContents();
        this.tags = inquireUploadDTO.getTags();
        this.address = inquireUploadDTO.getAddress();
        this.inquireCategory = inquireUploadDTO.getInquireCategory();
        this.member = member;
    }

    public void saveImages(S3Uploader s3Uploader, List<MultipartFile> files) {
        files.forEach(file -> {
            try {
                InquireImage inquireImage = new InquireImage();
                inquireImage.changeImageUrl(s3Uploader.upload(file, "inquire"));
                inquireImages.add(inquireImage);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
