package com.analysis.comp.controller;

import com.analysis.comp.auth.JwtTokenProvider;
import com.analysis.comp.auth.JwtTokenProviderV2;
import com.analysis.comp.model.dto.LoginDto;
import com.analysis.comp.model.dto.SignUpDto;
import com.analysis.comp.model.entity.TokenEntity;
import com.analysis.comp.model.entity.UserEntity;
import com.analysis.comp.model.repository.TokenRepository;
import com.analysis.comp.model.repository.UserRepository;
import com.analysis.comp.service.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountServiceImpl;
    @Autowired
    private JwtTokenProviderV2 jwtTokenProvider;
    @Autowired
    private TokenRepository tokenRepository;


    @GetMapping("/test")
    public String test() {
        return "api/analysis/test";
    }

    /**
     * 회원가입 처리 메서드
     */
    @GetMapping("/signup")
    public String signup(@ModelAttribute("user") SignUpDto signUpDto) {
        return "api/analysis/memberForm";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("user") SignUpDto signUpDto, BindingResult bindingResult) {
        boolean check = accountServiceImpl.saveUser(signUpDto);
        if (!check) {
            bindingResult.reject("SignUpFail", "중복 이메일입니다.");
            return "api/analysis/memberForm";
        }
        return "redirect:/api";
    }

    /**
     * 로그인 처리 메서드
     */
    @GetMapping("/login")
    public String login(@ModelAttribute("login") LoginDto loginDto) {
        return "api/analysis/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("login") LoginDto loginDto, BindingResult bindingResult,
                        HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "api/analysis/loginForm";
        }

        boolean loginCheck = accountServiceImpl.login(loginDto.getEmail(), loginDto.getPw());
        if (loginCheck == false) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "api/analysis/loginForm";
        }

        UserDetails userEntity = accountServiceImpl.loadUserByUsername(loginDto.getEmail());

//        response.addHeader("Authorization", token);
//        res.addHeader("Refresh", newToken.getRefreshToken());
//        response.setContentType("application/json;charset=UTF-8");

        TokenEntity token = jwtTokenProvider.createToken(loginDto.getEmail());
        tokenRepository.save(new TokenEntity(token.getAccessToken(), token.getRefreshToken()));
        // 클라이언트가 없어서 헤더에 넣는 방법을 모르겠다. => 쿠키로 대체
        Cookie accessToken = new Cookie("AccessToken", token.getAccessToken());
        Cookie refreshToken = new Cookie("RefreshToken", token.getRefreshToken());
        accessToken.setPath("/");
        refreshToken.setPath("/");
        response.addCookie(accessToken);
        response.addCookie(refreshToken);
        log.info("Login = {}",response.toString());
        // redirectURL 주니까 에러나서 일단 변경
        return "redirect:/api";
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        log.info("LOGOUT");
        Cookie accessToken = new Cookie("AccessToken", "");
        Cookie refreshToken = new Cookie("RefreshToken", "");
        accessToken.setMaxAge(0);
        refreshToken.setMaxAge(0);
        accessToken.setPath("/");
        refreshToken.setPath("/");
        response.addCookie(accessToken);
        response.addCookie(refreshToken);
        return "redirect:/api";

    }

//    /**
//     * 쿠키 테스트용
//     */
//    @GetMapping("/oauth2/{token}")
//    public String oAuth2Token(@PathVariable("token") String token, HttpServletResponse response) {
//        log.info("TOKEN = {}",token);
//
//        Cookie authorization = new Cookie("Authorization", token);
//        authorization.setPath("/");
//        response.addCookie(authorization);
//
//        return "redirect:/api";
//    }


}
