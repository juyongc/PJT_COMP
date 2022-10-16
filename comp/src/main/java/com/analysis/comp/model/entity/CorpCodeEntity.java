package com.analysis.comp.model.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "corp_code_table")
public class CorpCodeEntity {

    @Id
    private String corpName;
    @NotNull
    private String corpCode;
    private String stockCode;
}
