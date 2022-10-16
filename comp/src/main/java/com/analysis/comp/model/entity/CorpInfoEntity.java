package com.analysis.comp.model.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class CorpInfoEntity {

    @Id
    @Column(name = "CORP_NAME")
    private String corpName;
    @NotNull
    private String corpCode;
    private String stockCode;
    // 발행주식총수, 유통주식총수
    private Long publishNum;
    private Long circulateNum;
    // 유동자산, 자산총계, 유동부채, 부채총계, 매출액, 영업이익, 법인세 전 순이익, 당기순이익
    private Long currentAsset;
    private Long totAsset;
    private Long currentDebt;
    private Long totDebt;
    private Long revenue;
    private Long operIncome;
    private Long befTax;
    private Long netIncome;

    public CorpInfoEntity(String corpName,String corpCode,String stockCode,Long publishNum, Long circulateNum, ArrayList arrayList) {
        this.corpName = corpName;
        this.corpCode = corpCode;
        this.stockCode = stockCode;
        this.publishNum = publishNum;
        this.circulateNum = circulateNum;
        this.currentAsset = (Long) arrayList.get(0);
        this.totAsset = (Long) arrayList.get(1);
        this.currentDebt = (Long) arrayList.get(2);
        this.totDebt = (Long) arrayList.get(3);
        this.revenue = (Long) arrayList.get(4);
        this.operIncome = (Long) arrayList.get(5);
        this.befTax = (Long) arrayList.get(6);
        this.netIncome = (Long) arrayList.get(7);
    }
}
