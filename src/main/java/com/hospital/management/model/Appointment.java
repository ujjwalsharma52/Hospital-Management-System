package com.hospital.management.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * Entity representing an appointment between a patient and a doctor.
 */
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(nullable = false)
    private LocalDate appointmentDate;

    @Column(nullable = false)
    private LocalTime appointmentTime;

    @Column(length = 500)
    private String reason;

    @Column(length = 1000)
    private String notes;

    @Column(nullable = false, length = 20)
    private String status = "Scheduled";

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Appointment() {}

    public Appointment(Long id, Patient patient, Doctor doctor, LocalDate appointmentDate, LocalTime appointmentTime, String reason, String notes, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.reason = reason;
        this.notes = notes;
        this.status = status != null ? status : "Scheduled";
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static AppointmentBuilder builder() {
        return new AppointmentBuilder();
    }

    public static class AppointmentBuilder {
        private Long id;
        private Patient patient;
        private Doctor doctor;
        private LocalDate appointmentDate;
        private LocalTime appointmentTime;
        private String reason;
        private String notes;
        private String status = "Scheduled";
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public AppointmentBuilder id(Long id) { this.id = id; return this; }
        public AppointmentBuilder patient(Patient patient) { this.patient = patient; return this; }
        public AppointmentBuilder doctor(Doctor doctor) { this.doctor = doctor; return this; }
        public AppointmentBuilder appointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; return this; }
        public AppointmentBuilder appointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; return this; }
        public AppointmentBuilder reason(String reason) { this.reason = reason; return this; }
        public AppointmentBuilder notes(String notes) { this.notes = notes; return this; }
        public AppointmentBuilder status(String status) { this.status = status; return this; }
        public AppointmentBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public AppointmentBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Appointment build() {
            return new Appointment(id, patient, doctor, appointmentDate, appointmentTime, reason, notes, status, createdAt, updatedAt);
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
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }
    public LocalTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
