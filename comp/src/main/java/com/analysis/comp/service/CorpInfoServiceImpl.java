package com.analysis.comp.service;

import com.analysis.comp.model.dto.CorpSearchDto;
import com.analysis.comp.model.entity.CorpCodeEntity;
import com.analysis.comp.model.entity.CorpInfoEntity;
import com.analysis.comp.model.entity.QCorpInfoEntity;
import com.analysis.comp.model.repository.CorpCodeRepository;
import com.analysis.comp.model.repository.CorpInfoRepository;
import com.querydsl.core.BooleanBuilder;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Transactional
@Service
public class CorpInfoServiceImpl implements CorpInfoService{

    @Autowired
    private StockInfoService stockInfoService;
    @Autowired
    private CorpAnalysisService corpAnalysisService;
    @Autowired
    private CorpInfoRepository corpInfoRepository;
    @Autowired
    private CorpCodeRepository corpCodeRepository;

    @Override
    public void makeDbByName(String corpName) throws IOException, ParseException, ParserConfigurationException, SAXException {
        CorpCodeEntity corpCodeData = corpCodeRepository.getById(corpName);
        Map<String, String> stockInfo = stockInfoService.requestStockInfo(corpName);
        Long publishNum = Long.valueOf(stockInfo.get("발행주식 총수").replace(",",""));
        Long circulateNum = Long.valueOf(stockInfo.get("유통주식수").replace(",",""));
        Map<String, ArrayList> corpInfo = corpAnalysisService.requestCorpAnalysis(corpName);
        ArrayList yearCurrent = corpInfo.get("yearCurrent");

        CorpInfoEntity corpInfoEntity = new CorpInfoEntity(
                corpName,corpCodeData.getCorpCode(),corpCodeData.getStockCode(),
                publishNum,circulateNum,yearCurrent
                );
        corpInfoRepository.save(corpInfoEntity);
    }

    @Override
    public void makeDbByCode(String corpCode) {

    }

    @Override
    public List<CorpInfoEntity> findCorps(CorpSearchDto corpSearchDto) {

        List<CorpInfoEntity> corps = corpInfoRepository.findAllFromCondition(corpSearchDto);

        return corps;
    }

}
