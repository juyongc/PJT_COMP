package com.analysis.comp.service;

import com.analysis.comp.model.dto.CorpSearchDto;
import com.analysis.comp.model.entity.CorpCodeEntity;
import com.analysis.comp.model.entity.CorpInfoEntity;
import com.analysis.comp.model.repository.CorpCodeRepository;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CorpInfoDBTest {

    @Autowired
    private CorpInfoServiceImpl corpInfoServiceImpl;
    @Autowired
    private CorpAnalysisService corpAnalysisService;
    @Autowired
    private CorpCodeRepository corpCodeRepository;

//    @Transactional
    @Test
    public void makeDBTest() throws IOException, ParseException, ParserConfigurationException, SAXException {
//        String corpName = "삼성전자";
        String[] corpNames = {"대동", "루트로닉", "NAVER", "카카오", "지니언스", "이마트", "오로라", "오뚜기", "삼성전기"};
        // 여기서 org.hibernate.LazyInitializationException 에러 떴는데
        // @Transactional 추가해주니 해결됐다 - 왜지? 해결 완료!
        for (String corpName : corpNames) {
            Optional<CorpCodeEntity> byId = corpCodeRepository.findById(corpName);
            System.out.println(byId.get().toString());
            corpInfoServiceImpl.makeDbByName(corpName);
            System.out.println(corpCodeRepository.findById(corpName));
            System.out.println(corpAnalysisService.requestCorpAnalysis(corpName).toString());

        }
    }

    @Transactional
    @Test
    public void getCorpsTest() {
        CorpSearchDto dto = new CorpSearchDto(5000000L, 50000000L, null, 88117132000000L, null);
        List<CorpInfoEntity> corps = corpInfoServiceImpl.findCorps(dto);
        System.out.println(corps.toString());
    }


}
