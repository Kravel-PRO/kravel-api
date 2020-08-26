package com.kravel.server.api.model;

import com.kravel.server.common.S3Uploader;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter
@Builder
@RequiredArgsConstructor
public class Review extends BaseTimeEntity{

    private long reviewId;

    private long memberId;
    private long placeId;
    private long mediaId;
    private long celebrityId;
    private double grade;
    private String imageUrl;

    private String useAt = "Y";

    @Builder
    public Review(long reviewId, long memberId, long placeId, long mediaId, long celebrityId, double grade, String imageUrl, String useAt) {
        this.reviewId = reviewId;
        this.memberId = memberId;
        this.placeId = placeId;
        this.mediaId = mediaId;
        this.celebrityId = celebrityId;
        this.grade = grade;
        this.imageUrl = imageUrl;
        this.useAt = useAt;
    }

    public void saveImage(S3Uploader s3Uploader, MultipartFile file) throws Exception {
        this.imageUrl = s3Uploader.upload(file, "review");
    }
}
