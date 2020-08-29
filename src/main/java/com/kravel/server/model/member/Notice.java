package com.kravel.server.model.member;

import com.kravel.server.model.BaseTimeEntity;
import com.kravel.server.enums.NoticeCategory;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Notice extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "notice_id")
    private long id;

    @Enumerated(EnumType.STRING)
    private NoticeCategory noticeCategory;

    private String subject;
    private String contents;
}