package com.analysis.comp.service;

import com.analysis.comp.model.dto.SignUpDto;
import com.analysis.comp.model.entity.UserEntity;
import com.analysis.comp.model.entity.UserFavoriteCorpEntity;
import com.analysis.comp.model.repository.UserFavoriteCorpRepository;
import com.analysis.comp.model.repository.UserRepository;
import com.analysis.comp.service.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class UserTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountServiceImpl accountServiceImpl;
    @Autowired
    private FavoriteCorpServiceImpl favoriteCorpServiceImpl;
    @Test
    public void userDataTest() {
        SignUpDto signUpDto = new SignUpDto("email", "nick", "1q2w3er!");
        boolean result = accountServiceImpl.saveUser(signUpDto);
        System.out.println(result);
    }

    @Test
    public void getUserTest() {
        List<UserEntity> allUsers = userRepository.findAll();
//        List<UserEntity> allUsers = userRepository.findAllUsers();
        for (UserEntity user: allUsers) {
            for (UserFavoriteCorpEntity userFavoriteCorpEntity : user.getFavoriteCorpList()) {
                System.out.println(userFavoriteCorpEntity.getCorpInfo().getCorpName());
            }
        }
//        UserEntity admin = userRepository.getByNickname("admin2");
//        boolean check = favoriteCorpServiceImpl.saveFavoriteCorp(admin.getUserpk(), "카카오");
//        for (UserFavoriteCorpEntity corp : admin.getFavoriteCorpList()) {
//            System.out.println(corp.getCorpInfo().getCorpName());
//        }

//        System.out.println(check);
//        System.out.println(admin.toString());
//        UserDetails userDetails = accountServiceImpl.loadUserByUsername("admin");
//        System.out.println(userDetails.toString());
    }

}
