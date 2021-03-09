package com.hwanchang.vacation.controller.v1.application.dto;

import com.hwanchang.vacation.controller.v1.approve.dto.ApproveDto;
import com.hwanchang.vacation.controller.v1.user.dto.UserDto;
import com.hwanchang.vacation.controller.v1.vacation.dto.VacationDto;
import com.hwanchang.vacation.entity.application.Application;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
@ToString
public class ApplicationResponse {

    @ApiModelProperty(value = "PK", required = true)
    private Long applicationId;

    @ApiModelProperty(value = "결재 단계", required = true)
    private int level;

    @ApiModelProperty(value = "신청자", required = true)
    private UserDto user;

    @ApiModelProperty(value = "신청자", required = true)
    private List<VacationDto> vacation;

    @ApiModelProperty(value = "신청자", required = true)
    private List<ApproveDto> approve;

    public ApplicationResponse(Application source) {
        copyProperties(source, this);

        this.user = new UserDto(source.getUser());
        this.vacation = source.getVacations().stream()
                .map(VacationDto::new)
                .collect(toList());
        this.approve = source.getApproves().stream()
                .map(ApproveDto::new)
                .collect(toList());
    }

}
