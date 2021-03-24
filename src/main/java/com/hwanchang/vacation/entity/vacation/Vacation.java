package com.hwanchang.vacation.entity.vacation;

import com.hwanchang.vacation.entity.BaseTimeEntity;
import com.hwanchang.vacation.entity.application.Application;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@DynamicUpdate
@Entity
@EqualsAndHashCode(of = "vacationId", callSuper = false)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Vacation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vacationId;

    @Column(nullable = false, unique = true)
    private LocalDate date;

    @Column(nullable = false, length = 500)
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private Application application;

    public Vacation(LocalDate date, String reason, Application application) {
        this(null, date, reason, application);
    }

    public Vacation(Long vacationId, LocalDate date, String reason, Application application) {
        checkNotNull(date, "date must be provided.");
        checkArgument(isNotEmpty(reason), "reason must be provided.");
        checkArgument(
                reason.length() >= 1 && reason.length() <= 500,
                "reason length must be between 1 and 500 characters."
        );
        checkNotNull(application, "application must be provided.");

        this.vacationId = vacationId;
        this.date = date;
        this.reason = reason;
        this.application = application;
    }

    public void updateReason(String reason) {
        checkArgument(isNotEmpty(reason), "reason must be provided.");
        checkArgument(
                reason.length() >= 1 && reason.length() <= 500,
                "reason length must be between 1 and 500 characters."
        );

        this.reason = reason;
    }

}
