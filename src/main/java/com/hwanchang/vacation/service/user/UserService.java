package com.hwanchang.vacation.service.user;

import com.hwanchang.vacation.entity.user.User;
import com.hwanchang.vacation.error.NotFoundException;
import com.hwanchang.vacation.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User join(String email, String name, String password, String phone) {
        checkNotNull(password, "password must be provided.");
        checkArgument(
                password.length() >= 8 && password.length() <= 15,
                "password length must be between 8 and 15 characters."
        );

        return update(
                new User(email, name, passwordEncoder.encode(password), phone)
        );
    }

    @Transactional
    public User login(String email, String password) {
        checkNotNull(password, "password must be provided.");

        return findByEmail(email)
                .map(user -> {
                    user.login(passwordEncoder, password);
                    user.afterLoginSuccess();
                    return user;
                }).orElseThrow(() -> new NotFoundException(User.class, email));
    }

    @Transactional
    public User updatePassword(Long userId, String password) {
        checkNotNull(password, "password must be provided.");

        return findById(userId)
                .map(user -> {
                    user.updatePassword(passwordEncoder.encode(password));
                    return userRepository.save(user);
                }).orElseThrow(() -> new NotFoundException(User.class, userId));
    }

    @Transactional
    public User updateUser(Long userId, String name, String phone) {
        return findById(userId)
                .map(user -> {
                    user.updateUser(name, phone);
                    return update(user);
                }).orElseThrow(() -> new NotFoundException(User.class, userId));
    }

    @Transactional
    public User delete(Long userId) {
        return findById(userId)
                .map(user -> {
                    userRepository.delete(user);
                    return user;
                }).orElseThrow(() -> new NotFoundException(User.class, userId));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    private User update(User user) {
        return userRepository.save(user);
    }

}
