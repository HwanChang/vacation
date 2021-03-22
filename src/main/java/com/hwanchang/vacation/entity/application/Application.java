package com.hwanchang.vacation.entity.application;

import com.hwanchang.vacation.entity.BaseTimeEntity;
import com.hwanchang.vacation.entity.approve.Approve;
import com.hwanchang.vacation.entity.user.User;
import com.hwanchang.vacation.entity.vacation.Vacation;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@DynamicUpdate
@Entity
@EqualsAndHashCode(of = "applicationId", callSuper = false)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"vacations", "approves"})
public class Application extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false)
    private int approveCount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private User user;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vacation> vacations = new ArrayList<>();

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Approve> approves = new ArrayList<>();

    public Application(int approveCount, User user) {
        this(null, 1, approveCount, State.RUNNING, user);
    }

    public Application(Long applicationId, int level, int approveCount, State state, User user) {
        checkNotNull(user, "user must be provided.");

        this.applicationId = applicationId;
        this.level = level;
        this.approveCount = approveCount;
        this.state = state;
        this.user = user;
    }

    public void addVacations(List<Vacation> vacations) {
        this.vacations.addAll(vacations);
    }

    public void addApproves(List<Approve> approves) {
        this.approves.addAll(approves);
    }

    public void approveApplication() {
        this.level++;
    }

}
