package com.kravel.server.api.article.controller;

import com.kravel.server.api.article.dto.media.MediaInfoDTO;
import com.kravel.server.api.article.dto.media.MediaListDTO;
import com.kravel.server.api.article.service.MediaService;
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
@RequestMapping("/api/articles/medias")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findAllMedia(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                        @RequestParam(value = "max", defaultValue = "5") int max,
                                        @RequestParam(value = "sort", defaultValue = "CREATE_DE") String sort,
                                        @RequestParam(value = "order", defaultValue = "DESC") String order,
                                        Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("offset", offset);
        param.put("max", max);
        param.put("sort", sort);
        param.put("order", order);

        List<MediaListDTO> mediaListDTOList = mediaService.findAllMedia(param);

        return new ResponseMessage(HttpStatus.OK, mediaListDTOList);
    }

    @GetMapping("/{mediaId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findMediaById(@PathVariable("mediaId") long mediaId,
                                         Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("mediaId", mediaId);

        MediaInfoDTO result = mediaService.findMediaInfoById(param);
        return new ResponseMessage(HttpStatus.OK, result);
    }
}