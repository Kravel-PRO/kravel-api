package com.kravel.server.api.mapper;

import com.kravel.server.api.dto.celebrity.CelebrityDTO;
import com.kravel.server.api.model.Celebrity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface CelebrityMapper {

    public List<Celebrity> findAllCelebrities(@Param("param") Map<String, Object> param);

    public Celebrity findCelebrityById(@Param("param") Map<String, Object> param);


}
