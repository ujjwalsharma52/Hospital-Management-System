package com.hospital.management.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a doctor in the hospital.
 */
@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, length = 100)
    private String specialization;

    @Column(length = 50)
    private String qualification;

    @Column(length = 15)
    private String phone;

    @Column(length = 100)
    private String email;

    @Column(nullable = false, length = 10)
    private String gender;

    @Column(length = 500)
    private String address;

    @Column(nullable = false)
    private Double consultationFee = 500.0;

    @Column(length = 50)
    private String availableDays;

    @Column(length = 20)
    private String availableTimeStart;

    @Column(length = 20)
    private String availableTimeEnd;

    @Column(nullable = false, length = 20)
    private String status = "Active";

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Doctor() {}

    public Doctor(Long id, String fullName, String specialization, String qualification, String phone, String email, String gender, String address, Double consultationFee, String availableDays, String availableTimeStart, String availableTimeEnd, String status, User user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.fullName = fullName;
        this.specialization = specialization;
        this.qualification = qualification;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.consultationFee = consultationFee != null ? consultationFee : 500.0;
        this.availableDays = availableDays;
        this.availableTimeStart = availableTimeStart;
        this.availableTimeEnd = availableTimeEnd;
        this.status = status != null ? status : "Active";
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static DoctorBuilder builder() {
        return new DoctorBuilder();
    }

    public static class DoctorBuilder {
        private Long id;
        private String fullName;
        private String specialization;
        private String qualification;
        private String phone;
        private String email;
        private String gender;
        private String address;
        private Double consultationFee = 500.0;
        private String availableDays;
        private String availableTimeStart;
        private String availableTimeEnd;
        private String status = "Active";
        private User user;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public DoctorBuilder id(Long id) { this.id = id; return this; }
        public DoctorBuilder fullName(String fullName) { this.fullName = fullName; return this; }
        public DoctorBuilder specialization(String specialization) { this.specialization = specialization; return this; }
        public DoctorBuilder qualification(String qualification) { this.qualification = qualification; return this; }
        public DoctorBuilder phone(String phone) { this.phone = phone; return this; }
        public DoctorBuilder email(String email) { this.email = email; return this; }
        public DoctorBuilder gender(String gender) { this.gender = gender; return this; }
        public DoctorBuilder address(String address) { this.address = address; return this; }
        public DoctorBuilder consultationFee(Double fee) { this.consultationFee = fee; return this; }
        public DoctorBuilder availableDays(String days) { this.availableDays = days; return this; }
        public DoctorBuilder availableTimeStart(String start) { this.availableTimeStart = start; return this; }
        public DoctorBuilder availableTimeEnd(String end) { this.availableTimeEnd = end; return this; }
        public DoctorBuilder status(String status) { this.status = status; return this; }
        public DoctorBuilder user(User user) { this.user = user; return this; }
        public DoctorBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public DoctorBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Doctor build() {
            return new Doctor(id, fullName, specialization, qualification, phone, email, gender, address, consultationFee, availableDays, availableTimeStart, availableTimeEnd, status, user, createdAt, updatedAt);
        }
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Double getConsultationFee() { return consultationFee; }
    public void setConsultationFee(Double consultationFee) { this.consultationFee = consultationFee; }
    public String getAvailableDays() { return availableDays; }
    public void setAvailableDays(String availableDays) { this.availableDays = availableDays; }
    public String getAvailableTimeStart() { return availableTimeStart; }
    public void setAvailableTimeStart(String availableTimeStart) { this.availableTimeStart = availableTimeStart; }
    public String getAvailableTimeEnd() { return availableTimeEnd; }
    public void setAvailableTimeEnd(String availableTimeEnd) { this.availableTimeEnd = availableTimeEnd; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
