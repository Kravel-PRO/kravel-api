package com.kravel.server.api.article.controller;

import com.kravel.server.common.util.message.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article")
public class ArticleMainController {

    @GetMapping("/places")
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage findAllPlaces(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                         @RequestParam(value = "max", defaultValue = "5") int max,
                                         @RequestParam(value = "sort", defaultValue = "createDe") String sort,
                                         @RequestParam(value = "order", defaultValue = "desc") String order,
                                         @RequestParam(value = "search", defaultValue = "") String search) throws Exception {
        return null;
    }
}
