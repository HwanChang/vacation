package com.hwanchang.vacation.controller.v1.vacation.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class VacationRequest {

    @ApiModelProperty(value = "휴가 날짜", required = true)
    private LocalDate date;

    @ApiModelProperty(value = "사유", required = true)
    private String reason;

}
