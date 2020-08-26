package com.kravel.server.api.service;

import com.kravel.server.api.dto.NoticeDTO;
import com.kravel.server.api.mapper.NoticeMapper;
import com.kravel.server.common.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeMapper noticeMapper;

    public List<NoticeDTO> findAllNotices(Map<String, Object> param) throws Exception {

        List<NoticeDTO> noticeDTOs = noticeMapper.findAllNotices(param);
        if (noticeDTOs.isEmpty()) {
            throw new NotFoundException("🔥 error: is not exist notice!");
        }

        return noticeDTOs;
    }
}
