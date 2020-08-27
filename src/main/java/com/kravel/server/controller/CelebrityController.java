package com.kravel.server.controller;

import com.kravel.server.dto.celebrity.CelebrityDTO;
import com.kravel.server.dto.celebrity.CelebrityDetailDTO;
import com.kravel.server.dto.review.ReviewOverviewDTO;
import com.kravel.server.service.CelebrityService;
import com.kravel.server.service.ReviewService;
import com.kravel.server.common.util.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CelebrityController {

    private final CelebrityService celebrityService;
    private final ReviewService reviewService;

    @GetMapping("/api/celebrities")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findAllCelebrities(@RequestParam(value = "search", defaultValue = "") String search,
                                              @PageableDefault Pageable pageable,
                                              Authentication authentication) throws Exception {

        List<CelebrityDTO> celebrityList = celebrityService.findAllCelebrities(search, pageable);

        return new ResponseMessage(HttpStatus.OK, celebrityList);
    }
    // 여기
    @GetMapping("/api/celebrities/{celebrityId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findCelebrityById(@PathVariable("celebrityId") long celebrityId,
                                             @RequestParam(value = "offset", defaultValue = "0") int offset,
                                             @RequestParam(value = "size", defaultValue = "10") int size,
                                             @RequestParam(value = "sort", defaultValue = "createdDate") String sort,
                                             @RequestParam(value = "order", defaultValue = "DESC") String order,
                                             Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("celebrityId", celebrityId);
        param.put("offset", offset);
        param.put("size", size);
        param.put("sort", sort);
        param.put("order", order);

        CelebrityDetailDTO result = celebrityService.findCelebrityById(param);
        return new ResponseMessage(HttpStatus.OK, result);
    }

    @GetMapping("/api/celebrities/{celebrityId}/reviews")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findAllReviewByCelebrity(@PathVariable("celebrityId") long celebrityId,
                                                  @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                  @RequestParam(value = "size", defaultValue = "6") int size,
                                                  @RequestParam(value = "sort", defaultValue = "createdDate") String sort,
                                                  @RequestParam(value = "order", defaultValue = "DESC") String order,
                                                  Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("offset", offset);
        param.put("size", size);
        param.put("sort", sort);
        param.put("order", order);
        param.put("celebrityId", celebrityId);

        List<ReviewOverviewDTO> result = reviewService.findAllReviewByCelebrity(param);
        return new ResponseMessage(HttpStatus.OK, result);
    }

}
