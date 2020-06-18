package com.kravel.server.api.article.controller;

import com.kravel.server.api.article.dto.celebrity.CelebrityDTO;
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
    public ResponseMessage findAllCelebrities(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                              @RequestParam(value = "max", defaultValue = "10") int max,
                                              @RequestParam(value = "order", defaultValue = "DESC") String order,
                                              @RequestParam(value = "search", defaultValue = "") String search,
                                              Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("offset", offset);
        param.put("max", max);
        param.put("order", order);
        param.put("search", search);

        List<CelebrityDTO> celebrityList = celebrityService.findAllCelebrities(param);

        return new ResponseMessage(HttpStatus.OK, celebrityList);
    }

    @GetMapping("/{celebrityId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findCelebrityById(@PathVariable("celebrityId") long celebrityId,
                                             @RequestParam(value = "offset", defaultValue = "0") int offset,
                                             @RequestParam(value = "max", defaultValue = "10") int max,
                                             @RequestParam(value = "sort", defaultValue = "CREATE_DE") String sort,
                                             @RequestParam(value = "order", defaultValue = "DESC") String order,
                                             Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("celebrityId", celebrityId);
        param.put("offset", offset);
        param.put("max", max);
        param.put("sort", sort);
        param.put("order", order);

        Map<String, Object> result = celebrityService.findCelebrityById(param);
        return new ResponseMessage(HttpStatus.OK, result);
    }

    @GetMapping("/{celebrityId}/reviews")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findAllCelebrityReview(@PathVariable("celebrityId") long celebrityId,
                                                  @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                  @RequestParam(value = "max", defaultValue = "0") int max,
                                                  @RequestParam(value = "sort", defaultValue = "CREATE_DE") String sort,
                                                  @RequestParam(value = "order", defaultValue = "DESC") String order,
                                                  Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("offset", offset);
        param.put("max", max);
        param.put("sort", sort);
        param.put("order", order);

        return new ResponseMessage(HttpStatus.OK, null);
    }

}
