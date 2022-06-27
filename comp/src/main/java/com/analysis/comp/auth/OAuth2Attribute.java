package com.analysis.comp.auth;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class OAuth2Attribute {

    private Map<String, Object> attributes;     // OAuth2에서 반환하는 유저 정보
    private String attributeKey;
    private String email;
    private String nickname;

    @Builder
    public OAuth2Attribute(Map<String, Object> attributes, String attributeKey, String email, String nickname) {
        this.attributes = attributes;
        this.attributeKey = attributeKey;
        this.email = email;
        this.nickname = nickname;
    }

    public static OAuth2Attribute of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){

        return ofKakao("id", attributes);
    }

    public static OAuth2Attribute ofKakao(String userNameAttribute, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        return OAuth2Attribute.builder()
                .nickname(String.valueOf(kakaoProfile.get("nickname")))
                .email((String) kakaoAccount.get("email"))
                .attributes(attributes)
                .attributeKey(userNameAttribute)
                .build();

    }

    /**
     * Map 형식으로 만들기
     */
    public Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", attributeKey);
        map.put("key", attributeKey);
        map.put("email", email);
        map.put("nickname", nickname);
        return map;
    }
}
