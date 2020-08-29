package com.kravel.server.auth.security.token;

import com.kravel.server.common.util.exception.NotFoundException;
import com.kravel.server.model.member.RememberMe;
import com.kravel.server.model.member.RememberMeQueryRepository;
import com.kravel.server.model.member.RememberMeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.util.Date;

@RequiredArgsConstructor
public class RememberMemTokenRepository implements PersistentTokenRepository {

    private final RememberMeRepository rememberMeRepository;
    private final RememberMeQueryRepository rememberMeQueryRepository;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        RememberMe rememberMe = RememberMe.builder()
                .loginEmail(token.getUsername())
                .series(token.getSeries())
                .token(token.getTokenValue())
                .lastUsed(token.getDate())
                .build();
        rememberMeRepository.save(rememberMe);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        RememberMe rememberMe = rememberMeRepository.findById(series).orElseThrow(() -> new NotFoundException("🔥 error: is not exist remember me"));
        rememberMe.updateToken(tokenValue, lastUsed);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        RememberMe rememberMe = rememberMeRepository.findById(seriesId).orElseThrow(() -> new NotFoundException("🔥 error: is not exist remember me"));
        return new PersistentRememberMeToken(rememberMe.getLoginEmail(), rememberMe.getSeries(), rememberMe.getToken(), rememberMe.getLastUsed());
    }

    @Override
    public void removeUserTokens(String loginEmail) {
        RememberMe rememberMe = rememberMeQueryRepository.findByLoginEmail(loginEmail).orElseThrow(() -> new NotFoundException("🔥 error: is not exist remember me"));
        rememberMeRepository.delete(rememberMe);
    }
}
