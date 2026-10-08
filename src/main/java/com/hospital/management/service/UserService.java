package com.hospital.management.service;

import com.hospital.management.model.Role;
import com.hospital.management.model.User;
import com.hospital.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for User management and authentication operations.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<User> findByRole(Role role) {
        return userRepo.findByRole(role);
    }

    public User save(User user) {
        return userRepo.save(user);
    }
}
