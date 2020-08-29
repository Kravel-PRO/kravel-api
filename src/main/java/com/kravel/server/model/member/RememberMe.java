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
import java.util.Date;

@Entity @Getter
@NoArgsConstructor
public class RememberMe {

    @Id
    private String series;
    private String loginEmail;
    private String token;
    private Date lastUsed;

    public void updateToken(String token, Date lastUsed) {
        this.token = token;
        this.lastUsed = lastUsed;
    }

    @Builder
    public RememberMe(String series, String loginEmail, String token, Date lastUsed) {
        this.series = series;
        this.loginEmail = loginEmail;
        this.token = token;
        this.lastUsed = lastUsed;
    }
}
