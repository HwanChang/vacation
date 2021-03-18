package com.hwanchang.vacation.repository.approve;

import com.hwanchang.vacation.entity.approve.Approve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApproveRepository extends JpaRepository<Approve, Long> {

    Optional<Approve> findByApplicationApplicationIdAndUserUserId(Long applicationId, Long userId);

    List<Approve> findAllByUserUserId(Long userId);

}
