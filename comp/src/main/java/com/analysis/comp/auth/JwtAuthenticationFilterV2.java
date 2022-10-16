package com.analysis.comp.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
/***
 * httpservletRequest,Response 사용하기 위해 상속 변경
 */
public class JwtAuthenticationFilterV2 extends OncePerRequestFilter {

    private final JwtTokenProviderV2 jwtTokenProvider;

    /**
     * JWT 토큰 유효성 확인 필터
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // JWT 토큰 가져오기
        log.info("filter request check = {}");
        String token = jwtTokenProvider.resolveToken(request);
        log.info("filter token = {}",token);

//        if (token != "") {
//            Cookie accessToken = new Cookie("AccessToken", token);
//            accessToken.setPath("/");
//            response.addCookie(accessToken);
//        }

        // 토큰 유효성 확인
        if (token != "" && jwtTokenProvider.verifyToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);  // 토큰으로부터 유저 정보를 가져오기
            SecurityContextHolder.getContext().setAuthentication(authentication);       // SecurityContext에 Authentication 객체 저장
            log.info("CHECK AUTHENTICATION = {}",authentication);
        }
        filterChain.doFilter(request,response);
    }




}
