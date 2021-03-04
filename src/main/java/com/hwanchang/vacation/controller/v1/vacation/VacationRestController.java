package com.hwanchang.vacation.controller.v1.vacation;

import com.hwanchang.vacation.controller.v1.vacation.dto.ReasonRequest;
import com.hwanchang.vacation.controller.v1.vacation.dto.VacationDto;
import com.hwanchang.vacation.controller.v1.vacation.dto.VacationRequest;
import com.hwanchang.vacation.security.JwtAuthentication;
import com.hwanchang.vacation.service.vacation.VacationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Api(tags = {"휴가 APIs"})
@RequestMapping("api/v1")
@RequiredArgsConstructor
@RestController
public class VacationRestController {

    private final VacationService vacationService;

    @ApiOperation(value = "휴가 등록")
    @PostMapping(path = "vacation")
    public ResponseEntity<VacationDto> add(
            @AuthenticationPrincipal JwtAuthentication authentication,
            @RequestBody VacationRequest vacationRequest
    ) {
        return new ResponseEntity<>(
                new VacationDto(
                        vacationService.add(authentication.userId, vacationRequest.getDate(), vacationRequest.getReason())
                ),
                HttpStatus.CREATED
        );
    }

    @ApiOperation(value = "내 휴가 전체 조회")
    @GetMapping(path = "vacations")
    public ResponseEntity<List<VacationDto>> findAll(@AuthenticationPrincipal JwtAuthentication authentication) {
        return ResponseEntity.ok(
                vacationService.findByUserUserId(authentication.userId).stream()
                        .map(VacationDto::new)
                        .collect(toList())
        );
    }

    @ApiOperation(value = "휴가 사유 수정")
    @PatchMapping(path = "vacation")
    public ResponseEntity<VacationDto> update(
            @AuthenticationPrincipal JwtAuthentication authentication,
            @RequestBody ReasonRequest reasonRequest
    ) {
        return ResponseEntity.ok(
                new VacationDto(
                        vacationService.updateReason(authentication.userId, reasonRequest.getVacationId(), reasonRequest.getReason())
                )
        );
    }

    @ApiOperation(value = "휴가 삭제")
    @DeleteMapping(path = "vacation")
    public ResponseEntity<VacationDto> delete(
            @AuthenticationPrincipal JwtAuthentication authentication,
            @RequestBody @ApiParam(value = "example: {\"vacationId\": 1}", example = "{\"vacationId\": 1}") Map<String, Long> request
    ) {
        return ResponseEntity.ok(
                new VacationDto(
                        vacationService.delete(authentication.userId, request.get("vacationId"))
                )
        );
    }

}
