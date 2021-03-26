package com.hwanchang.vacation.service.approve;

import com.hwanchang.vacation.entity.application.Application;
import com.hwanchang.vacation.entity.application.State;
import com.hwanchang.vacation.entity.approve.Approve;
import com.hwanchang.vacation.entity.user.User;
import com.hwanchang.vacation.repository.appclication.ApplicationRepository;
import com.hwanchang.vacation.repository.approve.ApproveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ApproveServiceTest {

    @InjectMocks
    private ApproveService approveService;

    @Mock
    private ApproveRepository approveRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    User expectedRequester;

    User expectedApprover;

    Application expectedApplication;

    @BeforeEach
    void setUp() {
        expectedRequester = User.builder()
                .userId(1L)
                .email("hwanchang.dev@gmail.com")
                .name("박환창")
                .password("P@ssword1")
                .phone("010-1111-1111")
                .loginCount(0)
                .lastLoginAt(null)
                .build();

        expectedApprover = User.builder()
                .userId(2L)
                .email("ghksckd219@gmail.com")
                .name("박환창")
                .password("P@ssword2")
                .phone("010-2222-2222")
                .loginCount(0)
                .lastLoginAt(null)
                .build();

        expectedApplication = Application.builder()
                .applicationId(1L)
                .level(1)
                .approveCount(2)
                .state(State.RUNNING)
                .user(expectedRequester)
                .build();
    }

    @Test
    void 신청서_결재() {
        //given
        Approve expectedApprove = Approve.builder()
                .approveId(1L)
                .approved(false)
                .level(1)
                .user(expectedApprover)
                .application(expectedApplication)
                .build();
        expectedApplication.addApproves(singletonList(expectedApprove));
        given(approveRepository.findByApplicationApplicationIdAndUserUserIdAndApplicationStateAndApprovedIsFalse(anyLong(), anyLong(), any(State.class)))
                .willReturn(Optional.of(expectedApprove));
        given(applicationRepository.findById(anyLong())).willReturn(Optional.of(expectedApplication));

        //when
        Application application = approveService.approve(1L, 2L).orElse(null);

        //then
        assertThat(application).isNotNull();
        assertThat(application.getLevel()).isEqualTo(2);
        assertThat(application.getApproveCount()).isEqualTo(2);
        assertThat(application.getState()).isEqualTo(State.RUNNING);
        assertThat(application.getApproves().get(0).isApproved()).isEqualTo(true);
    }

    @Test
    void 신청서_최종_결재() {
        //given
        expectedApplication.approveApplication();
        Approve expectedApprove = Approve.builder()
                .approveId(1L)
                .approved(false)
                .level(2)
                .user(expectedApprover)
                .application(expectedApplication)
                .build();
        expectedApplication.addApproves(singletonList(expectedApprove));
        given(approveRepository.findByApplicationApplicationIdAndUserUserIdAndApplicationStateAndApprovedIsFalse(anyLong(), anyLong(), any(State.class)))
                .willReturn(Optional.of(expectedApprove));
        given(applicationRepository.findById(anyLong())).willReturn(Optional.of(expectedApplication));

        //when
        Application application = approveService.approve(1L, 3L).orElse(null);

        //then
        assertThat(application).isNotNull();
        assertThat(application.getLevel()).isEqualTo(2);
        assertThat(application.getApproveCount()).isEqualTo(2);
        assertThat(application.getState()).isEqualTo(State.FINISHED);
        assertThat(application.getApproves().get(0).isApproved()).isEqualTo(true);
    }

}
