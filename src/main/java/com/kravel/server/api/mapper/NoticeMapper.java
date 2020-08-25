package com.kravel.server.api.mapper;

import com.kravel.server.api.dto.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface NoticeMapper {

    public List<NoticeDTO> findAllNotices(@Param("param") Map<String, Object> param);
}
