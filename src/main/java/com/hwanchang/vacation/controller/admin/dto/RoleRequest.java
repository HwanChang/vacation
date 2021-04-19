package com.hwanchang.vacation.controller.admin.dto;

import com.hwanchang.vacation.entity.user.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class RoleRequest {

    @ApiModelProperty(value = "사용자 PK", required = true)
    private Long userId;

    @ApiModelProperty(value = "권한", required = true)
    private List<Role> roles;

}
