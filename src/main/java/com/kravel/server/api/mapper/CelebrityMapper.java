package com.kravel.server.api.mapper;

import com.kravel.server.api.dto.celebrity.CelebrityDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface CelebrityMapper {

    public List<CelebrityDTO> findAllCelebrities(@Param("param") Map<String, Object> param);

    public CelebrityDTO findCelebrityById(@Param("param") Map<String, Object> param);


}
