package com.kravel.server.model.member;

import com.kravel.bella.model.BaseTimeEntity;
import com.kravel.bella.model.member.enums.NoticeCategory;
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
