package com.hwanchang.vacation.service.user;

import com.hwanchang.vacation.entity.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    private String email;

    private String name;

    private String password;

    private String phone;

    @BeforeAll
    void setUp() {
        name = "박환창";
        email = "hwanchang.dev@gmail.com";
        password = "P@ssword1";
        phone = "010-0000-0000";
    }

    @Test
    @Order(1)
    void 사용자_회원가입() {
        User user = userService.join(email, name, password, phone);
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getName()).contains(name);
        assertThat(user.getPhone()).contains(phone);
        assertThat(user.getLoginCount()).isEqualTo(0);
        assertThat(user.getLastLoginAt()).isNull();

        log.info("User: {}", user);
    }

    @Test
    @Order(2)
    void 사용자_로그인() {
        LocalDateTime now = LocalDateTime.now();
        User user = userService.login(email, password);
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getName()).contains(name);
        assertThat(user.getPhone()).contains(phone);
        assertThat(user.getLoginCount()).isEqualTo(1);
        assertThat(user.getLastLoginAt()).isAfter(now);

        log.info("Login User: {}", user);
    }

    @Test
    @Order(3)
    void 이메일로_사용자_조회() {
        User user = userService.findByEmail(email).orElse(null);
        assertThat(user).isNotNull();
        assertThat(user.getUserId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getPhone()).contains(phone);

        log.info("Found by {}: {}", email, user);
    }

    @Test
    @Order(4)
    void userId로_사용자_조회() {
        User user = userService.findById(1L).orElse(null);
        assertThat(user).isNotNull();
        assertThat(user.getUserId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getPhone()).contains(phone);

        log.info("Found by userId {}: {}", 1L, user);
    }

    @Test
    @Order(5)
    void 사용자_정보_수정() {
        User user = userService.findById(1L).orElse(null);
        assertThat(user).isNotNull();
        assertThat(user.getUserId()).isEqualTo(1L);

        log.info("Found by userId Before Update {}: {}", 1L, user);

        User updatedUser = userService.updateUser(user.getUserId(), "박레이", "010-1111-1111");
        assertThat(user.getEmail()).isEqualTo(updatedUser.getEmail());
        assertThat(user.getName()).isNotEqualTo(updatedUser.getName());
        assertThat(user.getPhone()).doesNotContain(updatedUser.getPhone());

        log.info("Found by userId After Update {}: {}", 1L, updatedUser);
    }

    @Test
    @Order(6)
    void 사용자_삭제() {
        User user = userService.findById(1L).orElse(null);
        assertThat(user).isNotNull();
        assertThat(user.getUserId()).isEqualTo(1L);

        log.info("Found by userId {}: {}", 1L, user);

        User deletedUser = userService.delete(user.getUserId());
        assertThat(userService.findById(deletedUser.getUserId()).isPresent()).isFalse();

        log.info("Found by userId Deleted User {}: {}", 1L, deletedUser);
    }

}
