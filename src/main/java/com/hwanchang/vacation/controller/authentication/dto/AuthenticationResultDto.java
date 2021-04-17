package com.hwanchang.vacation.controller.authentication.dto;

import com.hwanchang.vacation.controller.v1.user.dto.UserDto;
import com.hwanchang.vacation.security.AuthenticationResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
@ToString
public class AuthenticationResultDto {

    @ApiModelProperty(value = "API 토큰", required = true)
    private String token;

    @ApiModelProperty(value = "사용자 정보", required = true)
    private UserDto user;

    public AuthenticationResultDto(AuthenticationResult source) {
        copyProperties(source, this);

        this.user = new UserDto(source.getUser());
    }

}
