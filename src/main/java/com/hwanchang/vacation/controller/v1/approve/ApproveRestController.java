package com.hwanchang.vacation.controller.v1.approve;

import com.hwanchang.vacation.controller.v1.application.dto.ApplicationDto;
import com.hwanchang.vacation.entity.application.Application;
import com.hwanchang.vacation.error.ApplicationNotFoundException;
import com.hwanchang.vacation.security.JwtAuthentication;
import com.hwanchang.vacation.service.approve.ApproveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"결재 APIs"})
@RequestMapping("api/v1")
@RequiredArgsConstructor
@RestController
public class ApproveRestController {

    private final ApproveService approveService;

    @ApiOperation(value = "신청서 결재")
    @PatchMapping(path = "application/{applicationId}/approve")
    public ResponseEntity<ApplicationDto> approve(
            @AuthenticationPrincipal JwtAuthentication authentication,
            @PathVariable Long applicationId
    ) {
        return ResponseEntity.ok(
                approveService.approve(applicationId, authentication.userId)
                        .map(ApplicationDto::new)
                        .orElseThrow(() -> new ApplicationNotFoundException(Application.class, applicationId))
        );
    }

}
