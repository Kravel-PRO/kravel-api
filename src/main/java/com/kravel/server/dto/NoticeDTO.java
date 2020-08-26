package com.kravel.server.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Getter @Setter
@Alias("noticeDTO")
public class NoticeDTO {
    private long noticeId;
    private String title;
    private String imageUrl;
    private LocalDateTime createdDate;
}
