package com.hwanchang.vacation.security;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class AuthenticationRequest {

    @ApiModelProperty(value = "이메일", required = true)
    private String principal;

    @ApiModelProperty(value = "패스워드", required = true)
    private String credentials;

}
