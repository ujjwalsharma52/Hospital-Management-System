package com.hospital.management.repository;

import com.hospital.management.model.Bed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BedRepository extends JpaRepository<Bed, Long> {
    List<Bed> findByWardId(Long wardId);
    List<Bed> findByStatus(String status);
    List<Bed> findByPatientId(Long patientId);
    long countByStatus(String status);

    @Query("SELECT COUNT(b) FROM Bed b WHERE b.status = 'Available'")
    long countAvailableBeds();

    @Query("SELECT COUNT(b) FROM Bed b WHERE b.status = 'Occupied'")
    long countOccupiedBeds();
}
