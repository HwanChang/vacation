package com.hwanchang.vacation.controller.v1.confirm;

import com.hwanchang.vacation.controller.v1.confirm.dto.ConfirmDto;
import com.hwanchang.vacation.entity.confirm.Confirm;
import com.hwanchang.vacation.error.NotFoundException;
import com.hwanchang.vacation.security.JwtAuthentication;
import com.hwanchang.vacation.service.confirm.ConfirmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"처리 APIs"})
@RequestMapping("api/v1")
@RequiredArgsConstructor
@RestController
public class ConfirmController {

    private final ConfirmService confirmService;

    @ApiOperation(value = "신청서 처리")
    @PatchMapping(path = "application/{applicationId}/confirm")
    public ResponseEntity<ConfirmDto> confirm(
            @AuthenticationPrincipal JwtAuthentication authentication,
            @PathVariable Long applicationId
    ) {
        return ResponseEntity.ok(
                confirmService.confirm(applicationId, authentication.userId)
                        .map(ConfirmDto::new)
                        .orElseThrow(() -> new NotFoundException(Confirm.class, applicationId, authentication.userId))
        );
    }

}
