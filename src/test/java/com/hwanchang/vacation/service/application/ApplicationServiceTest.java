package com.hwanchang.vacation.service.application;

import com.hwanchang.vacation.controller.v1.approve.dto.ApproveRequest;
import com.hwanchang.vacation.controller.v1.vacation.dto.VacationRequest;
import com.hwanchang.vacation.entity.application.Application;
import com.hwanchang.vacation.entity.application.State;
import com.hwanchang.vacation.entity.approve.Approve;
import com.hwanchang.vacation.entity.user.User;
import com.hwanchang.vacation.entity.vacation.Vacation;
import com.hwanchang.vacation.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

    User test01 = new User("test01@gmail.com", "test01", "P@ssword1", "010-1111-1111");
    User test02 = new User("test02@gmail.com", "test02", "P@ssword2", "010-2222-2222");
    User test03 = new User("test03@gmail.com", "test03", "P@ssword3", "010-3333-3333");

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
        User requestUser = userService.join(test01.getEmail(), test01.getName(), test01.getPassword(), test01.getPhone());
        User firstApprovalUser = userService.join(test02.getEmail(), test02.getName(), test02.getPassword(), test02.getPhone());
        User secondApprovalUser = userService.join(test03.getEmail(), test03.getName(), test03.getPassword(), test03.getPhone());

        log.info("RequestUser: {}", requestUser);
        log.info("FirstApprovalUser: {}", firstApprovalUser);
        log.info("SecondApprovalUser: {}", secondApprovalUser);

        //when
        Application application = applicationService.save(1L, vacationRequest, approveRequests);

        //then
        assertThat(application).isNotNull();
        assertThat(application.getLevel()).isEqualTo(1);
        assertThat(application.getApproveCount()).isEqualTo(2);
        assertThat(application.getState()).isEqualTo(State.RUNNING);
        assertThat(application.getUser()).isEqualTo(requestUser);
        assertThat(application.getVacations().stream().map(Vacation::getDate)).containsAll(vacationDates);
        assertThat(application.getVacations().stream().map(Vacation::getReason)).contains(reason);
        assertThat(application.getApproves()).hasSize(2);
        assertThat(application.getApproves().stream().map(Approve::getUser)).contains(firstApprovalUser);
        assertThat(application.getApproves().stream().map(Approve::getUser)).contains(secondApprovalUser);

        log.info("Application: {}", application);
    }

    @Test
    @Transactional
    @Order(2)
    void 내_휴가_전체조회() {
        //given
        User requestUser = userService.findById(1L).orElse(null);
        User firstApprovalUser = userService.findById(2L).orElse(null);
        User secondApprovalUser = userService.findById(3L).orElse(null);

        log.info("RequestUser: {}", requestUser);
        log.info("FirstApprovalUser: {}", firstApprovalUser);
        log.info("SecondApprovalUser: {}", secondApprovalUser);

        //when
        List<Application> applications = applicationService.findAll(1L);

        //then
        assertThat(applications).hasSizeGreaterThan(0);
        for (Application application : applications) {
            assertThat(application.getLevel()).isEqualTo(1);
            assertThat(application.getApproveCount()).isEqualTo(2);
            assertThat(application.getState()).isEqualTo(State.RUNNING);
            assertThat(application.getUser()).isEqualTo(requestUser);
            assertThat(application.getVacations().stream().map(Vacation::getDate)).containsAll(vacationDates);
            assertThat(application.getVacations().stream().map(Vacation::getReason)).contains(reason);
            assertThat(application.getApproves()).hasSize(2);
            assertThat(application.getApproves().stream().map(Approve::getUser)).contains(firstApprovalUser);
            assertThat(application.getApproves().stream().map(Approve::getUser)).contains(secondApprovalUser);

            log.info("Application: {}", application);
        }
    }

}
