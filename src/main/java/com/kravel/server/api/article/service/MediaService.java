package com.kravel.server.api.article.service;

import com.kravel.server.api.article.dto.media.MediaInfoDTO;
import com.kravel.server.api.article.dto.media.MediaListDTO;
import com.kravel.server.api.article.mapper.MediaMapper;
import com.kravel.server.common.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MediaService {

    @Autowired
    private MediaMapper mediaMapper;

    public List<MediaListDTO> findAllMedia(Map<String, Object> param) throws Exception {
        return mediaMapper.findAllMedia(param);
    }

    public MediaInfoDTO findMediaInfoById(Map<String, Object> param) throws Exception {

        MediaInfoDTO mediaInfoDTOs = mediaMapper.findMediaInfoById(param);
        if (mediaInfoDTOs.getMediaName().length() < 1) {
            throw new NotFoundException("Is not exist media information");
        }

        return mediaInfoDTOs;
    }
}
