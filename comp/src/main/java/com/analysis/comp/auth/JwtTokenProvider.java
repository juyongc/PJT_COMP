package com.analysis.comp.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private String secretKey = "projectkey";
    private long tokenValidTime = 30 * 60 * 1000L;

    @Autowired
    private UserService userService;


    /**
     * 스크릿키 base64 encoding
     */
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * jwt 토큰 생성
     * 클레임 : 이메일, 생성 시간, 유효 기간
     */
    public String createToken(String email) {
        Claims claimEmail = Jwts.claims().setSubject(email);
        Date now = new Date();
        log.info("secret key = " + secretKey);
        return Jwts.builder()
                .setClaims(claimEmail)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * JWT 토큰에서 인증 정보 조회
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userService.loadUserByUsername(this.getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * JWT 토큰에서 회원 정보 추출 - 이메일
     */
    public String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * 헤더에서 토큰값 가져오기
     */
    public String resolveToken(HttpServletRequest request) {

        String token = "";
        Cookie[] cookieList = request.getCookies();
        log.info("CHECK Cookie LIST");
        if (cookieList != null) {
            for (Cookie cookie : cookieList) {
                if (cookie.getName().equals("Authorization")) {
                    token = cookie.getValue();
                    break;
                }
            }
            log.info("RESOLVE TOKEN = {}",token);
        }
        return token;

//        // 헤더말고 쿠키에서 가져옴
//        return request.getHeader("Authorization");
    }

    /**
     * 토큰 유효성 확인
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }



}
