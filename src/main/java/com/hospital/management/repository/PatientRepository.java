package com.hospital.management.repository;

import com.hospital.management.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByFullNameContainingIgnoreCase(String name);
    List<Patient> findByStatus(String status);
    long countByStatus(String status);

    @Query("SELECT COUNT(p) FROM Patient p WHERE MONTH(p.registeredAt) = MONTH(CURRENT_DATE) AND YEAR(p.registeredAt) = YEAR(CURRENT_DATE)")
    long countNewPatientsThisMonth();
}
