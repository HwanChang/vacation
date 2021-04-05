package com.hwanchang.vacation.controller.v1.confirm.dto;

import com.hwanchang.vacation.controller.v1.application.dto.ApplicationDto;
import com.hwanchang.vacation.controller.v1.user.dto.UserDto;
import com.hwanchang.vacation.entity.confirm.Confirm;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
@ToString
public class ConfirmResponse {

    @ApiModelProperty(value = "PK", required = true)
    private Long confirmId;

    @ApiModelProperty(value = "처리 여부", required = true)
    private boolean confirmed;

    @ApiModelProperty(value = "신청서", required = true)
    private ApplicationDto application;

    @ApiModelProperty(value = "신청자", required = true)
    private UserDto user;

    public ConfirmResponse(Confirm source) {
        copyProperties(source, this);

        this.application = new ApplicationDto(source.getApplication());
        this.user = new UserDto(source.getApplication().getUser());
    }

}
