package com.hospital.management.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a hospital bed within a ward.
 */
@Entity
@Table(name = "beds")
public class Bed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String bedNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_id", nullable = false)
    private Ward ward;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(nullable = false, length = 20)
    private String status = "Available";

    @Column(nullable = false)
    private Double dailyRate = 0.0;

    private LocalDateTime admissionDate;

    private LocalDateTime dischargeDate;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Bed() {}

    public Bed(Long id, String bedNumber, Ward ward, Patient patient, String status, Double dailyRate, LocalDateTime admissionDate, LocalDateTime dischargeDate, LocalDateTime createdAt) {
        this.id = id;
        this.bedNumber = bedNumber;
        this.ward = ward;
        this.patient = patient;
        this.status = status != null ? status : "Available";
        this.dailyRate = dailyRate != null ? dailyRate : 0.0;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
        this.createdAt = createdAt;
    }

    public static BedBuilder builder() {
        return new BedBuilder();
    }

    public static class BedBuilder {
        private Long id;
        private String bedNumber;
        private Ward ward;
        private Patient patient;
        private String status = "Available";
        private Double dailyRate = 0.0;
        private LocalDateTime admissionDate;
        private LocalDateTime dischargeDate;
        private LocalDateTime createdAt;

        public BedBuilder id(Long id) { this.id = id; return this; }
        public BedBuilder bedNumber(String bedNumber) { this.bedNumber = bedNumber; return this; }
        public BedBuilder ward(Ward ward) { this.ward = ward; return this; }
        public BedBuilder patient(Patient patient) { this.patient = patient; return this; }
        public BedBuilder status(String status) { this.status = status; return this; }
        public BedBuilder dailyRate(Double dailyRate) { this.dailyRate = dailyRate; return this; }
        public BedBuilder admissionDate(LocalDateTime admissionDate) { this.admissionDate = admissionDate; return this; }
        public BedBuilder dischargeDate(LocalDateTime dischargeDate) { this.dischargeDate = dischargeDate; return this; }
        public BedBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Bed build() {
            return new Bed(id, bedNumber, ward, patient, status, dailyRate, admissionDate, dischargeDate, createdAt);
        }
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBedNumber() { return bedNumber; }
    public void setBedNumber(String bedNumber) { this.bedNumber = bedNumber; }
    public Ward getWard() { return ward; }
    public void setWard(Ward ward) { this.ward = ward; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Double getDailyRate() { return dailyRate; }
    public void setDailyRate(Double dailyRate) { this.dailyRate = dailyRate; }
    public LocalDateTime getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(LocalDateTime admissionDate) { this.admissionDate = admissionDate; }
    public LocalDateTime getDischargeDate() { return dischargeDate; }
    public void setDischargeDate(LocalDateTime dischargeDate) { this.dischargeDate = dischargeDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
