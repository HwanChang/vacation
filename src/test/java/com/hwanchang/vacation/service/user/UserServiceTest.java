package com.hwanchang.vacation.service.user;

import com.hwanchang.vacation.entity.user.User;
import com.hwanchang.vacation.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private String email;

    private String name;

    private String password;

    private String phone;

    private User expectedUser;

    @BeforeEach
    void setUp() {
        email = "hwanchang.dev@gmail.com";
        name = "박환창";
        password = "P@ssword1";
        phone = "010-0000-0000";
        expectedUser = User.builder()
                .userId(1L)
                .email(email)
                .name(name)
                .password(new BCryptPasswordEncoder().encode(password))
                .phone(phone)
                .loginCount(0)
                .lastLoginAt(null)
                .build();
    }


    @Test
    @Rollback(value = false)
    void 사용자_회원가입() {
        //given
        given(userRepository.save(any(User.class))).willReturn(expectedUser);
        ReflectionTestUtils.setField(userService, "passwordEncoder", new BCryptPasswordEncoder());

        //when
        User user = userService.join(email, name, password, phone);

        //then
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getName()).contains(name);
        assertThat(user.getPhone()).contains(phone);
        assertThat(user.getLoginCount()).isEqualTo(0);
        assertThat(user.getLastLoginAt()).isNull();
    }

    @Test
    void 사용자_로그인() {
        //given
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(expectedUser));
        ReflectionTestUtils.setField(userService, "passwordEncoder", new BCryptPasswordEncoder());
        LocalDateTime now = LocalDateTime.now();

        //when
        User user = userService.login(email, password);

        //then
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getName()).contains(name);
        assertThat(user.getPhone()).contains(phone);
        assertThat(user.getLoginCount()).isEqualTo(1);
        assertThat(user.getLastLoginAt()).isAfter(now);
    }

    @Test
    void 이메일로_사용자_조회() {
        //given
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(expectedUser));

        //when
        User user = userService.findByEmail(email).orElse(null);

        //then
        assertThat(user).isNotNull();
        assertThat(user.getUserId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getPhone()).contains(phone);
    }

    @Test
    void userId로_사용자_조회() {
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.of(expectedUser));

        //when
        User user = userService.findById(1L).orElse(null);

        //then
        assertThat(user).isNotNull();
        assertThat(user.getUserId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getPhone()).contains(phone);
    }

    @Test
    void 사용자_정보_수정() {
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.of(expectedUser));
        given(userRepository.save(any(User.class))).willReturn(expectedUser);

        //when
        User user = userService.updateUser(1L, "박레이", "010-1111-1111");

        //then
        assertThat(user.getEmail()).isEqualTo(expectedUser.getEmail());
        assertThat(user.getName()).isEqualTo("박레이");
        assertThat(user.getPhone()).contains("010-1111-1111");
    }

    @Test
    void 사용자_삭제() {
        //given
        doNothing().when(userRepository).delete(any(User.class));
        given(userRepository.findById(anyLong())).willReturn(Optional.of(expectedUser));

        //when
        userService.delete(expectedUser.getUserId());

        //then
        verify(userRepository).delete(eq(expectedUser));
    }

}
