package com.hwanchang.vacation.controller.v1.application.dto;

import com.hwanchang.vacation.entity.application.Application;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
@ToString
public class ApplicationDto {

    @ApiModelProperty(value = "PK", required = true)
    private Long applicationId;

    @ApiModelProperty(value = "결재 단계", required = true)
    private int level;

    @ApiModelProperty(value = "결재 개수", required = true)
    private int approveCount;

    @ApiModelProperty(value = "신청 상태", required = true)
    private String state;

    public ApplicationDto(Application source) {
        copyProperties(source, this);

        this.state = source.getState().value();
    }

}
