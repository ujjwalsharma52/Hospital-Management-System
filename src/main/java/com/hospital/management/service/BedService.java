package com.hospital.management.service;

import com.hospital.management.model.Bed;
import com.hospital.management.model.Patient;
import com.hospital.management.model.Ward;
import com.hospital.management.repository.BedRepository;
import com.hospital.management.repository.PatientRepository;
import com.hospital.management.repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service layer for Bed and Ward management operations.
 */
@Service
@Transactional
public class BedService {

    @Autowired
    private BedRepository bedRepo;

    @Autowired
    private WardRepository wardRepo;

    @Autowired
    private PatientRepository patientRepo;

    // ---- Bed Operations ----

    public List<Bed> findAllBeds() {
        return bedRepo.findAll();
    }

    public Bed findBedById(Long id) {
        return bedRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Bed not found with ID: " + id));
    }

    /**
     * Saves a bed, linking it to the specified ward and optionally assigning a patient.
     */
    public Bed saveBed(Bed bed, Long wardId, Long patientId) {
        Ward ward = wardRepo.findById(wardId)
                .orElseThrow(() -> new RuntimeException("Ward not found with ID: " + wardId));
        bed.setWard(ward);

        if (patientId != null) {
            Patient patient = patientRepo.findById(patientId)
                    .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));
            bed.setPatient(patient);
            bed.setStatus("Occupied");
        }

        return bedRepo.save(bed);
    }

    /**
     * Discharges a patient from a bed, setting it back to Available.
     */
    public Bed discharge(Long bedId) {
        Bed bed = findBedById(bedId);
        bed.setPatient(null);
        bed.setStatus("Available");
        bed.setDischargeDate(LocalDateTime.now());
        return bedRepo.save(bed);
    }

    public void deleteBed(Long id) {
        if (!bedRepo.existsById(id)) {
            throw new RuntimeException("Bed not found with ID: " + id);
        }
        bedRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long countAvailableBeds() {
        return bedRepo.countAvailableBeds();
    }

    @Transactional(readOnly = true)
    public long countOccupiedBeds() {
        return bedRepo.countOccupiedBeds();
    }

    @Transactional(readOnly = true)
    public long countAllBeds() {
        return bedRepo.count();
    }

    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }

    // ---- Ward Operations ----

    public List<Ward> findAllWards() {
        return wardRepo.findAll();
    }

    public Ward saveWard(Ward ward) {
        return wardRepo.save(ward);
    }
}
