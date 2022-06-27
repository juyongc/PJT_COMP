package com.analysis.comp.model.dto;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회원가입용
 */
@Data
@AllArgsConstructor
public class SignUpDto {

    @NotNull
    private String email;
    @NotNull
    private String nickname;
    @NotNull
    private String pw;
}
