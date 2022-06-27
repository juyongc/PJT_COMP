package com.analysis.comp.controller;

import com.analysis.comp.auth.JwtTokenProvider;
import com.analysis.comp.model.dto.FormDto;
import com.analysis.comp.model.dto.StatementInfoDto;
import com.analysis.comp.model.dto.StockInfoDto;
import com.analysis.comp.model.repository.UserRepository;
import com.analysis.comp.service.CorpAnalysisService;
import com.analysis.comp.service.StockInfoService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/api")
public class CorpAnalysisController {

    @Autowired
    private StockInfoService stockInfoService;
    @Autowired
    private CorpAnalysisService corpAnalysisService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String hello(Model model, Model model2, HttpServletRequest request){

        log.info("COMING HOME");
        String token = jwtTokenProvider.resolveToken(request);
        log.info("HELLO TOKEN = {}",token);
        if (token != "" && jwtTokenProvider.validateToken(token)) {
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

        return "api/analysis/corp";
    }

    @PostMapping("")
    public String companyInput(@ModelAttribute("form") FormDto formDto, Model model, Model model2, HttpServletRequest request) throws IOException, ParseException, ParserConfigurationException, SAXException {

        log.info("COMING HOME");
        String token = jwtTokenProvider.resolveToken(request);
        log.info("HELLO TOKEN = {}",token);
        if (token != "" && jwtTokenProvider.validateToken(token)) {
            String email = jwtTokenProvider.getUserEmail(token);
            String nickname = userRepository.getNicknameByEmail(email);
            model.addAttribute("check", 1);
            model.addAttribute("nickname", nickname);
        } else {
            model.addAttribute("check", 0);
        }


        Map<String, String> companyData = stockInfoService.requestStockInfo(formDto.getName());
        StockInfoDto stockInfoDto = new StockInfoDto(companyData.get("회사명"), companyData.get("종목번호"), companyData.get("발행주식 총수"), companyData.get("유통주식수"));
        model.addAttribute("stockInfo", stockInfoDto);

        Map<String, ArrayList> statementData = corpAnalysisService.requestCorpAnalysis(formDto.getName());
        StatementInfoDto statementInfo1 = new StatementInfoDto(statementData.get("yearAgoAgo"));
        StatementInfoDto statementInfo2 = new StatementInfoDto(statementData.get("yearAgo"));
        StatementInfoDto statementInfo3 = new StatementInfoDto(statementData.get("yearCurrent"));
        log.info(String.valueOf(statementInfo1));
        model2.addAttribute("st1", statementInfo1);
        model2.addAttribute("st2", statementInfo2);
        model2.addAttribute("st3", statementInfo3);

        return "api/analysis/corp";
    }

}
