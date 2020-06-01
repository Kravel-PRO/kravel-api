package com.kravel.server.api.article.service;

import com.kravel.server.api.article.Model.Article;
import com.kravel.server.api.article.mapper.PlaceMapper;
import com.kravel.server.common.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PlaceService {

    @Autowired
    private PlaceMapper placeMapper;

    public List<Article> findAllPlaces(Map<String,Object> param) throws Exception {
        List<Article> articleList = placeMapper.findAllPlaces(param);
        if (articleList.isEmpty()) {
            throw new NotFoundException("IS not exist Article List");
        }

        return articleList;
    }

}
