package com.analysis.comp.auth;

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

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
//    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserEntity userEntity = userRepository.getAllByEmail(username).get();
//        String encodePw = passwordEncoder.encode(userEntity.getPw());
//        userEntity.setPw(encodePw);

        return userEntity;
    }
}
