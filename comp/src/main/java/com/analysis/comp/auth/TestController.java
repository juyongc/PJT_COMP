package com.analysis.comp.auth;

import com.analysis.comp.model.dto.SignUpDto;
import com.analysis.comp.model.entity.UserEntity;
import com.analysis.comp.model.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class TestController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    private PasswordEncoder passwordEncoder;
    SignUpDto signUpDto = new SignUpDto("token3", "token3", "token3");
    UserEntity user = new UserEntity(signUpDto.getEmail(), signUpDto.getNickname(), signUpDto.getPw());

    @PostMapping("/test")
    public String test() {
        return "<h1>test 통과</h1>";
    }

    @PostMapping("/logout")
    public String logout() {
        return "<h1>로그아웃 성공</h1>";
    }

    /**
     * 회원가입 메서드
     * SPRINGSECURITY로 인해 PasswordEncoder 없으면 에러 나옴
     * PasswordEncoder 사용해서 비밀번호 암호화 및 저장
     */
    @PostMapping("/signup")
    public String signup() {
        
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encodePw = passwordEncoder.encode(user.getPw());
        user.setPw(encodePw);
        userRepository.save(user);

        return user.toString();
    }

    /**
     * 로그인 메서드
     * 사용자임을 확인하고, JWT 토큰 생성 및 반환
     */
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        if (!(user.get("email")).equals(userRepository.existByEmail(user.get("email")))) {
            return "패스워드 비밀번호를 확인해줘";
        }
        UserDetails userEntity = userService.loadUserByUsername(user.get("email"));
        log.info(String.valueOf(userEntity));
        return jwtTokenProvider.createToken(user.get("email"));
    }
}
