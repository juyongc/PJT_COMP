package com.analysis.comp.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * JWT 토큰 유효성 확인 필터
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // JWT 토큰 가져오기
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        log.info("FILTER CHECK TOKEN = {}",token);
        // 토큰 유효성 확인
        if (token != "" && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);  // 토큰으로부터 유저 정보를 가져오기
            SecurityContextHolder.getContext().setAuthentication(authentication);       // SecurityContext에 Authentication 객체 저장
            log.info("CHECK AUTHENTICATION = {}",authentication);
        }
        chain.doFilter(request,response);
    }

}
