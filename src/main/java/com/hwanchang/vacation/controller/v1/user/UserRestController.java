package com.hwanchang.vacation.controller.v1.user;

import com.hwanchang.vacation.controller.v1.user.dto.JoinRequest;
import com.hwanchang.vacation.controller.v1.user.dto.JoinResponse;
import com.hwanchang.vacation.controller.v1.user.dto.UpdateRequest;
import com.hwanchang.vacation.controller.v1.user.dto.UserDto;
import com.hwanchang.vacation.entity.user.Role;
import com.hwanchang.vacation.entity.user.User;
import com.hwanchang.vacation.error.NotFoundException;
import com.hwanchang.vacation.security.Jwt;
import com.hwanchang.vacation.security.JwtAuthentication;
import com.hwanchang.vacation.service.user.UserService;
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
import java.util.Optional;

import static java.util.regex.Pattern.matches;
import static java.util.stream.Collectors.toList;

@Api(tags = {"사용자 APIs"})
@RequestMapping("api/v1")
@RequiredArgsConstructor
@RestController
public class UserRestController {

    private final UserService userService;

    private final Jwt jwt;

    @ApiOperation(value = "사용자 등록 (JWT 불필요)")
    @PostMapping(path = "user/join")
    public ResponseEntity<JoinResponse> join(@RequestBody JoinRequest joinRequest) {
        User user = userService.join(
                joinRequest.getEmail(),
                joinRequest.getName(),
                joinRequest.getPassword(),
                joinRequest.getPhone()
        );

        String token = user.createToken(jwt, new String[]{Role.USER.value()});
        return new ResponseEntity<>(new JoinResponse(token, new UserDto(user)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "이메일 중복확인 (JWT 불필요)")
    @PostMapping(path = "user/exists")
    public ResponseEntity<Boolean> checkEmail(
            @RequestBody @ApiParam(value = "example: {\"email\": \"test@gmail.com\"}", example = "{\"email\": \"test@gmail.com\"}") Map<String, String> request
    ) {
        return ResponseEntity.ok(
                request.get("email").equals("") || userService.findByEmail(request.get("email")).isPresent()
        );
    }

    @ApiOperation(value = "내 정보 조회")
    @GetMapping(path = "user")
    public ResponseEntity<UserDto> me(@AuthenticationPrincipal JwtAuthentication authentication) {
        return ResponseEntity.ok(
                new UserDto(
                        userService.findById(authentication.userId)
                                .orElseThrow(() -> new NotFoundException(User.class, authentication.userId))
                )
        );
    }

    @ApiOperation(value = "전체 사용자 정보 조회")
    @GetMapping(path = "users")
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(
                userService.findAll().stream()
                        .map(UserDto::new)
                        .collect(toList())
        );
    }

    @ApiOperation(value = "사용자 패스워드 변경")
    @PatchMapping("user/password")
    public ResponseEntity<UserDto> updatePassword(
            @AuthenticationPrincipal JwtAuthentication authentication,
            @RequestBody @ApiParam(value = "example: {\"password\": \"P@ssword!\"}", example = "{\"password\": \"P@ssword!\"}") Map<String, String> request
    ) {
        return ResponseEntity.ok(
                new UserDto(
                        userService.updatePassword(authentication.userId, request.get("password"))
                )
        );
    }

    @ApiOperation(value = "사용자 정보 수정")
    @PatchMapping("user")
    public ResponseEntity<UserDto> update(
            @AuthenticationPrincipal JwtAuthentication authentication,
            @RequestBody UpdateRequest updateRequest
    ) {
        return ResponseEntity.ok(
                new UserDto(
                        userService.updateUser(
                                authentication.userId,
                                updateRequest.getName(),
                                updateRequest.getPhone()
                        )
                )
        );
    }

    @ApiOperation(value = "사용자 삭제")
    @DeleteMapping("user")
    public ResponseEntity<UserDto> deleteUser(@AuthenticationPrincipal JwtAuthentication authentication) {
        return ResponseEntity.ok(
                new UserDto(
                        userService.delete(authentication.userId)
                )
        );
    }

}
