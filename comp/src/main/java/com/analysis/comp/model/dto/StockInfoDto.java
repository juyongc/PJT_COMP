package com.analysis.comp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class StockInfoDto {

    private String corpName;
    private String stockCode;
    private String publishNum;
    private String circulateNum;

}
