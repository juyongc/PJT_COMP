package com.analysis.comp.auth;

import com.analysis.comp.service.AccountServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AccountServiceImpl accountServiceImpl;

    /**
     * 인증 성공 후, 일어날 절차 생성 메서드
     * JWT 토큰 생성 및 헤더에 추가 및 반환
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info(String.valueOf(authentication));
        String token = jwtTokenProvider.createToken(authentication.getName());

        Cookie authorization = new Cookie("Authorization", token);
        authorization.setPath("/");
        response.addCookie(authorization);
        getRedirectStrategy().sendRedirect(request,response,"/api");
//        String targetUrl = "http://localhost:8080/api/oauth2/" + token;
//        log.info(targetUrl);
//        getRedirectStrategy().sendRedirect(request,response,targetUrl);

    }
}
