package com.analysis.comp.service;

import com.analysis.comp.model.entity.CorpCodeEntity;
import com.analysis.comp.model.repository.CorpCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

@Slf4j
@Service
public class ZipDecompressService {

    @Autowired
    private CorpCodeRepository corpCodeRepository;

    /**
     * DART OPEN API에서 공시번호, 기업명, 종목 코드 가져와
     * DB에 저장하는 메서드
     */
    public void decompress() throws IOException, ParserConfigurationException, SAXException, TransformerException {

        File file = new File("C:\\Users\\chunj\\Desktop\\company analysis\\corpcode.xml");      // xml 저장한 위치

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder docuBuilder = dbf.newDocumentBuilder();
        Document document = docuBuilder.parse(file);
        document.getDocumentElement().normalize();

        Element root = document.getDocumentElement();
        NodeList childNodes = root.getElementsByTagName("list");
        // 종목코드가 존재하는 종목에 한해서 값 가져와 DB에 저장
        for (int i=0; i< childNodes.getLength();i++) {
            Node nodeInfo = childNodes.item(i);

            if(nodeInfo.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodeInfo;
                if (!getTagValue("stock_code",element).trim().equals("")) {
                    String corpName = getTagValue("corp_name",element);     // 기업명
                    String corpCode = getTagValue("corp_code",element);     // 공시번호
                    String stockCode = getTagValue("stock_code",element);   // 종목명
                    // DB에 존재하지 않는 기업명이면 => DB 저장
                    if (!corpCodeRepository.existsById(corpName)) {
                        CorpCodeEntity corpCodeEntity = new CorpCodeEntity(corpName, corpCode, stockCode);
                        corpCodeRepository.save(corpCodeEntity);
                    }
                }
            }
        }

//        //XML을 string으로 출력 => XML 구조 확인용
//        Transformer tf = TransformerFactory.newInstance().newTransformer();
//        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//        tf.setOutputProperty(OutputKeys.INDENT, "yes");
//        Writer out = new StringWriter();
//        tf.transform(new DOMSource(document), new StreamResult(out));
//        System.out.println(out.toString());
    }


    /**
     * 노드를 Element 타입으로 변환한 뒤, 원하는 태그 값 가져오기 위한 메서드
     */
    private static String getTagValue(String tag, Element eElement) {
        Node nValue=null;

        NodeList x= eElement.getElementsByTagName(tag);
        Node test=x.item(0);
        NodeList t=null;

        if(test!=null) {
            t= test.getChildNodes();
            if((Node)t.item(0)!=null) {nValue=(Node)t.item(0);}
        }

        if(nValue==null) return null;
        return nValue.getNodeValue();
    }
}
