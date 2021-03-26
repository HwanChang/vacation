package com.hwanchang.vacation.controller.v1.vacation.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class VacationRequest {

    @ApiModelProperty(value = "휴가 날짜", required = true)
    private List<LocalDate> dates;

    @ApiModelProperty(value = "사유", required = true)
    private String reason;

}
