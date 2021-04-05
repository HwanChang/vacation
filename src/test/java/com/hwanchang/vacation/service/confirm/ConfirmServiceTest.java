package com.hwanchang.vacation.service.confirm;

import com.hwanchang.vacation.entity.application.Application;
import com.hwanchang.vacation.entity.application.State;
import com.hwanchang.vacation.entity.confirm.Confirm;
import com.hwanchang.vacation.entity.user.User;
import com.hwanchang.vacation.repository.confirm.ConfirmRepository;
import com.hwanchang.vacation.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ConfirmServiceTest {

    @InjectMocks
    private ConfirmService confirmService;

    @Mock
    private ConfirmRepository confirmRepository;

    @Mock
    private UserRepository userRepository;

    private Confirm expectedConfirm;

    @BeforeEach
    void setUp() {
        User expectedRequester = User.builder()
                .userId(1L)
                .email("hwanchang.dev@gmail.com")
                .name("박환창")
                .password("P@ssword1")
                .phone("010-1111-1111")
                .loginCount(0)
                .lastLoginAt(null)
                .build();

        Application expectedApplication = Application.builder()
                .applicationId(1L)
                .level(1)
                .approveCount(2)
                .state(State.FINISHED)
                .user(expectedRequester)
                .build();

        expectedConfirm = Confirm.builder()
                .confirmId(1L)
                .confirmed(false)
                .application(expectedApplication)
                .user(null)
                .build();
    }

    @Test
    void 처리_신청서_조회() {
        //given
        given(confirmRepository.findAll()).willReturn(singletonList(expectedConfirm));

        //when
        List<Confirm> confirmList = confirmService.findAll();

        //then
        assertThat(confirmList).isNotNull();
        assertThat(confirmList.size()).isEqualTo(1);
        assertThat(confirmList.get(0).getConfirmId()).isEqualTo(1L);
        assertThat(confirmList.get(0).isConfirmed()).isEqualTo(false);
        assertThat(confirmList.get(0).getApplication().getState()).isEqualTo(State.FINISHED);
    }

    @Test
    void 신청서_처리() {
        //given
        User manager = User.builder()
                .userId(2L)
                .email("ghksckd219@gmail.com")
                .name("박환창")
                .password("P@ssword2")
                .phone("010-2222-2222")
                .loginCount(0)
                .lastLoginAt(null)
                .build();
        given(confirmRepository.findByApplicationApplicationIdAndApplicationStateAndConfirmedIsFalse(anyLong(), any(State.class))).willReturn(Optional.of(expectedConfirm));
        given(userRepository.findById(anyLong())).willReturn(Optional.of(manager));

        //when
        Confirm confirm = confirmService.confirm(1L, 2L).orElse(null);

        //then
        assertThat(confirm).isNotNull();
        assertThat(confirm.isConfirmed()).isEqualTo(true);
        assertThat(confirm.getUser()).isNotNull();
        assertThat(confirm.getUser().getUserId()).isEqualTo(2L);
    }

}
