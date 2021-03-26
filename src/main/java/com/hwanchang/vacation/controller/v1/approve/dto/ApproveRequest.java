package com.hwanchang.vacation.controller.v1.approve.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ApproveRequest {

    @ApiModelProperty(value = "결재자", required = true)
    private Long approverId;

    @ApiModelProperty(value = "결재 단계", required = true)
    private int level;

}
