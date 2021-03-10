package com.hwanchang.vacation.repository.appclication;

import com.hwanchang.vacation.entity.application.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findAllByUserUserId(Long userId);

}
