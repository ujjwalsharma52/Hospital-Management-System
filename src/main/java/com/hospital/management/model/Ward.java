package com.hospital.management.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a hospital ward.
 */
@Entity
@Table(name = "wards")
public class Ward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String wardName;

    @Column(nullable = false, length = 50)
    private String wardType;

    @Column(nullable = false)
    private Integer totalBeds;

    @Column(nullable = false)
    private Integer floorNumber;

    @Column(length = 200)
    private String description;

    @Column(nullable = false)
    private boolean active = true;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Ward() {}

    public Ward(Long id, String wardName, String wardType, Integer totalBeds, Integer floorNumber, String description, boolean active, LocalDateTime createdAt) {
        this.id = id;
        this.wardName = wardName;
        this.wardType = wardType;
        this.totalBeds = totalBeds;
        this.floorNumber = floorNumber;
        this.description = description;
        this.active = active;
        this.createdAt = createdAt;
    }

    public static WardBuilder builder() {
        return new WardBuilder();
    }

    public static class WardBuilder {
        private Long id;
        private String wardName;
        private String wardType;
        private Integer totalBeds;
        private Integer floorNumber;
        private String description;
        private boolean active = true;
        private LocalDateTime createdAt;

        public WardBuilder id(Long id) { this.id = id; return this; }
        public WardBuilder wardName(String wardName) { this.wardName = wardName; return this; }
        public WardBuilder wardType(String wardType) { this.wardType = wardType; return this; }
        public WardBuilder totalBeds(Integer totalBeds) { this.totalBeds = totalBeds; return this; }
        public WardBuilder floorNumber(Integer floorNumber) { this.floorNumber = floorNumber; return this; }
        public WardBuilder description(String description) { this.description = description; return this; }
        public WardBuilder active(boolean active) { this.active = active; return this; }
        public WardBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Ward build() {
            return new Ward(id, wardName, wardType, totalBeds, floorNumber, description, active, createdAt);
        }
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getWardName() { return wardName; }
    public void setWardName(String wardName) { this.wardName = wardName; }
    public String getWardType() { return wardType; }
    public void setWardType(String wardType) { this.wardType = wardType; }
    public Integer getTotalBeds() { return totalBeds; }
    public void setTotalBeds(Integer totalBeds) { this.totalBeds = totalBeds; }
    public Integer getFloorNumber() { return floorNumber; }
    public void setFloorNumber(Integer floorNumber) { this.floorNumber = floorNumber; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
