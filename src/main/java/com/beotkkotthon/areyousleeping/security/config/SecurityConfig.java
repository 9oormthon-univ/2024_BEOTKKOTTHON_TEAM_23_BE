package com.beotkkotthon.areyousleeping.security.config;

import com.beotkkotthon.areyousleeping.constants.Constants;
import com.beotkkotthon.areyousleeping.security.filter.GlobalLoggerFilter;
import com.beotkkotthon.areyousleeping.security.filter.JwtAuthenticationFilter;
import com.beotkkotthon.areyousleeping.security.filter.JwtExceptionFilter;
import com.beotkkotthon.areyousleeping.security.handler.exception.CustomAccessDeniedHandler;
import com.beotkkotthon.areyousleeping.security.handler.exception.CustomAuthenticationEntryPointHandler;
import com.beotkkotthon.areyousleeping.security.handler.login.DefaultFailureHandler;
import com.beotkkotthon.areyousleeping.security.handler.login.DefaultSuccessHandler;
import com.beotkkotthon.areyousleeping.security.handler.logout.CustomLogoutProcessHandler;
import com.beotkkotthon.areyousleeping.security.handler.logout.CustomLogoutResultHandler;
import com.beotkkotthon.areyousleeping.security.provider.JwtAuthenticationManager;
import com.beotkkotthon.areyousleeping.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final DefaultSuccessHandler defaultSuccessHandler;
    private final DefaultFailureHandler defaultFailureHandler;
    private final CustomLogoutProcessHandler customSignOutProcessHandler;
    private final CustomLogoutResultHandler customSignOutResultHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPointHandler customAuthenticationEntryPointHandler;
    private final JwtAuthenticationManager jwtAuthenticationManager;
    private final JwtUtil jwtUtil;

    @Bean
    protected SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // csrf 보호 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // httpBasic 기본 인증 방식 해제
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 안하고 상태가 없는 방식으로 인증 = JWT 사용
                )

                .authorizeHttpRequests(registry ->
                        registry
                                .requestMatchers(Constants.NO_NEED_AUTH_URLS.toArray(String[]::new)).permitAll()
                                .anyRequest().authenticated()
                )

                .formLogin(configurer ->
                        configurer
                                .loginPage("/login")
                                .loginProcessingUrl("/api/v1/auth/login")
                                .usernameParameter("serial_id")
                                .passwordParameter("password")
                                .successHandler(defaultSuccessHandler)
                                .failureHandler(defaultFailureHandler)
                )
                .logout(configurer ->
                        configurer
                                .logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(customSignOutProcessHandler)
                                .logoutSuccessHandler(customSignOutResultHandler)
                )
                .exceptionHandling(configurer ->
                        configurer
                                .accessDeniedHandler(customAccessDeniedHandler)
                                .authenticationEntryPoint(customAuthenticationEntryPointHandler)
                )

                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtUtil, jwtAuthenticationManager),
                        LogoutFilter.class)
                .addFilterBefore(
                        new JwtExceptionFilter(),
                        JwtAuthenticationFilter.class)
                .addFilterBefore(
                        new GlobalLoggerFilter(),
                        JwtExceptionFilter.class)

                .getOrBuild();
    }
}
