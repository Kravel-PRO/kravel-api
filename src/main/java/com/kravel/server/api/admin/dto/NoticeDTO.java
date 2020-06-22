package com.kravel.server.api.admin.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("noticeDTO")
public class NoticeDTO {
    private long noticeId;
    private String subject;
    private String imgUrl;
    private String createDe;
}
