package com.hwanchang.vacation.controller.admin;

import com.hwanchang.vacation.controller.admin.dto.RoleRequest;
import com.hwanchang.vacation.controller.v1.user.dto.UserDto;
import com.hwanchang.vacation.service.admin.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"관리자 APIs"})
@RequestMapping("api/admin")
@RequiredArgsConstructor
@RestController
public class AdminRestController {

    private final AdminService adminService;

    @ApiOperation(value = "사용자 권한 변경")
    @PostMapping(path = "role")
    public ResponseEntity<UserDto> changeRole(
            @RequestBody RoleRequest roleRequest
    ) {
        return ResponseEntity.ok(
                new UserDto(
                        adminService.changeRole(roleRequest.getUserId(), roleRequest.getRoles())
                )
        );
    }

}
