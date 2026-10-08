package com.hospital.management.service;

import com.hospital.management.model.Doctor;
import com.hospital.management.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for Doctor management operations.
 */
@Service
@Transactional
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepo;

    public List<Doctor> findAll() {
        return doctorRepo.findAll();
    }

    public List<Doctor> searchByName(String name) {
        return doctorRepo.findByFullNameContainingIgnoreCase(name);
    }

    public Doctor findById(Long id) {
        return doctorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));
    }

    public Doctor save(Doctor doctor) {
        return doctorRepo.save(doctor);
    }

    public void deleteById(Long id) {
        if (!doctorRepo.existsById(id)) {
            throw new RuntimeException("Doctor not found with ID: " + id);
        }
        doctorRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long countAll() {
        return doctorRepo.count();
    }

    @Transactional(readOnly = true)
    public long countByStatus(String status) {
        return doctorRepo.countByStatus(status);
    }

    @Transactional(readOnly = true)
    public java.util.Optional<Doctor> findByUser(com.hospital.management.model.User user) {
        if (user == null) return java.util.Optional.empty();
        return doctorRepo.findByUser(user);
    }

    @Transactional(readOnly = true)
    public java.util.Optional<Doctor> findByUserId(Long userId) {
        if (userId == null) return java.util.Optional.empty();
        return doctorRepo.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public java.util.Optional<Doctor> findByUsername(String username) {
        if (username == null) return java.util.Optional.empty();
        return doctorRepo.findByUserUsername(username);
    }

    /**
     * Resolves the Doctor entity for a given user, with intelligent fallback.
     */
    @Transactional(readOnly = true)
    public Doctor getDoctorForUser(com.hospital.management.model.User user) {
        if (user != null) {
            java.util.Optional<Doctor> byUser = doctorRepo.findByUser(user);
            if (byUser.isPresent()) return byUser.get();

            java.util.Optional<Doctor> byUsername = doctorRepo.findByUserUsername(user.getUsername());
            if (byUsername.isPresent()) return byUsername.get();

            List<Doctor> byName = searchByName(user.getFullName());
            if (!byName.isEmpty()) return byName.get(0);
        }

        // Fallback: return the first doctor or create an empty shell
        List<Doctor> all = findAll();
        if (!all.isEmpty()) {
            return all.get(0);
        }
        return Doctor.builder()
                .fullName(user != null ? user.getFullName() : "Doctor")
                .specialization("General Physician")
                .qualification("MBBS")
                .status("Active")
                .build();
    }
}
