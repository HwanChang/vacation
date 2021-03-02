package com.hwanchang.vacation.controller.v1.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import static com.google.common.base.Preconditions.checkNotNull;

@Getter
@ToString
public class JoinResponse {

    @ApiModelProperty(value = "API 토큰", required = true)
    private final String token;

    @ApiModelProperty(value = "사용자 정보", required = true)
    private final UserDto user;

    public JoinResponse(String token, UserDto user) {
        checkNotNull(token, "token must be provided.");
        checkNotNull(user, "user must be provided.");

        this.token = token;
        this.user = user;
    }

}
