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

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "rememberMe")
    private Member member;

    private String token;

    public void updateToken(String token) {
        this.token = token;
    }

    public static RememberMe generateToken(String token) {
        return RememberMe.builder().token(token).build();
    }
    @Builder
    public RememberMe(Member member, String token) {
        this.member = member;
        this.token = token;
    }
}
