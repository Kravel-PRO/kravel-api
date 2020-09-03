package com.kravel.server.model.member;

import com.kravel.server.model.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity @Getter
@NoArgsConstructor
public class RememberMe extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "remember_me_id")
    private long id;
    private String loginEmail;
    private String token;

    public void updateToken(String loginEmail, String token) {
        this.loginEmail = loginEmail;
        this.token = token;
    }

    @Builder
    public RememberMe(String loginEmail, String token) {
        this.loginEmail = loginEmail;
        this.token = token;
    }
}
