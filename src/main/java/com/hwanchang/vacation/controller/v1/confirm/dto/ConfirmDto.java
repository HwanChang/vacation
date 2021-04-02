package com.hwanchang.vacation.controller.v1.confirm.dto;

import com.hwanchang.vacation.entity.application.Application;
import com.hwanchang.vacation.entity.confirm.Confirm;
import com.hwanchang.vacation.entity.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
@ToString
public class ConfirmDto {

    @ApiModelProperty(value = "PK", required = true)
    private Long confirmId;

    @ApiModelProperty(value = "처리 여부", required = true)
    private boolean confirmed;

    @ApiModelProperty(value = "신청서", required = true)
    private Application application;

    @ApiModelProperty(value = "처리자", required = true)
    private User user;

    public ConfirmDto(Confirm source) {
        copyProperties(source, this);
    }

}
