package com.beotkkotthon.areyousleeping.security.info.factory;

import com.beotkkotthon.areyousleeping.dto.type.EProvider;
import com.beotkkotthon.areyousleeping.security.info.AppleOauth2UserInfo;
import com.beotkkotthon.areyousleeping.security.info.KakaoOauth2UserInfo;

import java.util.Map;

public class Oauth2UserInfoFactory {
    public static Oauth2UserInfo getOauth2UserInfo(EProvider provider, Map<String, Object> attributes){
        switch (provider) {
            case KAKAO:
                return new KakaoOauth2UserInfo(attributes);
            case APPLE:
                return new AppleOauth2UserInfo(attributes);
            default:
                throw new IllegalAccessError("잘못된 제공자 입니다.");
        }
    }
}
