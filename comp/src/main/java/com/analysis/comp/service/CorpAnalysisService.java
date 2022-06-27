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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Slf4j
@AllArgsConstructor
@Service
public class CorpAnalysisService {

    @Autowired
    private CorpCodeRepository corpCodeRepository;

    public Map<String, ArrayList> requestCorpAnalysis(String corpName) throws IOException, ParseException {

        Map<String, ArrayList> operIncome = new HashMap<String, ArrayList>();
        // 기업명으로 검색하면 => 고유번호, 종목번호 가져오기
        Optional<CorpCodeEntity> corp = corpCodeRepository.findById(corpName);
        if (corp.isEmpty()) {
            return operIncome;
        }

        String corpCode = corp.get().getCorpCode();
        String stockCode = corp.get().getStockCode();

        // Dart open api 통신
        StringBuilder sb = new StringBuilder("https://opendart.fss.or.kr/api/fnlttMultiAcnt.json?");
        sb.append("crtfc_key=" + "244ee64d3bdf61a52cb8aaad420023c2a53fd480");
        sb.append("&" + "corp_code=" + corpCode);
        sb.append("&" + "bsns_year=" + "2021");
        sb.append("&" + "reprt_code=" + "11011");
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

        Map<String, String> companyInfo = new HashMap<String, String>();
        // String 형식으로 저장된 json 에서 원하는 부분 파싱으로
        JSONParser parser = new JSONParser();
        JSONObject jObj = (JSONObject) parser.parse(String.valueOf(res));
        JSONArray jsonArr = (JSONArray) jObj.get("list");

//         유동자산, 자산총계, 유동부채, 부채총계, 매출액, 영업이익, 법인세 전 순이익, 당기순이익
        ArrayList<String> yearCurrentList = new ArrayList<>();
        ArrayList<String> yearAgoList = new ArrayList<>();
        ArrayList<String> yearAgoAgoList = new ArrayList<>();
        final Set<Integer> needNum = new HashSet<>(Arrays.asList(0, 2, 3, 5, 9, 10, 11, 12));
        for (int i=0; i < 13; i++) {
            if (needNum.contains(i)) {
                JSONObject jObj2 = (JSONObject) parser.parse(String.valueOf(jsonArr.get(i)));
                String yearCurrentInfo = String.valueOf(jObj2.get("thstrm_amount"));
                String yearAgoInfo = String.valueOf(jObj2.get("frmtrm_amount"));
                String yearAgoAgoInfo = String.valueOf(jObj2.get("bfefrmtrm_amount"));
                yearCurrentList.add(yearCurrentInfo);
                yearAgoList.add(yearAgoInfo);
                yearAgoAgoList.add(yearAgoAgoInfo);
            }
        }

//        for (int i=0;i<yearAgoAgoList.size();i++) {
//            ArrayList<String> groupList = new ArrayList<>();
//            groupList.add(yearAgoAgoList.get(i));
//            groupList.add(yearAgoList.get(i));
//            groupList.add(yearCurrentList.get(i));
//            operIncome.put(String.valueOf(i), groupList);
//        }
//        for (String s : operIncome.keySet()) {
//            log.info(String.valueOf(operIncome.get(s)));
//        }
        operIncome.put("yearAgoAgo", yearAgoAgoList);
        operIncome.put("yearAgo", yearAgoList);
        operIncome.put("yearCurrent", yearCurrentList);

        return operIncome;
    }
}
