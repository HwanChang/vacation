package com.hwanchang.vacation.entity.vacation;

import com.hwanchang.vacation.entity.BaseTimeEntity;
import com.hwanchang.vacation.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

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
    @JoinColumn(name = "user_id")
    private User user;

    public Vacation(LocalDate date, String reason, User user) {
        this(null, date, reason, user);
    }

    public Vacation(Long vacationId, LocalDate date, String reason, User user) {
        checkNotNull(date, "date must be provided.");
        checkArgument(isNotEmpty(reason), "reason must be provided.");
        checkArgument(
                reason.length() >= 1 && reason.length() <= 500,
                "reason length must be between 1 and 500 characters."
        );
        checkNotNull(user, "user must be provided.");

        this.vacationId = vacationId;
        this.date = date;
        this.reason = reason;
        this.user = user;
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
