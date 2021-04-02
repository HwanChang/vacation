package com.hwanchang.vacation.repository.confirm;

import com.hwanchang.vacation.entity.application.State;
import com.hwanchang.vacation.entity.confirm.Confirm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmRepository extends JpaRepository<Confirm, Long> {

    Optional<Confirm> findByApplicationApplicationIdAndApplicationStateAndConfirmedIsFalse(Long applicationId, State state);
}
