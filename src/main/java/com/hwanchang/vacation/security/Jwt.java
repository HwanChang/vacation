package com.hwanchang.vacation.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Getter
public class Jwt {

    private final String issuer;

    private final String secret;

    private final int expirationTime;

    private final Algorithm algorithm;

    private final JWTVerifier jwtVerifier;

    public Jwt(String issuer, String secret, int expirationTime) {
        this.issuer = issuer;
        this.secret = secret;
        this.expirationTime = expirationTime;
        this.algorithm = Algorithm.HMAC512(secret);
        this.jwtVerifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();
    }

    public String newToken(Claims claims) {
        Date now = new Date();
        JWTCreator.Builder builder = JWT.create();
        builder.withIssuer(issuer);
        builder.withIssuedAt(now);
        if (expirationTime > 0) {
            builder.withExpiresAt(new Date(now.getTime() + expirationTime * 1_000L));
        }
        builder.withClaim("userId", claims.userId);
        builder.withClaim("email", claims.email);
        builder.withArrayClaim("roles", claims.roles);
        return builder.sign(algorithm);
    }

    public String refreshToken(String token) throws JWTVerificationException {
        Claims claims = verify(token);
        claims.eraseIat();
        claims.eraseExp();
        return newToken(claims);
    }

    public Claims verify(String token) throws JWTVerificationException {
        return new Claims(jwtVerifier.verify(token));
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    static public class Claims {
        Long userId;
        String email;
        String[] roles;
        Date iat;
        Date exp;

        Claims(DecodedJWT decodedJWT) {
            Claim userId = decodedJWT.getClaim("userId");
            if (!userId.isNull())
                this.userId = userId.asLong();
            Claim email = decodedJWT.getClaim("email");
            if (!email.isNull())
                this.email = email.asString();
            Claim roles = decodedJWT.getClaim("roles");
            if (!roles.isNull())
                this.roles = roles.asArray(String.class);
            this.iat = decodedJWT.getIssuedAt();
            this.exp = decodedJWT.getExpiresAt();
        }

        public static Claims of(long userId, String email, String[] roles) {
            Claims claims = new Claims();
            claims.userId = userId;
            claims.email = email;
            claims.roles = roles;
            return claims;
        }

        long exp() {
            return exp != null ? exp.getTime() : -1;
        }

        void eraseIat() {
            iat = null;
        }

        void eraseExp() {
            exp = null;
        }
    }

}
