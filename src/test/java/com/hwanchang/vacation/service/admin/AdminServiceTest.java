package com.hwanchang.vacation.service.admin;

import com.hwanchang.vacation.entity.user.Role;
import com.hwanchang.vacation.entity.user.User;
import com.hwanchang.vacation.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private UserRepository userRepository;

    private List<Role> roles = List.of(Role.USER);

    private User expectedUser;

    @BeforeEach
    void setUp() {
        expectedUser = User.builder()
                .userId(1L)
                .email("hwanchang.dev@gmail.com")
                .name("박환창")
                .password(new BCryptPasswordEncoder().encode("P@ssword1"))
                .roles(roles)
                .phone("010-0000-0000")
                .loginCount(0)
                .lastLoginAt(null)
                .build();
    }

    @Test
    void 사용자_권한_변경() {
        //given
        List<Role> expectedRoles = List.of(Role.USER, Role.MANAGER, Role.ADMIN);
        given(userRepository.findById(anyLong())).willReturn(Optional.of(expectedUser));

        //when
        User user = adminService.changeRole(1L, expectedRoles);

        //then
        assertThat(user).isNotNull();
        assertThat(user.getRoles()).containsAll(expectedRoles);
    }

}
