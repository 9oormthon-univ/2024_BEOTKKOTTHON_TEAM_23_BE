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
            "/api/v1/no-auth/**", "api/v1/oauth/login",
            "/api/v1/auth/sign-up",
            "/api/v1/auth/email-duplicate",
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

    public static List<String> MISSION = List.of(
            "질문: 당신은 투명인간이 되었다. 가장 먼저 하고싶은 것은?",
            "질문: 지금 당장 시간여행을 어디로 갈 것인가?",
            "질문: 밥 맛 똥먹기 vs 똥 맛 밥먹기",
            "질문: 외계인이 지구에 온다면, 가장 먼저 어디를 보여주고 싶나요?",
            "질문: 초능력을 하나만 가질 수 있다면, 무엇을 선택하겠습니까?",
            "질문: 좀비 아포칼립스가 발생했을 때, 가장 먼저 할 일은?",

            "사진: 세수하고 물기 닦지 않고 셀카 찍기",
            "사진: 지금 당장 생각나는거 하나 그려서 사진찍기",
            "사진: 지금 바로 눈 앞에 있는 것 찍기",
            "사진: 현재 당신의 발 모양을 찍어 보내기",
            "사진: 당신이 가진 가장 이상한 물건 찍어 보내기",
            "사진: 야간 모드로 창밖 풍경 사진 찍기",
            "사진: 얼굴로 웃기기"
    );
}
