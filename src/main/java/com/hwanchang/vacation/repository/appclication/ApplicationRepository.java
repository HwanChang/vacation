package com.hwanchang.vacation.repository.appclication;

import com.hwanchang.vacation.entity.application.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
