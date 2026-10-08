package com.hospital.management.repository;

import com.hospital.management.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findAllByOrderByCreatedAtDesc();

    List<Prescription> findByPatientIdOrderByPrescriptionDateDesc(Long patientId);

    List<Prescription> findByDoctorIdOrderByPrescriptionDateDesc(Long doctorId);

    List<Prescription> findByStatusOrderByCreatedAtDesc(String status);

    Optional<Prescription> findByPrescriptionNumber(String prescriptionNumber);

    long countByStatus(String status);

    @Query("SELECT p FROM Prescription p WHERE " +
           "LOWER(p.prescriptionNumber) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.patient.fullName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.doctor.fullName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.diagnosis) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "ORDER BY p.createdAt DESC")
    List<Prescription> searchPrescriptions(@Param("query") String query);

    @Query("SELECT p FROM Prescription p WHERE p.status = 'Active' ORDER BY p.createdAt DESC")
    List<Prescription> findPendingDispensePrescriptions();
}
