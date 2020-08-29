package com.kravel.server.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kravel.server.auth.security.filter.FilterSkipMatcher;
import com.kravel.server.auth.security.filter.FormLoginFilter;
import com.kravel.server.auth.security.filter.JwtAuthenticationFilter;
import com.kravel.server.auth.security.handler.FormLoginAuthenticationSuccessHandler;
import com.kravel.server.auth.security.handler.FormLoginFailureHandler;
import com.kravel.server.auth.security.handler.JwtAuthenticationFailureHandler;
import com.kravel.server.auth.security.provider.FormLoginAuthenticationProvider;
import com.kravel.server.auth.security.provider.JwtAuthenticationProvider;
import com.kravel.server.auth.security.util.jwt.HeaderTokenExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired @Lazy
    private FormLoginAuthenticationSuccessHandler formLoginAuthenticationSuccessHandler;

    private final FormLoginFailureHandler formLoginFailureHandler;

    private final FormLoginAuthenticationProvider provider;

    private final JwtAuthenticationProvider jwtProvider;

    private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;

    private final HeaderTokenExtractor headerTokenExtractor;

    private final PersistentTokenRepository persistentTokenRepository;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        return characterEncodingFilter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

//    @Bean
//    public PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices() {
//        PersistentTokenBasedRememberMeServices persistenceTokenBasedservice = new PersistentTokenBasedRememberMeServices("uniqueAndSecret", userDetailsService(), persistentTokenRepository);
//        persistenceTokenBasedservice.setParameter("auto_login");
//        persistenceTokenBasedservice.setAlwaysRemember(false);
//        persistenceTokenBasedservice.setCookieName("remember-me");
//        persistenceTokenBasedservice.setTokenValiditySeconds(60 * 60 * 24 * 7);		// 토큰 유효시간 1주일 설정
//        return persistenceTokenBasedservice;
//    }

    protected FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter filter = new FormLoginFilter("/auth/sign-in", formLoginAuthenticationSuccessHandler, formLoginFailureHandler);
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    protected JwtAuthenticationFilter jwtFilter() throws Exception {
        FilterSkipMatcher matcher = new FilterSkipMatcher(Arrays.asList("/auth/**"), "/api/**");
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(matcher, jwtAuthenticationFailureHandler, headerTokenExtractor);
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(this.provider)
                .authenticationProvider(this.jwtProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors()
            .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .csrf().disable()
                .headers().frameOptions().disable()
            .and()
                .httpBasic()
//            .and()
//                .rememberMe()
//                    .key("uniqueAndSecret") // 리멤버미 인증을 위해 발행되는 토큰을 구별하는 키 이름을 지정
//                    .tokenValiditySeconds(60 * 60 * 24 * 7)	// 기본 토큰 유효시간 설정(초) - 별도로 설정안하면 기본 2주동안 유효
//                    .rememberMeParameter("auto_login")	// 로그인 페이지에서 html (input 태그) 자동로그인 파라미터 이름(name 값)
//                    .rememberMeCookieName("remember-me")
//                    .tokenRepository(persistentTokenRepository)
//                    .userDetailsService(userDetailsService())
//                    .rememberMeServices(persistentTokenBasedRememberMeServices())
                    //.useSecureCookie(true)		//https 요청만 쿠키사용
            .and()
                .addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}