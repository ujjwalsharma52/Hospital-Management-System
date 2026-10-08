package com.hospital.management.service;

import com.hospital.management.model.Patient;
import com.hospital.management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for Patient management operations.
 */
@Service
@Transactional
public class PatientService {

    @Autowired
    private PatientRepository patientRepo;

    public List<Patient> findAll() {
        return patientRepo.findAll();
    }

    public List<Patient> searchByName(String name) {
        return patientRepo.findByFullNameContainingIgnoreCase(name);
    }

    public Patient findById(Long id) {
        return patientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + id));
    }

    public Patient save(Patient patient) {
        return patientRepo.save(patient);
    }

    public void deleteById(Long id) {
        if (!patientRepo.existsById(id)) {
            throw new RuntimeException("Patient not found with ID: " + id);
        }
        patientRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long countAll() {
        return patientRepo.count();
    }

    @Transactional(readOnly = true)
    public long countByStatus(String status) {
        return patientRepo.countByStatus(status);
    }

    @Transactional(readOnly = true)
    public long countNewPatientsThisMonth() {
        return patientRepo.countNewPatientsThisMonth();
    }

    /**
     * Returns the most recent patients (last N registered).
     */
    @Transactional(readOnly = true)
    public List<Patient> findRecentPatients(int limit) {
        List<Patient> patients = patientRepo.findAll();
        if (patients.size() > limit) {
            return patients.subList(patients.size() - limit, patients.size());
        }
        return patients;
    }
}
