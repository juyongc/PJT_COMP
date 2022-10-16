package com.analysis.comp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorpSearchDto {

    private Long revenue;
    private Long operIncome;
    private Long netIncome;
    private Long currentDebt;
    private Long totDebt;

    public CorpSearchDto(ArrayList arrayList) {
        this.revenue = (Long) arrayList.get(0);
        this.operIncome = (Long) arrayList.get(1);
        this.netIncome = (Long) arrayList.get(2);
        this.currentDebt = (Long) arrayList.get(3);
        this.totDebt = (Long) arrayList.get(4);

    }

    public List<Long> getAll() {
        List<Long> corpDataList = Arrays.asList(
                this.revenue, this.operIncome, this.netIncome, this.currentDebt, this.totDebt
                );
        return corpDataList;
    }
}
