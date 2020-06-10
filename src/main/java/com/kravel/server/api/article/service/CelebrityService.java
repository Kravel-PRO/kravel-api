package com.kravel.server.api.article.service;

import com.kravel.server.api.article.Model.Celebrity;
import com.kravel.server.api.article.mapper.CelebrityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CelebrityService {

    @Autowired
    private CelebrityMapper celebrityMapper;

    public List<Celebrity> findAllCelebrities(Map<String, Object> param) throws Exception {
        List<Celebrity> celebrityList = celebrityMapper.findAllCelebrities(param);
        System.out.println(celebrityList.toString());
        return celebrityList;
    }
}
