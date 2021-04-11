package com.hwanchang.vacation.controller.v1.application.dto;

import com.hwanchang.vacation.controller.v1.approve.dto.ApproveDto;
import com.hwanchang.vacation.controller.v1.user.dto.UserDto;
import com.hwanchang.vacation.entity.application.Application;
import com.hwanchang.vacation.entity.application.State;
import com.hwanchang.vacation.entity.vacation.Vacation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
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

    @ApiModelProperty(value = "결재 개수", required = true)
    private int approveCount;

    @ApiModelProperty(value = "신청 상태", required = true)
    private State state;

    @ApiModelProperty(value = "신청자", required = true)
    private UserDto user;

    @ApiModelProperty(value = "휴가 날짜", required = true)
    private List<LocalDate> dates;

    @ApiModelProperty(value = "사유", required = true)
    private String reason;

    @ApiModelProperty(value = "결재자", required = true)
    private List<ApproveDto> approve;

    public ApplicationResponse(Application source) {
        copyProperties(source, this);

        this.user = new UserDto(source.getUser());
        this.dates = source.getVacations().stream()
                .map(Vacation::getDate)
                .collect(toList());
        this.reason = source.getVacations().stream()
                .map(Vacation::getReason)
                .distinct()
                .collect(toList()).get(0);
        this.approve = source.getApproves().stream()
                .map(ApproveDto::new)
                .collect(toList());
    }

}
