package com.kravel.server.api.article.service;

import com.kravel.server.api.article.dto.media.ExMediaArticleDTO;
import com.kravel.server.api.article.dto.media.MediaArticleDTO;
import com.kravel.server.api.article.dto.media.MediaInfoDTO;
import com.kravel.server.api.article.dto.media.MediaListDTO;
import com.kravel.server.api.article.mapper.MediaMapper;
import com.kravel.server.common.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MediaService {

    private final MediaMapper mediaMapper;

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

    public List<MediaArticleDTO> findMediaArticlesById(Map<String, Object> param) throws Exception {

        List<ExMediaArticleDTO> exMediaArticleDTOs = mediaMapper.findMediaArticlesById(param);
        if (exMediaArticleDTOs.isEmpty()) {
            throw new NotFoundException("IS not exist media articles");
        }

        List<MediaArticleDTO> mediaArticleDTOs = new ArrayList<>();
        for (ExMediaArticleDTO exMediaArticleDTO : exMediaArticleDTOs) {
            mediaArticleDTOs.add(new MediaArticleDTO(exMediaArticleDTO));
        }

        return mediaArticleDTOs;
    }
}
