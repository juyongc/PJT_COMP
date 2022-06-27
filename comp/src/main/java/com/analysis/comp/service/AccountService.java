package com.analysis.comp.service;

import com.analysis.comp.model.dto.SignUpDto;
import com.analysis.comp.model.entity.UserEntity;

public interface AccountService {

    public boolean checkEmail(String email);            // 이메일 존재 여부 체크(없으면 false)
    public boolean saveUser(SignUpDto signUpDto);       // 유저 회원가입
    public boolean login(String email, String pw);      // 로그인한 유저 DB 존재 확인
}
