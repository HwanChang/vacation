package com.hwanchang.vacation.controller.v1.application;

import com.hwanchang.vacation.controller.v1.application.dto.ApplicationRequest;
import com.hwanchang.vacation.controller.v1.application.dto.ApplicationResponse;
import com.hwanchang.vacation.security.JwtAuthentication;
import com.hwanchang.vacation.service.application.ApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Api(tags = {"신청 APIs"})
@RequestMapping("api/v1")
@RequiredArgsConstructor
@RestController
public class ApplicationRestController {

    private final ApplicationService applicationService;

    @ApiOperation(value = "신청 등록")
    @PostMapping(path = "application")
    public ResponseEntity<ApplicationResponse> add(
            @AuthenticationPrincipal JwtAuthentication authentication,
            @RequestBody ApplicationRequest applicationRequest
    ) {
        return new ResponseEntity<>(
                new ApplicationResponse(
                        applicationService.save(authentication.userId, applicationRequest.getVacationRequest(), applicationRequest.getApproveRequests())
                ),
                HttpStatus.CREATED
        );
    }

    @ApiOperation(value = "내 신청서 조회")
    @GetMapping(path = "application")
    public ResponseEntity<List<ApplicationResponse>> findAll(@AuthenticationPrincipal JwtAuthentication authentication) {
        return ResponseEntity.ok(
                applicationService.findAll(authentication.userId).stream()
                        .map(ApplicationResponse::new)
                        .collect(toList())
        );
    }

}
