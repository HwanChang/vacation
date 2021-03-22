package com.hwanchang.vacation.service.approve;

import com.hwanchang.vacation.entity.application.Application;
import com.hwanchang.vacation.entity.application.State;
import com.hwanchang.vacation.error.NotFoundException;
import com.hwanchang.vacation.repository.appclication.ApplicationRepository;
import com.hwanchang.vacation.repository.approve.ApproveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ApproveService {

    private final ApproveRepository approveRepository;

    private final ApplicationRepository applicationRepository;

    @Transactional
    public Optional<Application> approve(Long applicationId, Long userId) {
        return approveRepository.findByApplicationApplicationIdAndUserUserIdAndApplicationState(applicationId, userId, State.RUNNING)
                .map(approve -> {
                    approve.approve();
                    return applicationRepository.findById(applicationId)
                            .map(application -> {
                                if (application.getApproveCount() == approve.getLevel()) {
                                    application.finish();
                                } else {
                                    application.approveApplication();
                                }
                                return application;
                            }).orElseThrow(() -> new NotFoundException(Application.class, applicationId));
                });
    }

}
