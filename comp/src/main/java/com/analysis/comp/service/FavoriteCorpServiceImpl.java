package com.analysis.comp.service;

import com.analysis.comp.model.entity.CorpInfoEntity;
import com.analysis.comp.model.entity.UserEntity;
import com.analysis.comp.model.entity.UserFavoriteCorpEntity;
import com.analysis.comp.model.repository.CorpInfoRepository;
import com.analysis.comp.model.repository.UserFavoriteCorpRepository;
import com.analysis.comp.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class FavoriteCorpServiceImpl implements FavoriteCorpService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CorpInfoRepository corpInfoRepository;
    @Autowired
    private UserFavoriteCorpRepository userFavoriteCorpRepository;

    @Override
    public boolean saveFavoriteCorp(long userpk, String corpName) {

        UserEntity user = userRepository.getById(userpk);
        CorpInfoEntity corpInfo = corpInfoRepository.getById(corpName);
        UserFavoriteCorpEntity ufc = new UserFavoriteCorpEntity(user, corpInfo);
        userFavoriteCorpRepository.save(ufc);

        return true;

    }

    @Override
    public long getCountUser(String corpName) {
        return 0;
    }
}
