package com.hwanchang.vacation.security;

import com.hwanchang.vacation.entity.user.User;
import lombok.Getter;
import lombok.ToString;

import static com.google.common.base.Preconditions.checkNotNull;

@Getter
@ToString
public class AuthenticationResult {

    private final String token;

    private final User user;

    public AuthenticationResult(String token, User user) {
        checkNotNull(token, "token must be provided.");
        checkNotNull(user, "user must be provided.");

        this.token = token;
        this.user = user;
    }

}
