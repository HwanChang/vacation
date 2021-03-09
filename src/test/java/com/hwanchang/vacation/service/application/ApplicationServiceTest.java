package com.hwanchang.vacation.service.application;

import com.hwanchang.vacation.controller.v1.approve.dto.ApproveRequest;
import com.hwanchang.vacation.controller.v1.vacation.dto.VacationRequest;
import com.hwanchang.vacation.entity.application.Application;
import com.hwanchang.vacation.entity.approve.Approve;
import com.hwanchang.vacation.entity.user.User;
import com.hwanchang.vacation.entity.vacation.Vacation;
import com.hwanchang.vacation.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationServiceTest {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserService userService;

    List<LocalDate> vacationDates;

    String reason;

    VacationRequest vacationRequest = new VacationRequest();

    List<ApproveRequest> approveRequests = new ArrayList<>();

    @BeforeAll
    void setUp() {
        vacationDates = List.of(LocalDate.of(2021, 3, 9), LocalDate.of(2021, 3, 10));
        reason = "개인 사유";
        vacationRequest.setDates(vacationDates);
        vacationRequest.setReason(reason);
        ApproveRequest firstApproval = new ApproveRequest();
        firstApproval.setApproverId(2L);
        firstApproval.setLevel(1);
        ApproveRequest secondApproval = new ApproveRequest();
        secondApproval.setApproverId(3L);
        secondApproval.setLevel(2);
        approveRequests.addAll(List.of(firstApproval, secondApproval));
    }

    @Test
    @Order(1)
    void 휴가_신청_등록() {
        //given
        User requestUser = userService.join("test01@gmail.com", "test01", "P@ssword1", "010-1111-1111");
        User firstApprovalUser = userService.join("test02@gmail.com", "test02", "P@ssword2", "010-2222-2222");
        User secondApprovalUser = userService.join("test03@gmail.com", "test03", "P@ssword3", "010-3333-3333");

        log.info("RequestUser: {}", requestUser);
        log.info("FirstApprovalUser: {}", firstApprovalUser);
        log.info("SecondApprovalUser: {}", secondApprovalUser);

        //when
        Application application = applicationService.save(1L, vacationRequest, approveRequests);

        //then
        assertThat(application).isNotNull();
        assertThat(application.getLevel()).isEqualTo(1);
        assertThat(application.getUser()).isEqualTo(requestUser);
        assertThat(application.getVacations().stream().map(Vacation::getDate)).containsAll(vacationDates);
        assertThat(application.getVacations().stream().map(Vacation::getReason)).contains(reason);
        assertThat(application.getApproves()).hasSize(2);
        assertThat(application.getApproves().stream().map(Approve::getUser)).contains(firstApprovalUser);
        assertThat(application.getApproves().stream().map(Approve::getUser)).contains(secondApprovalUser);

        log.info("Application: {}", application);
    }

}
