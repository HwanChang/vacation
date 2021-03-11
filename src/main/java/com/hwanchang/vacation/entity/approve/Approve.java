package com.hwanchang.vacation.entity.approve;

import com.hwanchang.vacation.entity.BaseTimeEntity;
import com.hwanchang.vacation.entity.application.Application;
import com.hwanchang.vacation.entity.user.User;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkNotNull;

@DynamicUpdate
@Entity
@EqualsAndHashCode(of = "approveId", callSuper = false)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Approve extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long approveId;

    @Column(nullable = false)
    private boolean isApprove;

    @Column(nullable = false)
    private int level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private Application application;

    public Approve(int level, User user, Application application) {
        this(null, false, level, user, application);
    }

    public Approve(Long approveId, boolean isApprove, int level, User user, Application application) {
        checkNotNull(user, "user must be provided.");
        checkNotNull(application, "application must be provided.");

        this.approveId = approveId;
        this.isApprove = isApprove;
        this.level = level;
        this.user = user;
        this.application = application;
    }

    public void approve() {
        this.isApprove = true;
    }

}
