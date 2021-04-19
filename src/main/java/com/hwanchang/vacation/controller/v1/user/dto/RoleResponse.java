package com.hwanchang.vacation.controller.v1.user.dto;

import com.hwanchang.vacation.entity.user.Role;
import com.hwanchang.vacation.entity.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
@ToString
public class RoleResponse {

    @ApiModelProperty(value = "권한", required = true)
    private List<Role> roles;

    public RoleResponse(User source) {
        copyProperties(source, this);
    }

}
