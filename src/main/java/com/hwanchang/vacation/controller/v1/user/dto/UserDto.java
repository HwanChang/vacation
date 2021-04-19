package com.hwanchang.vacation.controller.v1.user.dto;

import com.hwanchang.vacation.entity.user.Role;
import com.hwanchang.vacation.entity.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
@ToString
public class UserDto {

    @ApiModelProperty(value = "PK", required = true)
    private Long userId;

    @ApiModelProperty(value = "이메일", required = true)
    private String email;

    @ApiModelProperty(value = "사용자명", required = true)
    private String name;

    @ApiModelProperty(value = "권한", required = true)
    private List<Role> roles;

    @ApiModelProperty(value = "전화번호", required = true)
    private String phone;

    @ApiModelProperty(value = "로그인 횟수", required = true)
    private int loginCount;

    @ApiModelProperty(value = "마지막 로그인 날짜", required = true)
    private LocalDateTime lastLoginAt;

    public UserDto(User source) {
        copyProperties(source, this);
    }

}
