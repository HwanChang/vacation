package com.hwanchang.vacation.entity.confirm;

import com.hwanchang.vacation.entity.BaseTimeEntity;
import com.hwanchang.vacation.entity.application.Application;
import com.hwanchang.vacation.entity.user.User;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkNotNull;

@DynamicUpdate
@Entity
@EqualsAndHashCode(of = "confirmId", callSuper = false)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Confirm extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long confirmId;

    @Column(nullable = false)
    private boolean confirmed;

    @OneToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User user;

    public Confirm(Application application) {
        this(null, false, application, null);
    }

    @Builder
    private Confirm(Long confirmId, boolean confirmed, Application application, User user) {
        checkNotNull(application, "application must be provided.");

        this.confirmId = confirmId;
        this.confirmed = confirmed;
        this.application = application;
        this.user = user;
    }

    public void confirm(User user) {
        this.confirmed = true;
        this.user = user;
    }

}
