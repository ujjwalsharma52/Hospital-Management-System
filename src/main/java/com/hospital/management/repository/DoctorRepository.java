package com.hospital.management.repository;

import com.hospital.management.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByFullNameContainingIgnoreCase(String name);
    List<Doctor> findBySpecialization(String specialization);
    List<Doctor> findByStatus(String status);
    long countByStatus(String status);

    List<Doctor> findBySpecializationContainingIgnoreCase(String specialization);

    java.util.Optional<Doctor> findByUser(com.hospital.management.model.User user);
    java.util.Optional<Doctor> findByUserId(Long userId);
    java.util.Optional<Doctor> findByUserUsername(String username);
}

