package com.kravel.server.model.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity @Getter
@NoArgsConstructor
public class RememberMe {

    @Id @GeneratedValue
    @Column(name = "remember_me_id")
    private long id;

    private String series;
    private String loginEmail;
    private String token;
    private LocalDateTime lastUsed;

    @Builder
    public RememberMe(long id, String series, String loginEmail, String token, LocalDateTime lastUsed) {
        this.id = id;
        this.series = series;
        this.loginEmail = loginEmail;
        this.token = token;
        this.lastUsed = lastUsed;
    }
}
