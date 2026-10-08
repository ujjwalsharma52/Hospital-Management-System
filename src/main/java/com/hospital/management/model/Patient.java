package com.hospital.management.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * Entity representing a patient in the hospital.
 */
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false, length = 10)
    private String gender;

    @Column(length = 15)
    private String phone;

    @Column(length = 100)
    private String email;

    @Column(length = 500)
    private String address;

    @Column(length = 20)
    private String bloodGroup;

    @Column(length = 500)
    private String allergies;

    @Column(length = 1000)
    private String medicalHistory;

    @Column(length = 50)
    private String emergencyContactName;

    @Column(length = 15)
    private String emergencyContactPhone;

    @Column(nullable = false, length = 20)
    private String status = "Active"; // Active, Discharged, Deceased

    @Column(updatable = false)
    private LocalDateTime registeredAt;

    private LocalDateTime updatedAt;

    public Patient() {}

    public Patient(Long id, String fullName, LocalDate dateOfBirth, String gender, String phone, String email, String address, String bloodGroup, String allergies, String medicalHistory, String emergencyContactName, String emergencyContactPhone, String status, LocalDateTime registeredAt, LocalDateTime updatedAt) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.bloodGroup = bloodGroup;
        this.allergies = allergies;
        this.medicalHistory = medicalHistory;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
        this.status = status != null ? status : "Active";
        this.registeredAt = registeredAt;
        this.updatedAt = updatedAt;
    }

    public static PatientBuilder builder() {
        return new PatientBuilder();
    }

    public static class PatientBuilder {
        private Long id;
        private String fullName;
        private LocalDate dateOfBirth;
        private String gender;
        private String phone;
        private String email;
        private String address;
        private String bloodGroup;
        private String allergies;
        private String medicalHistory;
        private String emergencyContactName;
        private String emergencyContactPhone;
        private String status = "Active";
        private LocalDateTime registeredAt;
        private LocalDateTime updatedAt;

        public PatientBuilder id(Long id) { this.id = id; return this; }
        public PatientBuilder fullName(String fullName) { this.fullName = fullName; return this; }
        public PatientBuilder dateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; return this; }
        public PatientBuilder gender(String gender) { this.gender = gender; return this; }
        public PatientBuilder phone(String phone) { this.phone = phone; return this; }
        public PatientBuilder email(String email) { this.email = email; return this; }
        public PatientBuilder address(String address) { this.address = address; return this; }
        public PatientBuilder bloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; return this; }
        public PatientBuilder allergies(String allergies) { this.allergies = allergies; return this; }
        public PatientBuilder medicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; return this; }
        public PatientBuilder emergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; return this; }
        public PatientBuilder emergencyContactPhone(String emergencyContactPhone) { this.emergencyContactPhone = emergencyContactPhone; return this; }
        public PatientBuilder status(String status) { this.status = status; return this; }
        public PatientBuilder registeredAt(LocalDateTime registeredAt) { this.registeredAt = registeredAt; return this; }
        public PatientBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Patient build() {
            return new Patient(id, fullName, dateOfBirth, gender, phone, email, address, bloodGroup, allergies, medicalHistory, emergencyContactName, emergencyContactPhone, status, registeredAt, updatedAt);
        }
    }

    @PrePersist
    protected void onCreate() {
        registeredAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public String getPatientId() {
        return id != null ? String.format("PID-%04d", id) : "PID-NEW";
    }

    public int getAge() {
        if (dateOfBirth != null) {
            return Period.between(dateOfBirth, LocalDate.now()).getYears();
        }
        return 0;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }
    public String getMedicalHistory() { return medicalHistory; }
    public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }
    public String getEmergencyContactName() { return emergencyContactName; }
    public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }
    public String getEmergencyContactPhone() { return emergencyContactPhone; }
    public void setEmergencyContactPhone(String emergencyContactPhone) { this.emergencyContactPhone = emergencyContactPhone; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(LocalDateTime registeredAt) { this.registeredAt = registeredAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
