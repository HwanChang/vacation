package com.hwanchang.vacation.controller.v1.vacation.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReasonRequest {

    @ApiModelProperty(value = "PK", required = true)
    private Long vacationId;

    @ApiModelProperty(value = "사유", required = true)
    private String reason;


}
