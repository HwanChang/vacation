package com.hwanchang.vacation.service.vacation;

import com.hwanchang.vacation.entity.user.User;
import com.hwanchang.vacation.entity.vacation.Vacation;
import com.hwanchang.vacation.error.NotFoundException;
import com.hwanchang.vacation.repository.user.UserRepository;
import com.hwanchang.vacation.repository.vacation.VacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationService {

    private final VacationRepository vacationRepository;

    private final UserRepository userRepository;

    @Transactional
    public Vacation add(Long userId, LocalDate date, String reason) {
        return userRepository.findById(userId)
                .map(user -> update(new Vacation(date, reason, user)))
                .orElseThrow(() -> new NotFoundException(User.class, userId));
    }

    public List<Vacation> findByUserUserId(Long userId) {
        return vacationRepository.findAllByUserUserId(userId);
    }

    @Transactional
    public Vacation updateReason(Long userId, Long vacationId, String reason) {
        return vacationRepository.findByUserUserIdAndVacationId(userId, vacationId)
                .map(vacation -> {
                    vacation.updateReason(reason);
                    return update(vacation);
                }).orElseThrow(() -> new NotFoundException(Vacation.class, vacationId));
    }

    @Transactional
    public Vacation delete(Long userId, Long vacationId) {
        return vacationRepository.findByUserUserIdAndVacationId(userId, vacationId)
                .map(vacation -> {
                    vacationRepository.delete(vacation);
                    return vacation;
                }).orElseThrow(() -> new NotFoundException(Vacation.class, vacationId));
    }

    private Vacation update(Vacation vacation) {
        return vacationRepository.save(vacation);
    }

}
