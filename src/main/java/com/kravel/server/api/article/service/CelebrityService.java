package com.kravel.server.api.article.service;

import com.kravel.server.api.article.dto.celebrity.CelebrityArticleDTO;
import com.kravel.server.api.article.dto.celebrity.CelebrityDetailDTO;
import com.kravel.server.api.article.dto.celebrity.ExCelebrityArticleDTO;
import com.kravel.server.api.article.dto.celebrity.CelebrityDTO;
import com.kravel.server.api.article.mapper.CelebrityMapper;
import com.kravel.server.api.article.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CelebrityService {

    private final ReviewMapper reviewMapper;
    private final CelebrityMapper celebrityMapper;

    public List<CelebrityDTO> findAllCelebrities(Map<String, Object> param) throws Exception {
        return celebrityMapper.findAllCelebrities(param);
    }

    public CelebrityDetailDTO findCelebrityById(Map<String, Object> param) throws Exception {

        CelebrityDetailDTO result = new CelebrityDetailDTO();

        CelebrityDTO celebrityDTO = celebrityMapper.findCelebrityById(param);
        result.setCelebrity(celebrityDTO);

        ExCelebrityArticleDTO exCelebrityArticleDTO = celebrityMapper.findAllArticleByCelebrity(param);
        CelebrityArticleDTO celebrityArticleDTO = new CelebrityArticleDTO(celebrityDTO, exCelebrityArticleDTO);

        result.setArticles(celebrityArticleDTO);

        return result;
    }
}
