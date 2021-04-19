package com.hwanchang.vacation.entity.user;

import com.hwanchang.vacation.entity.BaseTimeEntity;
import com.hwanchang.vacation.entity.application.Application;
import com.hwanchang.vacation.entity.approve.Approve;
import com.hwanchang.vacation.entity.confirm.Confirm;
import com.hwanchang.vacation.security.Jwt;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.time.LocalDateTime.now;
import static java.util.regex.Pattern.matches;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@DynamicUpdate
@Entity
@EqualsAndHashCode(of = "userId", callSuper = false)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"applications", "approves", "confirmList"})
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 100)
    private String password;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Role> roles;

    @Column(nullable = false, length = 13)
    private String phone;

    private int loginCount;

    private LocalDateTime lastLoginAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Application> applications = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Approve> approves = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Confirm> confirmList = new ArrayList<>();

    public User(String email, String name, String password, String phone) {
        this(null, email, name, List.of(Role.USER), password, phone, 0, null);
    }

    @Builder
    private User(Long userId, String email, String name, List<Role> roles, String password, String phone, int loginCount, LocalDateTime lastLoginAt) {
        checkArgument(isNotEmpty(email), "email must be provided.");
        checkArgument(
                email.length() >= 4 && email.length() <= 50,
                "email length must be between 4 and 50 characters."
        );
        checkArgument(checkEmail(email), "Invalid email address: " + email);
        checkArgument(isNotEmpty(name), "name must be provided.");
        checkArgument(
                name.length() >= 1 && name.length() <= 10,
                "name length must be between 1 and 10 characters."
        );
        checkNotNull(password, "password must be provided.");
        checkNotNull(phone, "phone must be provided.");
        checkArgument(checkPhone(phone), "Invalid phone number: " + phone);

        this.userId = userId;
        this.email = email;
        this.name = name;
        this.roles = roles;
        this.password = password;
        this.phone = phone;
        this.loginCount = loginCount;
        this.lastLoginAt = lastLoginAt;
    }

    private static boolean checkEmail(String email) {
        return matches("[\\w~\\-.+]+@[\\w~\\-]+(\\.[\\w~\\-]+)+", email);
    }

    private static boolean checkPhone(String phone) {
        return matches("^\\d{3}-\\d{3,4}-\\d{4}$", phone);
    }

    public void login(PasswordEncoder passwordEncoder, String credentials) {
        if (!passwordEncoder.matches(credentials, password))
            throw new IllegalArgumentException("Bad credential");
    }

    public void afterLoginSuccess() {
        loginCount++;
        lastLoginAt = now();
    }

    public String createToken(Jwt jwt, String[] roles) {
        Jwt.Claims claims = Jwt.Claims.of(userId, email, roles);
        return jwt.newToken(claims);
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateUser(String name, String phone) {
        checkArgument(isNotEmpty(name), "name must be provided.");
        checkArgument(
                name.length() >= 1 && name.length() <= 10,
                "name length must be between 1 and 10 characters."
        );
        checkNotNull(phone, "phone must be provided.");
        checkArgument(checkPhone(phone), "Invalid phone number: " + phone);

        this.name = name;
        this.phone = phone;
    }

    public void changeRole(List<Role> roles) {
        this.roles = roles;
    }

}
