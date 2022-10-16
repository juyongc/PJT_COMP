package com.analysis.comp.service;

import com.analysis.comp.auth.JwtTokenProviderV2;
import com.analysis.comp.model.entity.TokenEntity;
import com.analysis.comp.model.repository.TokenRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
public class TokenTest {

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private JwtTokenProviderV2 jwtTokenProvider;

    /**
     * findByAccessToken(), findByRefreshToken() 값 같은지 확인용 테스트
     */
    @Test
    public void tokenCheck() {
        Optional<TokenEntity> token = tokenRepository.findById(14L);
        TokenEntity tokenEntity = token.get();
        TokenEntity byAccessToken = tokenRepository.findByAccessToken(tokenEntity.getAccessToken());
        TokenEntity byRefreshToken = tokenRepository.findByRefreshToken(tokenEntity.getRefreshToken());
        System.out.println(tokenEntity.toString());

        Assertions.assertThat(tokenEntity).isEqualTo(byAccessToken);
        Assertions.assertThat(byAccessToken).isEqualTo(byRefreshToken);
    }

}
