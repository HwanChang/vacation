package com.hwanchang.vacation.controller.v1.vacation.dto;

import com.hwanchang.vacation.entity.vacation.Vacation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
@ToString
public class VacationDto {

    @ApiModelProperty(value = "PK", required = true)
    private Long vacationId;

    @ApiModelProperty(value = "휴가 날짜", required = true)
    private LocalDate date;

    @ApiModelProperty(value = "사유", required = true)
    private String reason;

    public VacationDto(Vacation source) {
        copyProperties(source, this);
    }

}
