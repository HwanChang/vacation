package com.hwanchang.vacation.controller;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Error {

    @ApiModelProperty(value = "오류 메시지", required = true)
    private final String message;

}
