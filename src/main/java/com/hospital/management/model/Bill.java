package com.hospital.management.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a billing record for a patient.
 */
@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(nullable = false)
    private Double consultationFee = 0.0;

    @Column(nullable = false)
    private Double medicineCost = 0.0;

    @Column(nullable = false)
    private Double labTestCost = 0.0;

    @Column(nullable = false)
    private Double roomCharges = 0.0;

    @Column(nullable = false)
    private Double otherCharges = 0.0;

    @Column(nullable = false)
    private Double discount = 0.0;

    @Column(nullable = false)
    private Double tax = 0.0;

    @Column(nullable = false)
    private Double totalAmount = 0.0;

    @Column(nullable = false)
    private Double paidAmount = 0.0;

    @Column(nullable = false, length = 20)
    private String paymentMethod = "Cash";

    @Column(nullable = false, length = 20)
    private String status = "Pending";

    @Column(length = 500)
    private String description;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillItem> items = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Bill() {}

    public Bill(Long id, Patient patient, Double consultationFee, Double medicineCost, Double labTestCost, Double roomCharges, Double otherCharges, Double discount, Double tax, Double totalAmount, Double paidAmount, String paymentMethod, String status, String description, List<BillItem> items, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.patient = patient;
        this.consultationFee = consultationFee != null ? consultationFee : 0.0;
        this.medicineCost = medicineCost != null ? medicineCost : 0.0;
        this.labTestCost = labTestCost != null ? labTestCost : 0.0;
        this.roomCharges = roomCharges != null ? roomCharges : 0.0;
        this.otherCharges = otherCharges != null ? otherCharges : 0.0;
        this.discount = discount != null ? discount : 0.0;
        this.tax = tax != null ? tax : 0.0;
        this.totalAmount = totalAmount != null ? totalAmount : 0.0;
        this.paidAmount = paidAmount != null ? paidAmount : 0.0;
        this.paymentMethod = paymentMethod != null ? paymentMethod : "Cash";
        this.status = status != null ? status : "Pending";
        this.description = description;
        this.items = items != null ? items : new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static BillBuilder builder() {
        return new BillBuilder();
    }

    public static class BillBuilder {
        private Long id;
        private Patient patient;
        private Double consultationFee = 0.0;
        private Double medicineCost = 0.0;
        private Double labTestCost = 0.0;
        private Double roomCharges = 0.0;
        private Double otherCharges = 0.0;
        private Double discount = 0.0;
        private Double tax = 0.0;
        private Double totalAmount = 0.0;
        private Double paidAmount = 0.0;
        private String paymentMethod = "Cash";
        private String status = "Pending";
        private String description;
        private List<BillItem> items = new ArrayList<>();
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public BillBuilder id(Long id) { this.id = id; return this; }
        public BillBuilder patient(Patient patient) { this.patient = patient; return this; }
        public BillBuilder consultationFee(Double fee) { this.consultationFee = fee; return this; }
        public BillBuilder medicineCost(Double cost) { this.medicineCost = cost; return this; }
        public BillBuilder labTestCost(Double cost) { this.labTestCost = cost; return this; }
        public BillBuilder roomCharges(Double charges) { this.roomCharges = charges; return this; }
        public BillBuilder otherCharges(Double charges) { this.otherCharges = charges; return this; }
        public BillBuilder discount(Double discount) { this.discount = discount; return this; }
        public BillBuilder tax(Double tax) { this.tax = tax; return this; }
        public BillBuilder totalAmount(Double totalAmount) { this.totalAmount = totalAmount; return this; }
        public BillBuilder paidAmount(Double paidAmount) { this.paidAmount = paidAmount; return this; }
        public BillBuilder paymentMethod(String method) { this.paymentMethod = method; return this; }
        public BillBuilder status(String status) { this.status = status; return this; }
        public BillBuilder description(String desc) { this.description = desc; return this; }
        public BillBuilder items(List<BillItem> items) { this.items = items; return this; }
        public BillBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public BillBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Bill build() {
            return new Bill(id, patient, consultationFee, medicineCost, labTestCost, roomCharges, otherCharges, discount, tax, totalAmount, paidAmount, paymentMethod, status, description, items, createdAt, updatedAt);
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

    /**
     * Calculates the total amount from all cost components.
     */
    public void calculateTotal() {
        double subtotal = (consultationFee != null ? consultationFee : 0.0)
                + (medicineCost != null ? medicineCost : 0.0)
                + (labTestCost != null ? labTestCost : 0.0)
                + (roomCharges != null ? roomCharges : 0.0)
                + (otherCharges != null ? otherCharges : 0.0);
        if (discount != null) {
            subtotal -= discount;
        }
        if (subtotal < 0) subtotal = 0;
        this.tax = subtotal * 0.05; // 5% tax
        this.totalAmount = subtotal + this.tax;
    }

    public void addItem(BillItem item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
        item.setBill(this);
    }

    public void removeItem(BillItem item) {
        if (items != null) {
            items.remove(item);
            item.setBill(null);
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public Double getConsultationFee() { return consultationFee; }
    public void setConsultationFee(Double consultationFee) { this.consultationFee = consultationFee; }
    public Double getMedicineCost() { return medicineCost; }
    public void setMedicineCost(Double medicineCost) { this.medicineCost = medicineCost; }
    public Double getLabTestCost() { return labTestCost; }
    public void setLabTestCost(Double labTestCost) { this.labTestCost = labTestCost; }
    public Double getRoomCharges() { return roomCharges; }
    public void setRoomCharges(Double roomCharges) { this.roomCharges = roomCharges; }
    public Double getOtherCharges() { return otherCharges; }
    public void setOtherCharges(Double otherCharges) { this.otherCharges = otherCharges; }
    public Double getDiscount() { return discount; }
    public void setDiscount(Double discount) { this.discount = discount; }
    public Double getTax() { return tax; }
    public void setTax(Double tax) { this.tax = tax; }
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    public Double getPaidAmount() { return paidAmount; }
    public void setPaidAmount(Double paidAmount) { this.paidAmount = paidAmount; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<BillItem> getItems() { return items; }
    public void setItems(List<BillItem> items) { this.items = items; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
