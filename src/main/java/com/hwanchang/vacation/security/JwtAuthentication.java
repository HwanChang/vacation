package com.hwanchang.vacation.security;

import static com.google.common.base.Preconditions.checkNotNull;

public class JwtAuthentication {

    public final Long userId;

    public final String email;

    JwtAuthentication(Long userId, String email) {
        checkNotNull(userId, "userId must be provided.");
        checkNotNull(email, "email must be provided.");

        this.userId = userId;
        this.email = email;
    }

}
