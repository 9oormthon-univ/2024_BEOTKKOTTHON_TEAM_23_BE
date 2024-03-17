package com.beotkkotthon.areyousleeping.constants;

import java.util.List;


public class Constants {
    public static String CLAIM_USER_ID = "uuid";
    public static String CLAIM_USER_ROLE = "role";
    public static String ACCESS_COOKIE_NAME = "access_token";
    public static String REFRESH_COOKIE_NAME = "refresh_token";
    public static String BEARER_PREFIX = "Bearer ";
    public static String AUTHCODE_PREFIX = "AuthCD ";
    public static String AUTHORIZATION_HEADER = "Authorization";

    public static List<String> NO_NEED_AUTH_URLS = List.of(
            "/api/v1/no-auth/**",
            "/api/v1/oauth2/sign-up",
            "/api/v1/auth/sign-up",
            "/oauth2/authorization/kakao",
            "/oauth2/authorization/google",
            "/login/oauth2/code/kakao",
            "/login/oauth2/code/google",
            "/api-docs.html",
            "/api-docs/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/ws-connection/**",
            "/ws-connection",
            "/app/**",
            "/subscribe/**");

    public static List<String> USER_URLS = List.of(
            "/api/v1/users/**");

}
