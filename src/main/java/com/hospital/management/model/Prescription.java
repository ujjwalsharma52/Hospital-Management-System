package com.hospital.management.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing an electronic prescription (Rx) issued by a doctor.
 */
@Entity
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String prescriptionNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @Column(nullable = false)
    private LocalDate prescriptionDate = LocalDate.now();

    @Column(nullable = false, length = 500)
    private String diagnosis;

    @Column(length = 500)
    private String symptoms;

    @Column(length = 255)
    private String vitals; // e.g. "BP: 120/80 | Pulse: 74 bpm | SpO2: 98% | Temp: 98.6°F"

    @Column(length = 1000)
    private String advice; // General clinical advice / lifestyle instructions

    private LocalDate followUpDate;

    @Column(nullable = false, length = 30)
    private String status = "Active"; // Active, Dispensed, Completed, Cancelled

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescriptionItem> items = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Prescription() {}

    public Prescription(Long id, String prescriptionNumber, Patient patient, Doctor doctor, Appointment appointment,
                        LocalDate prescriptionDate, String diagnosis, String symptoms, String vitals, String advice,
                        LocalDate followUpDate, String status, List<PrescriptionItem> items,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.prescriptionNumber = prescriptionNumber;
        this.patient = patient;
        this.doctor = doctor;
        this.appointment = appointment;
        this.prescriptionDate = prescriptionDate != null ? prescriptionDate : LocalDate.now();
        this.diagnosis = diagnosis;
        this.symptoms = symptoms;
        this.vitals = vitals;
        this.advice = advice;
        this.followUpDate = followUpDate;
        this.status = status != null ? status : "Active";
        this.items = items != null ? items : new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static PrescriptionBuilder builder() {
        return new PrescriptionBuilder();
    }

    public static class PrescriptionBuilder {
        private Long id;
        private String prescriptionNumber;
        private Patient patient;
        private Doctor doctor;
        private Appointment appointment;
        private LocalDate prescriptionDate = LocalDate.now();
        private String diagnosis;
        private String symptoms;
        private String vitals;
        private String advice;
        private LocalDate followUpDate;
        private String status = "Active";
        private List<PrescriptionItem> items = new ArrayList<>();
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public PrescriptionBuilder id(Long id) { this.id = id; return this; }
        public PrescriptionBuilder prescriptionNumber(String prescriptionNumber) { this.prescriptionNumber = prescriptionNumber; return this; }
        public PrescriptionBuilder patient(Patient patient) { this.patient = patient; return this; }
        public PrescriptionBuilder doctor(Doctor doctor) { this.doctor = doctor; return this; }
        public PrescriptionBuilder appointment(Appointment appointment) { this.appointment = appointment; return this; }
        public PrescriptionBuilder prescriptionDate(LocalDate prescriptionDate) { this.prescriptionDate = prescriptionDate; return this; }
        public PrescriptionBuilder diagnosis(String diagnosis) { this.diagnosis = diagnosis; return this; }
        public PrescriptionBuilder symptoms(String symptoms) { this.symptoms = symptoms; return this; }
        public PrescriptionBuilder vitals(String vitals) { this.vitals = vitals; return this; }
        public PrescriptionBuilder advice(String advice) { this.advice = advice; return this; }
        public PrescriptionBuilder followUpDate(LocalDate followUpDate) { this.followUpDate = followUpDate; return this; }
        public PrescriptionBuilder status(String status) { this.status = status; return this; }
        public PrescriptionBuilder items(List<PrescriptionItem> items) { this.items = items; return this; }
        public PrescriptionBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public PrescriptionBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Prescription build() {
            return new Prescription(id, prescriptionNumber, patient, doctor, appointment, prescriptionDate, diagnosis, symptoms, vitals, advice, followUpDate, status, items, createdAt, updatedAt);
        }
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (prescriptionDate == null) {
            prescriptionDate = LocalDate.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void addItem(PrescriptionItem item) {
        items.add(item);
        item.setPrescription(this);
    }

    public void removeItem(PrescriptionItem item) {
        items.remove(item);
        item.setPrescription(null);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPrescriptionNumber() { return prescriptionNumber; }
    public void setPrescriptionNumber(String prescriptionNumber) { this.prescriptionNumber = prescriptionNumber; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }
    public LocalDate getPrescriptionDate() { return prescriptionDate; }
    public void setPrescriptionDate(LocalDate prescriptionDate) { this.prescriptionDate = prescriptionDate; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }
    public String getVitals() { return vitals; }
    public void setVitals(String vitals) { this.vitals = vitals; }
    public String getAdvice() { return advice; }
    public void setAdvice(String advice) { this.advice = advice; }
    public LocalDate getFollowUpDate() { return followUpDate; }
    public void setFollowUpDate(LocalDate followUpDate) { this.followUpDate = followUpDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<PrescriptionItem> getItems() { return items; }
    public void setItems(List<PrescriptionItem> items) { this.items = items; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
