package com.beotkkotthon.areyousleeping.dto.request;

import com.beotkkotthon.areyousleeping.dto.type.EProvider;
import com.fasterxml.jackson.annotation.JsonProperty;

public record OauthSignUpDto(
        @JsonProperty("nickname")
        String nickname,
        @JsonProperty("provider")
        EProvider provider
) {
}