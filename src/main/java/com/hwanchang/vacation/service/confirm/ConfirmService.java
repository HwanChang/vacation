package com.hwanchang.vacation.service.confirm;

import com.hwanchang.vacation.entity.application.State;
import com.hwanchang.vacation.entity.confirm.Confirm;
import com.hwanchang.vacation.entity.user.User;
import com.hwanchang.vacation.error.NotFoundException;
import com.hwanchang.vacation.repository.confirm.ConfirmRepository;
import com.hwanchang.vacation.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmService {

    private final ConfirmRepository confirmRepository;

    private final UserRepository userRepository;

    @Transactional
    public Optional<Confirm> confirm(Long applicationId, Long userId) {
        return confirmRepository.findByApplicationApplicationIdAndApplicationStateAndConfirmedIsFalse(applicationId, State.FINISHED)
                .map(confirm -> {
                    confirm.confirm(
                            userRepository.findById(userId)
                                    .orElseThrow(() -> new NotFoundException(User.class, userId))
                    );
                    return confirm;
                });
    }

}
