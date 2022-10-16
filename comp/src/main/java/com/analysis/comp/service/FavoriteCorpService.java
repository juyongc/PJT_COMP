package com.analysis.comp.service;

import org.springframework.stereotype.Service;

public interface FavoriteCorpService {

    public boolean saveFavoriteCorp(long userpk, String corpName);         // 유저 - 관심기업 정보 매칭 저장

    public long getCountUser(String corpName);                          // 해당 기업 관심있는 유저 수 반환
}
