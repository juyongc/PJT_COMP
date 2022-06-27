package com.analysis.comp.model.dto;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 로그인용
 */
@Data
@NoArgsConstructor
public class LoginDto {

    @NotNull
    private String email;
    @NotNull
    private String pw;
}
