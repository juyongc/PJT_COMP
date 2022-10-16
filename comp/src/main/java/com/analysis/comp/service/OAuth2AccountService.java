package com.analysis.comp.service;

import com.analysis.comp.auth.OAuth2Attribute;
import com.analysis.comp.model.dto.SignUpDto;
import com.analysis.comp.model.entity.UserEntity;
import com.analysis.comp.model.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class OAuth2AccountService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountServiceImpl accountServiceImpl;

    /**
     * OAuth2로 생성된 유저 정보 반환
     * 신규유저면 회원가입 자동으로 한 뒤, 정보 반환
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2로 사용자 인증 및 정보 가져오기
        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        Map<String, Object> oAuth2AttributeToMap = oAuth2Attribute.convertToMap();

        // 첫 사용자는 자동 회원가입 시키기
        if (accountServiceImpl.checkEmail((String) oAuth2AttributeToMap.get("email"))) {
            String pw = UUID.randomUUID().toString().substring(0, 8);
            SignUpDto signUpDto = new SignUpDto((String) oAuth2AttributeToMap.get("email"), (String) oAuth2AttributeToMap.get("nickname"), pw);
            accountServiceImpl.saveUser(signUpDto);
        }

        return new DefaultOAuth2User(null,oAuth2AttributeToMap,"email");
    }
}
