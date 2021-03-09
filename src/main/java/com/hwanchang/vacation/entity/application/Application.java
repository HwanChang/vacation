package com.hwanchang.vacation.entity.application;

import com.hwanchang.vacation.entity.BaseTimeEntity;
import com.hwanchang.vacation.entity.approve.Approve;
import com.hwanchang.vacation.entity.user.User;
import com.hwanchang.vacation.entity.vacation.Vacation;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private User user;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vacation> vacations = new ArrayList<>();

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Approve> approves = new ArrayList<>();

    public Application(User user) {
        this(null, 1, user);
    }

    public Application(Long applicationId, int level, User user) {
        checkNotNull(user, "user must be provided.");

        this.applicationId = applicationId;
        this.level = level;
        this.user = user;
    }

    public void addVacations(List<Vacation> vacations) {
        this.vacations.addAll(vacations);
    }

    public void addApproves(List<Approve> approves) {
        this.approves.addAll(approves);
    }

}
