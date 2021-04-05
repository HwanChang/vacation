package com.hwanchang.vacation.service.application;

import com.hwanchang.vacation.controller.v1.approve.dto.ApproveRequest;
import com.hwanchang.vacation.controller.v1.vacation.dto.VacationRequest;
import com.hwanchang.vacation.entity.application.Application;
import com.hwanchang.vacation.entity.application.State;
import com.hwanchang.vacation.entity.user.User;
import com.hwanchang.vacation.repository.appclication.ApplicationRepository;
import com.hwanchang.vacation.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @InjectMocks
    private ApplicationService applicationService;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private UserRepository userRepository;

    private VacationRequest vacationRequest = new VacationRequest(
            List.of(LocalDate.of(2021, 3, 26), LocalDate.of(2021, 3, 27)),
            "개인 사유"
    );

    private List<ApproveRequest> approveRequests = new ArrayList<>(
            List.of(new ApproveRequest(2L, 1), new ApproveRequest(3L, 2))
    );

    private User expectedUser;

    private Application expectedApplication;

    @BeforeEach
    void setUp() {
        expectedUser = User.builder()
                .userId(1L)
                .email("hwanchang.dev@gmail.com")
                .name("박환창")
                .password("P@ssword1")
                .phone("010-0000-0000")
                .loginCount(0)
                .lastLoginAt(null)
                .build();

        expectedApplication = Application.builder()
                .applicationId(1L)
                .level(1)
                .approveCount(2)
                .state(State.RUNNING)
                .user(expectedUser)
                .build();
    }

    @Test
    void 휴가_신청_등록() {
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.of(expectedUser));
        given(applicationRepository.save(any(Application.class))).willReturn(expectedApplication);

        //when
        Application application = applicationService.save(1L, vacationRequest, approveRequests);

        //then
        assertThat(application).isNotNull();
        assertThat(application.getApplicationId()).isEqualTo(1L);
        assertThat(application.getLevel()).isEqualTo(1);
        assertThat(application.getApproveCount()).isEqualTo(2);
        assertThat(application.getState()).isEqualTo(State.RUNNING);
    }

    @Test
    void 내_휴가_전체조회() {
        //given
        given(applicationRepository.findAllByUserUserId(1L)).willReturn(singletonList(expectedApplication));

        //when
        List<Application> applications = applicationService.findAll(1L);

        //then
        assertThat(applications).isNotNull();
        assertThat(applications.size()).isEqualTo(1);
        assertThat(applications.get(0).getApplicationId()).isEqualTo(1L);
        assertThat(applications.get(0).getLevel()).isEqualTo(1);
        assertThat(applications.get(0).getApproveCount()).isEqualTo(2);
        assertThat(applications.get(0).getState()).isEqualTo(State.RUNNING);
    }

}
