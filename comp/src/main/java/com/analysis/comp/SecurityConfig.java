package com.analysis.comp;

import com.analysis.comp.auth.JwtAuthenticationFilter;
import com.analysis.comp.auth.JwtTokenProvider;
import com.analysis.comp.auth.OAuth2FailureHandler;
import com.analysis.comp.auth.OAuth2SuccessHandler;
import com.analysis.comp.service.OAuth2AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2AccountService oauth2AccountService;
    private final OAuth2SuccessHandler successHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers( "/api/test").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin().disable()
//                .formLogin()
//                .loginPage("/api/login")
//                .usernameParameter("email")		// 로그인 시 form에서 가져올 값(id, email 등이 해당)
//                .passwordParameter("pw")		// 로그인 시 form에서 가져올 값
//                .defaultSuccessUrl("/api")
//                .failureUrl("/api/login")
//                .and()
                .oauth2Login()
                .loginPage("/api/login")
                .successHandler(successHandler)                             // 로그인 인증 성공 후, 사용될 핸들러(userService -> successhandler 순)
                .userInfoEndpoint().userService(oauth2AccountService);      // 로그인 성공 후, 사용자 정보 가져오기 및 처리

        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

    }
}
