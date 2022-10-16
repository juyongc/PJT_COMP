package com.analysis.comp.service;

import com.analysis.comp.model.entity.CorpCodeEntity;
import com.analysis.comp.model.repository.CorpCodeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@AllArgsConstructor
@Service
/**
 * 기업정보에서 주식 수 관련 정보 가져오기
 * 정확성 높일 필요 있음 - 이전 보고서에서 가져오면 좋은데 현재는 작년 사업보고서 기준으로 가져오고 있음
 */
public class StockInfoService {

    @Autowired
    private CorpCodeRepository corpCodeRepository;

    public Map<String,String> requestStockInfo(String corpName) throws IOException, ParseException, ParserConfigurationException, SAXException {

        Map<String, String> stockInfo = new HashMap<String, String>();
        // 기업명으로 검색하면 => 고유번호, 종목번호 가져오기
        Optional<CorpCodeEntity> corp = corpCodeRepository.findById(corpName);
        if (corp.isEmpty()) {
            return stockInfo;
        }

        String corpCode = corp.get().getCorpCode();
        String stockCode = corp.get().getStockCode();

        stockInfo.put("회사명", corpName);
        stockInfo.put("종목번호", stockCode);


        // 년도 구해오기 - 작년 사업보고서 기준으로 주식 총수 구함
        // 추후, 개월별로 보고서 가져오게 만들면 될 듯
        LocalDate now = LocalDate.now();
        String year = String.valueOf(now.getYear()-1);

        // Dart에서 기업의 주식수 관련 정보 가져오기
        StringBuilder sb = new StringBuilder("https://opendart.fss.or.kr/api/stockTotqySttus.json?");
        sb.append("crtfc_key=" + "244ee64d3bdf61a52cb8aaad420023c2a53fd480");
        sb.append("&corp_code=" + corpCode);
        sb.append("&bsns_year=" + year);
        sb.append("&reprt_code=" + "11011");
        String apiUrl = sb.toString();

        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-type","application/json");
        con.setDoOutput(true);

        BufferedReader br;
        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        } else {
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }

        String inputLine;
        StringBuffer res = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            res.append(inputLine);
        }
        br.close();

        JSONParser parser = new JSONParser();
        JSONObject jObj = (JSONObject) parser.parse(String.valueOf(res));
        JSONArray jsonArr = (JSONArray) jObj.get("list");
        JSONObject jObj2 = (JSONObject) parser.parse(String.valueOf(jsonArr.get(0)));

        stockInfo.put("발행주식 총수", (String) jObj2.get("istc_totqy"));
        stockInfo.put("유통주식수", (String) jObj2.get("distb_stock_co"));

        log.info(stockInfo.get("발행주식 총수"));
        log.info(stockInfo.get("유통주식수"));

        return stockInfo;
    }
}
