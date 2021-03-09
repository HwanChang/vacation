package com.hwanchang.vacation.controller.v1.approve.dto;

import com.hwanchang.vacation.entity.approve.Approve;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
@ToString
public class ApproveDto {

    @ApiModelProperty(value = "PK", required = true)
    private Long applicationId;

    @ApiModelProperty(value = "결재 여부", required = true)
    private boolean isApprove;

    @ApiModelProperty(value = "결재 단계", required = true)
    private int level;

    public ApproveDto(Approve source) {
        copyProperties(source, this);
    }

}
