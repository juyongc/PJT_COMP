package com.analysis.comp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class StatementInfoDto {

    private String currentAsset;
    private String totAsset;
    private String currentDebt;
    private String totDebt;
    private String revenue;
    private String operIncome;
    private String befTax;
    private String netIncome;

    public StatementInfoDto(ArrayList arrayList) {
        this.currentAsset = String.valueOf(arrayList.get(0));
        this.totAsset = String.valueOf(arrayList.get(1));
        this.currentDebt = String.valueOf(arrayList.get(2));
        this.totDebt = String.valueOf(arrayList.get(3));
        this.revenue = String.valueOf(arrayList.get(4));
        this.operIncome = String.valueOf(arrayList.get(5));
        this.befTax = String.valueOf(arrayList.get(6));
        this.netIncome = String.valueOf(arrayList.get(7));
    }
}
