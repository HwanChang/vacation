package com.hwanchang.vacation.service.admin;

import com.hwanchang.vacation.entity.user.Role;
import com.hwanchang.vacation.entity.user.User;
import com.hwanchang.vacation.error.UserNotFoundException;
import com.hwanchang.vacation.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final UserRepository userRepository;

    @Transactional
    public User changeRole(Long userId, List<Role> roles) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.changeRole(roles);
                    return user;
                }).orElseThrow(() -> new UserNotFoundException(User.class, userId));
    }

}
