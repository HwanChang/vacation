package com.hwanchang.vacation.security;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import static java.lang.Thread.sleep;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JwtTest {

    private Jwt jwt;

    @BeforeAll
    void setUp() {
        String issuer = "vacation";
        String clientSecret = "366e66f3-166b-4c0d-8502-f35cf9b85d6a";
        int expirySeconds = 10;

        jwt = new Jwt(issuer, clientSecret, expirySeconds);
    }

    @Test
    void JWT_생성_후_복호화() {
        Jwt.Claims claims = Jwt.Claims.of(1L, "hwanchang.dev@gmail.com", new String[]{"ROLE_USER"});
        String encodedJWT = jwt.newToken(claims);
        log.info("encodedJWT: {}", encodedJWT);

        Jwt.Claims decodedJWT = jwt.verify(encodedJWT);
        log.info("decodedJWT: {}", decodedJWT);

        assertThat(claims.userId, is(decodedJWT.userId));
        assertThat(claims.email, is(decodedJWT.email));
        assertArrayEquals(claims.roles, decodedJWT.roles);
    }

    @Test
    void JWT_갱신() throws Exception {
        if (jwt.getExpirationTime() > 0) {
            Jwt.Claims claims = Jwt.Claims.of(1L, "hwanchang.dev@gmail.com", new String[]{"ROLE_USER"});
            String encodedJWT = jwt.newToken(claims);
            log.info("encodedJWT: {}", encodedJWT);

            // 1초 대기 후 토큰 갱신
            sleep(1_000L);

            String encodedRefreshedJWT = jwt.refreshToken(encodedJWT);
            log.info("encodedRefreshedJWT: {}", encodedRefreshedJWT);

            assertThat(encodedJWT, not(encodedRefreshedJWT));

            Jwt.Claims oldJwt = jwt.verify(encodedJWT);
            Jwt.Claims newJwt = jwt.verify(encodedRefreshedJWT);

            long oldExp = oldJwt.exp();
            long newExp = newJwt.exp();

            // 1초 대기 후 토큰 갱신했기 때문에 만료시간 차이는 1초 이상
            assertThat(newExp >= oldExp + 1_000L, is(true));

            log.info("oldJwt: {}", oldJwt);
            log.info("newJwt: {}", newJwt);
        }
    }

}
