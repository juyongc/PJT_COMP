package com.analysis.comp.service;

import com.analysis.comp.model.dto.SignUpDto;
import com.analysis.comp.model.entity.UserEntity;
import com.analysis.comp.model.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    /**
     * 이메일 회원가입 여부 확인
     * 있으면 - false, 없으면 - true
     */
    @Override
    public boolean checkEmail(String email) {
        if (email.equals(userRepository.existByEmail(email))) {
            return false;
        }
        return true;
    }

    @Override
    public boolean saveUser(SignUpDto signUpDto) {
        if (checkEmail(signUpDto.getEmail()) == false) {
            return false;
        }
        UserEntity user = new UserEntity(signUpDto.getEmail(), signUpDto.getNickname(), signUpDto.getPw());
        String encodePw = passwordEncoder.encode(user.getPw());
        user.setPw(encodePw);
        userRepository.save(user);

        return true;
    }

    @Override
    public boolean login(String email, String password) {

        log.info(email,password);
        if (!email.equals(userRepository.existByEmail(email))) {
            return false;
        }

        if (passwordEncoder.matches(password, userRepository.getPwByEmail(email))) {
            return true;
        }
        return false;
    }

    /**
     * UserDetailsService override
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.getAllByEmail(username).get();
        return userEntity;
    }
}
