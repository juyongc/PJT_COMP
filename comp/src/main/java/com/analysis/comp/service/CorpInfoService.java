package com.analysis.comp.service;

import com.analysis.comp.model.dto.CorpSearchDto;
import com.analysis.comp.model.dto.SignUpDto;
import com.analysis.comp.model.entity.CorpInfoEntity;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public interface CorpInfoService {

    public void makeDbByName(String corpName) throws IOException, ParseException, ParserConfigurationException, SAXException;      // 기업명으로 db 데이터 저장
    public void makeDbByCode(String corpCode);      // 기업코드번호로 db 데이터 저장

    public List<CorpInfoEntity> findCorps(CorpSearchDto corpSearchDto);      // 기업 조건 검색 - querydsl
}
