package com.kravel.server.api.article.controller;

import com.kravel.server.api.article.Model.Celebrity;
import com.kravel.server.api.article.service.CelebrityService;
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
@RequestMapping("/api/articles/celebrities")
public class CelebrityController {

    @Autowired
    private CelebrityService celebrityService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    private ResponseMessage findAllCelebrities(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                               @RequestParam(value = "max", defaultValue = "10") int max,
                                               @RequestParam(value = "order", defaultValue = "DESC") String order,
                                               @RequestParam(value = "search", defaultValue = "") String search,
                                               Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("offset", offset);
        param.put("max", max);
        param.put("order", order);
        param.put("search", search);

        List<Celebrity> celebrityList = celebrityService.findAllCelebrities(param);
        System.out.println(celebrityList.toString());

        return new ResponseMessage(HttpStatus.OK, celebrityList);
    }

}
