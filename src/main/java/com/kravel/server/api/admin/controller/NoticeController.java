package com.kravel.server.api.admin.controller;

import com.kravel.server.api.article.dto.review.ArticleReviewListDTO;
import com.kravel.server.common.util.message.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin/notice")
public class NoticeController {

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findAllNotice(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                         @RequestParam(value = "max", defaultValue = "6") int max,
                                         @RequestParam(value = "category", defaultValue = "NOTICE") String category,
                                         Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("offset", offset);
        param.put("max", max);
        param.put("category", category);

        return new ResponseMessage(HttpStatus.OK, null);
    }

}
