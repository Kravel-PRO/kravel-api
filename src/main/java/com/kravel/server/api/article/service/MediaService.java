package com.kravel.server.api.article.service;

import com.kravel.server.api.article.dto.media.MediaListDTO;
import com.kravel.server.api.article.mapper.MediaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MediaService {

    @Autowired
    private MediaMapper mediaMapper;

    public MediaListDTO findAllMedia(Map<String, Object> param) throws Exception {
        return mediaMapper.findAllMedia(param);
    }
}
