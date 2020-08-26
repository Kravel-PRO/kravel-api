package com.kravel.server.api.service;

import com.kravel.server.api.dto.review.ReviewDTO;
import com.kravel.server.api.dto.review.ReviewDetailDTO;
import com.kravel.server.api.dto.review.ReviewOverviewDTO;
import com.kravel.server.api.mapper.CelebrityMapper;
import com.kravel.server.api.mapper.MediaMapper;
import com.kravel.server.api.mapper.ReviewMapper;
import com.kravel.server.api.model.Review;
import com.kravel.server.common.S3Uploader;
import com.kravel.server.common.util.exception.InvalidRequestException;
import com.kravel.server.common.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final S3Uploader s3Uploader;
    private final ReviewMapper reviewMapper;
    private final MediaMapper mediaMapper;
    private final CelebrityMapper celebrityMapper;

    public List<ReviewOverviewDTO> findAllReviews(Map<String, Object> param) throws Exception {

        List<ReviewOverviewDTO> reviewOverviewDTOs = reviewMapper.findAllReviews(param);
        if (reviewOverviewDTOs.isEmpty()) {
            throw new NotFoundException("🔥 error: is not exist review");
        }
        return reviewOverviewDTOs;
    }

    public ReviewDetailDTO findReviewDetailById(Map<String, Object> param) throws Exception {

        ReviewDetailDTO articleReviewDTO = reviewMapper.findReviewDetailById(param);
        if (articleReviewDTO.getImageUrl().isEmpty()) {
            throw new NotFoundException("🔥 error: is not exist review");
        }

        return articleReviewDTO;
    }

    public List<ReviewOverviewDTO> findAllReviewByCelebrity(Map<String, Object> param) throws Exception {

        List<ReviewOverviewDTO> reviewOverviewDTOs = reviewMapper.findAllReviews(param);
        if (reviewOverviewDTOs.isEmpty()) {
            throw new NotFoundException("🔥 error: is not exist reviews");
        }

        return reviewOverviewDTOs;
    }

    public List<String> saveReview(MultipartFile file, long placeId, long memberId, long mediaId) throws Exception {
        // TODO place랑 관련된 media id 찾아서 review에 넣어주기

        Review review = Review.builder()
                .s3Uploader(s3Uploader)
                .memberId(memberId)
                .mediaId(mediaId)
                .build();

        review.saveImage(file);

        if (review.getImageUrl().isEmpty()) {
            throw new InvalidRequestException("🔥 error: image upload is failed");
        }


    }

    public boolean saveReviewToDatabase(Map<String, Object> param) throws Exception {

        Review review = reviewMapper.findArticleReviewById(param);

        param.put("mediaId", review.getMediaId());
        param.put("celebrityId", review.getCelebrityId());

        reviewMapper.saveReview(param);
        return reviewMapper.saveReviewImg(param) != 0;
    }

    public boolean handleReviewLike(Map<String, Object> param) throws Exception {
        int savedLike = reviewMapper.checkExistReviewLike(param);

        if ((boolean) param.get("likeState") && savedLike == 0) {
            return reviewMapper.saveReviewLike(param) != 0;

        } else if (savedLike >= 1) {
            return reviewMapper.removeReviewLike(param) != 0;

        } else  {
            throw new InvalidRequestException("It is not valid likeState");
        }
    }

}
