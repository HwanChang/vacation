package com.hwanchang.vacation.service.application;

import com.hwanchang.vacation.controller.v1.approve.dto.ApproveRequest;
import com.hwanchang.vacation.controller.v1.vacation.dto.VacationRequest;
import com.hwanchang.vacation.entity.application.Application;
import com.hwanchang.vacation.entity.approve.Approve;
import com.hwanchang.vacation.entity.user.User;
import com.hwanchang.vacation.entity.vacation.Vacation;
import com.hwanchang.vacation.error.NotFoundException;
import com.hwanchang.vacation.repository.appclication.ApplicationRepository;
import com.hwanchang.vacation.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    private final UserRepository userRepository;

    @Transactional
    public Application save(Long userId, VacationRequest vacationRequest, List<ApproveRequest> approveRequests) {
        return userRepository.findById(userId)
                .map(user -> {
                    Application application = new Application(user);
                    application.addVacations(vacationRequest.getDates().stream()
                            .map(date -> new Vacation(date, vacationRequest.getReason(), application)).collect(toList()));
                    application.addApproves(approveRequests.stream()
                            .map(approveRequest -> new Approve(
                                            approveRequest.getLevel(),
                                            userRepository.findById(approveRequest.getApproverId())
                                                    .orElseThrow(() -> new NotFoundException(User.class, approveRequest.getApproverId())),
                                            application
                                    )
                            ).collect(toList()));
                    return applicationRepository.save(application);
                }).orElseThrow(() -> new NotFoundException(User.class, userId));
    }

}
