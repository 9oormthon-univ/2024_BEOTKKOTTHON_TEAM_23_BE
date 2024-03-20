package com.beotkkotthon.areyousleeping.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "팀 생성 DTO")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserActiveStatusDto {

    @Schema(description = "밤샘 활성화 여부")
    private boolean isActive;

}
