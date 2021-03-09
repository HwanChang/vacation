package com.hwanchang.vacation.controller.v1.application.dto;

import com.hwanchang.vacation.controller.v1.approve.dto.ApproveRequest;
import com.hwanchang.vacation.controller.v1.vacation.dto.VacationRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ApplicationRequest {

    @ApiModelProperty(value = "휴가", required = true)
    private VacationRequest vacationRequest;

    @ApiModelProperty(value = "결재선", required = true)
    private List<ApproveRequest> approveRequests;

}
