package com.beotkkotthon.areyousleeping.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "팀 생성 DTO")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserActiveStatusDto {
    private boolean active;
}
