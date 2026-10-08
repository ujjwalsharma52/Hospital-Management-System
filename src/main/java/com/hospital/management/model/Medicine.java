package com.hospital.management.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a medicine in the pharmacy inventory.
 */
@Entity
@Table(name = "medicines")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String genericName;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(length = 100)
    private String manufacturer;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false)
    private Integer reorderLevel = 50;

    @Column(length = 50)
    private String batchNumber;

    private LocalDateTime expiryDate;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private boolean active = true;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Medicine() {}

    public Medicine(Long id, String name, String genericName, String category, String manufacturer, Double price, Integer stockQuantity, Integer reorderLevel, String batchNumber, LocalDateTime expiryDate, String description, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.genericName = genericName;
        this.category = category;
        this.manufacturer = manufacturer;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.reorderLevel = reorderLevel != null ? reorderLevel : 50;
        this.batchNumber = batchNumber;
        this.expiryDate = expiryDate;
        this.description = description;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static MedicineBuilder builder() {
        return new MedicineBuilder();
    }

    public static class MedicineBuilder {
        private Long id;
        private String name;
        private String genericName;
        private String category;
        private String manufacturer;
        private Double price;
        private Integer stockQuantity;
        private Integer reorderLevel = 50;
        private String batchNumber;
        private LocalDateTime expiryDate;
        private String description;
        private boolean active = true;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public MedicineBuilder id(Long id) { this.id = id; return this; }
        public MedicineBuilder name(String name) { this.name = name; return this; }
        public MedicineBuilder genericName(String genericName) { this.genericName = genericName; return this; }
        public MedicineBuilder category(String category) { this.category = category; return this; }
        public MedicineBuilder manufacturer(String manufacturer) { this.manufacturer = manufacturer; return this; }
        public MedicineBuilder price(Double price) { this.price = price; return this; }
        public MedicineBuilder stockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; return this; }
        public MedicineBuilder reorderLevel(Integer reorderLevel) { this.reorderLevel = reorderLevel; return this; }
        public MedicineBuilder batchNumber(String batchNumber) { this.batchNumber = batchNumber; return this; }
        public MedicineBuilder expiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; return this; }
        public MedicineBuilder description(String description) { this.description = description; return this; }
        public MedicineBuilder active(boolean active) { this.active = active; return this; }
        public MedicineBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public MedicineBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Medicine build() {
            return new Medicine(id, name, genericName, category, manufacturer, price, stockQuantity, reorderLevel, batchNumber, expiryDate, description, active, createdAt, updatedAt);
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
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGenericName() { return genericName; }
    public void setGenericName(String genericName) { this.genericName = genericName; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
    public Integer getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(Integer reorderLevel) { this.reorderLevel = reorderLevel; }
    public String getBatchNumber() { return batchNumber; }
    public void setBatchNumber(String batchNumber) { this.batchNumber = batchNumber; }
    public LocalDateTime getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
