package com.hospital.management.service;

import com.hospital.management.model.Doctor;
import com.hospital.management.model.LabReport;
import com.hospital.management.model.Patient;
import com.hospital.management.repository.DoctorRepository;
import com.hospital.management.repository.LabReportRepository;
import com.hospital.management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for Laboratory report management.
 */
@Service
@Transactional
public class LabReportService {

    @Autowired
    private LabReportRepository labReportRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private DoctorRepository doctorRepo;

    public List<LabReport> findAll() {
        return labReportRepo.findAll();
    }

    public List<LabReport> findByStatus(String status) {
        return labReportRepo.findByStatus(status);
    }

    public LabReport findById(Long id) {
        return labReportRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Lab report not found with ID: " + id));
    }

    /**
     * Saves a lab report, linking it to the specified patient and optionally a doctor.
     */
    public LabReport save(LabReport report, Long patientId, Long doctorId) {
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));
        report.setPatient(patient);

        if (doctorId != null) {
            Doctor doctor = doctorRepo.findById(doctorId)
                    .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + doctorId));
            report.setDoctor(doctor);
        }

        return labReportRepo.save(report);
    }

    public void deleteById(Long id) {
        if (!labReportRepo.existsById(id)) {
            throw new RuntimeException("Lab report not found with ID: " + id);
        }
        labReportRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long countByStatus(String status) {
        return labReportRepo.countByStatus(status);
    }

    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<LabReport> findReportsByDoctor(Long doctorId) {
        if (doctorId == null) return java.util.Collections.emptyList();
        return labReportRepo.findByDoctorIdOrderByTestDateDesc(doctorId);
    }

    @Transactional(readOnly = true)
    public long countPendingByDoctor(Long doctorId) {
        if (doctorId == null) return 0;
        return labReportRepo.countByDoctorIdAndStatus(doctorId, "Pending")
             + labReportRepo.countByDoctorIdAndStatus(doctorId, "In Progress");
    }
}
