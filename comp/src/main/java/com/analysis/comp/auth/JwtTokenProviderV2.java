package com.analysis.comp.auth;

import com.analysis.comp.model.entity.TokenEntity;
import com.analysis.comp.model.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProviderV2 {

    private String secretKey = "projectkey";
    private long accessTokenValidation = 1000L * 10 * 60;                       // 엑세스 토큰 = 10분
//    private long accessTokenValidation = 1000L * 60;                            // test용 엑세스 토큰 = 1분
    private long refreshTokenValidation = 1000L * 60 * 60 * 24 * 7 * 4;         // 리프레시 토큰 = 4주(28일)

    @Autowired
    private UserService userService;
    @Autowired
    private TokenRepository tokenRepository;

    /**
     * 스크릿키 base64 encoding
     */
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * jwt 토큰 생성 (accessToken, refreshToken)
     * 클레임 : 이메일, 생성 시간, 유효 기간
     */
    public TokenEntity createToken(String email) {
        Claims claimEmail = Jwts.claims().setSubject(email);
        Date now = new Date();

        return new TokenEntity(
                Jwts.builder()
                        .setClaims(claimEmail)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + accessTokenValidation))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact(),
                Jwts.builder()
                        .setClaims(claimEmail)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + refreshTokenValidation))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact()
        );
    }

    /**
     * access 토큰 재생성 및 반환
     * 클레임 : 이메일, 생성 시간, 유효 기간
     */
    public String RecreateAccessToken(String email) {
        Claims claimEmail = Jwts.claims().setSubject(email);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claimEmail)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidation))
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

        if (cookieList == null) {
            return token;
        }

        for (Cookie cookie : cookieList) {
            if (cookie.getName().equals("AccessToken")) {
                token = cookie.getValue();
                // access token 유효하지 않으면 새 access token 반환
                if (!verifyToken(token)) {
                    log.info("Check access token");
                    log.info("current access token = {}",token);
                    TokenEntity tokenEntity = tokenRepository.findByAccessToken(token);
                    token = verifyRefreshToken(tokenEntity.getRefreshToken());
                    tokenEntity.setAccessToken(token);
                    tokenRepository.save(tokenEntity);
                    log.info("changed access token = {}",token);
                    return token;
                }
                break;
            }
        }
        return token;

//        // 헤더말고 쿠키에서 가져옴
//        return request.getHeader("Authorization");
    }

    /**
     * 토큰 유효성 확인
     * 유효하면 - true 반환
     */
    public boolean verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            log.info("verify = {}",claims.getBody().getExpiration().after(new Date()));
            return claims.getBody().getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * refresh 토큰 유효성 확인
     * 유효하면 - 새 access token 생성 및 반환
     * 유효하지 않으면 - null
     */
    public String verifyRefreshToken(String refreshToken) {

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(refreshToken);
            log.info("verify refresh = {}",claims.getBody().getExpiration().after(new Date()));
            if (claims.getBody().getExpiration().after(new Date())) {
                return RecreateAccessToken(getUserEmail(refreshToken));
            }
        } catch (Exception e) {
            return "";
        }

        return "";
    }


}
