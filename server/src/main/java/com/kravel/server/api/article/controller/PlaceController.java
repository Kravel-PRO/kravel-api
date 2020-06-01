package com.kravel.server.api.article.controller;

import com.kravel.server.api.article.Model.Article;
import com.kravel.server.api.article.service.PlaceService;
import com.kravel.server.auth.model.Member;
import com.kravel.server.auth.security.token.PostAuthorizationToken;
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
@RequestMapping("/api/article")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @Autowired
    private ClaimExtractor claimExtractor;

    @GetMapping("/places")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findAllPlaces(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                         @RequestParam(value = "max", defaultValue = "5") int max,
                                         @RequestParam(value = "sort", defaultValue = "createDe") String sort,
                                         @RequestParam(value = "order", defaultValue = "desc") String order,
                                         @RequestParam(value = "search", defaultValue = "") String search,
                                         Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<String, Object>();

        param.put("offset", offset);
        param.put("max", max);
        param.put("sort", sort);
        param.put("order", order);
        param.put("search", search);
        param.put("language", claimExtractor.getLanguage(authentication));

        List<Article> articleList = placeService.findAllPlaces(param);
        return new ResponseMessage(HttpStatus.OK, articleList);
    }

    @GetMapping("/place/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage findPlaceByArticleId(@PathVariable("articeId") String articleId,
                                                Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<String, Object>();

        param.put("articleId", articleId);
        param.put("language", claimExtractor.getLanguage(authentication));

        return null;
    }
}
