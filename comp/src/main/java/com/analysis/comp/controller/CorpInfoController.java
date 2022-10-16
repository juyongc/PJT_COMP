package com.analysis.comp.controller;

import com.analysis.comp.auth.JwtTokenProviderV2;
import com.analysis.comp.model.dto.FormDto;
import com.analysis.comp.model.dto.StatementInfoDto;
import com.analysis.comp.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class CorpInfoController {

    @Autowired
    private JwtTokenProviderV2 jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/corpInfo")
    public String showCorpInfo(Model model, Model model2, HttpServletRequest request){

        String token = jwtTokenProvider.resolveToken(request);
        if (token != "" && jwtTokenProvider.verifyToken(token)) {
            String email = jwtTokenProvider.getUserEmail(token);
            String nickname = userRepository.getNicknameByEmail(email);
            model.addAttribute("check", 1);
            model.addAttribute("nickname", nickname);
        } else {
            model.addAttribute("check", 0);
        }

        model.addAttribute("form", new FormDto());

        StatementInfoDto statementInfo1 = new StatementInfoDto();
        StatementInfoDto statementInfo2 = new StatementInfoDto();
        StatementInfoDto statementInfo3 = new StatementInfoDto();
        log.info(String.valueOf(statementInfo1));
        model2.addAttribute("st1", statementInfo1);
        model2.addAttribute("st2", statementInfo2);
        model2.addAttribute("st3", statementInfo3);

        return "api/analysis/corpInfo";
    }
}
