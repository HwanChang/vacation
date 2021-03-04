package com.hwanchang.vacation.repository.vacation;

import com.hwanchang.vacation.entity.vacation.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VacationRepository extends JpaRepository<Vacation, Long> {

    List<Vacation> findAllByUserUserId(Long userId);

    Optional<Vacation> findByUserUserIdAndVacationId(Long userId, Long vacationId);

}
