package com.kravel.server.api.admin.service;

import com.kravel.server.api.admin.dto.NoticeDTO;
import com.kravel.server.api.admin.mapper.NoticeMapper;
import com.kravel.server.common.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    public List<NoticeDTO> findAllNotices(Map<String, Object> param) throws Exception {

        List<NoticeDTO> noticeDTOs = noticeMapper.findAllNotices(param);
        if (noticeDTOs.isEmpty()) {
            throw new NotFoundException("Is not exist notice!");
        }

        return noticeDTOs;
    }
}
