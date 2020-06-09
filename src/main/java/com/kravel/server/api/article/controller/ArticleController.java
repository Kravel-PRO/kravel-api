package com.kravel.server.api.article.controller;

import com.kravel.server.api.article.dto.ArticleDetailDTO;
import com.kravel.server.api.article.dto.ArticleMapDTO;
import com.kravel.server.api.article.dto.ArticleScrapDTO;
import com.kravel.server.api.article.service.ArticleService;
import com.kravel.server.auth.security.util.jwt.ClaimExtractor;
import com.kravel.server.common.util.message.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ClaimExtractor claimExtractor;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findAllPlaces(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                         @RequestParam(value = "max", defaultValue = "5") int max,
                                         @RequestParam(value = "sort", defaultValue = "CREATE_DE") String sort,
                                         @RequestParam(value = "order", defaultValue = "desc") String order,
                                         @RequestParam(value = "latitude", required = true) double latitude,
                                         @RequestParam(value = "longitude", required = true) double longitude,
                                         @RequestParam(value = "height", defaultValue = "0.25") double height,
                                         @RequestParam(value = "width", defaultValue = "0.25") double width,
                                         Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<String, Object>();

        param.put("offset", offset);
        param.put("max", max);
        param.put("sort", sort);
        param.put("order", order);
        param.put("latitude", latitude);
        param.put("longitude", longitude);
        param.put("height", height);
        param.put("width", width);
        param.put("langu", claimExtractor.getLangu(authentication));

        List<ArticleMapDTO> articleMapDTOList = articleService.findAllPlaces(param);
        return new ResponseMessage(HttpStatus.OK, articleMapDTOList);
    }

    @GetMapping("/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findPlaceById(@PathVariable("articleId") long articleId,
                                         Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<String, Object>();

        param.put("articleId", articleId);
        param.put("langu", claimExtractor.getLangu(authentication));

        ArticleDetailDTO articleDetailDTO = articleService.findPlaceById(param);
        return new ResponseMessage(HttpStatus.OK, articleDetailDTO);
    }

    @PostMapping("/{articleId}/scrap")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage handleScrape(@PathVariable("articleId") long articleId,
                                        @RequestBody ArticleScrapDTO articleScrapDTO,
                                        Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<String, Object>();

        param.put("scrapState", articleScrapDTO.isScrapState());
        param.put("articleId", articleId);
        param.put("memberId", claimExtractor.getMemberId(authentication));

        boolean result = articleService.handleArticleScrap(param);

        return new ResponseMessage(HttpStatus.OK, result);
    }
}