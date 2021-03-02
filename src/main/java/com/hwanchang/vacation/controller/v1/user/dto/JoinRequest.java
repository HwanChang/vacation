package com.hwanchang.vacation.controller.v1.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinRequest {

    @ApiModelProperty(value = "이메일", required = true)
    private String email;

    @ApiModelProperty(value = "이름", required = true)
    private String name;

    @ApiModelProperty(value = "패스워드", required = true)
    private String password;

    @ApiModelProperty(value = "전화번호", required = true)
    private String phone;

}
