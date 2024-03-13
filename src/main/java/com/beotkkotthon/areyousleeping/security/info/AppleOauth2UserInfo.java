package com.beotkkotthon.areyousleeping.security.info;

import com.beotkkotthon.areyousleeping.security.info.factory.Oauth2UserInfo;

import java.util.Map;

public class AppleOauth2UserInfo extends Oauth2UserInfo {
    public AppleOauth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }
    @Override
    public String getId() {
        return attributes.get("id_token").toString();
    }
}
