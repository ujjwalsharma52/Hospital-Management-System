package com.hospital.management.repository;

import com.hospital.management.model.User;
import com.hospital.management.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    List<User> findByRole(Role role);
    long countByRole(Role role);
    long countByEnabled(boolean enabled);
}
