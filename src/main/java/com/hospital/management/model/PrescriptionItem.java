package com.hospital.management.model;

import jakarta.persistence.*;

/**
 * Entity representing an individual medicine item within a prescription.
 */
@Entity
@Table(name = "prescription_items")
public class PrescriptionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    @Column(nullable = false, length = 150)
    private String medicineName;

    @Column(length = 100)
    private String dosage = "1 Tablet"; // e.g. "500 mg", "1 Tablet", "10 ml"

    @Column(length = 100)
    private String frequency = "1-0-1"; // e.g. "1-0-1 (Morning & Night)", "1-1-1 (Thrice Daily)", "0-0-1 (Night)"

    @Column(length = 50)
    private String duration = "5 Days"; // e.g. "3 Days", "5 Days", "10 Days", "1 Month"

    @Column(length = 50)
    private String timing = "After Food"; // "After Food", "Before Food", "With Water", "Empty Stomach"

    @Column(nullable = false)
    private Integer quantity = 10;

    @Column(length = 255)
    private String instructions;

    @Column(nullable = false)
    private boolean dispensed = false;

    public PrescriptionItem() {}

    public PrescriptionItem(Long id, Prescription prescription, Medicine medicine, String medicineName,
                            String dosage, String frequency, String duration, String timing,
                            Integer quantity, String instructions, boolean dispensed) {
        this.id = id;
        this.prescription = prescription;
        this.medicine = medicine;
        this.medicineName = medicineName;
        this.dosage = dosage != null ? dosage : "1 Tablet";
        this.frequency = frequency != null ? frequency : "1-0-1";
        this.duration = duration != null ? duration : "5 Days";
        this.timing = timing != null ? timing : "After Food";
        this.quantity = quantity != null ? quantity : 10;
        this.instructions = instructions;
        this.dispensed = dispensed;
    }

    public static PrescriptionItemBuilder builder() {
        return new PrescriptionItemBuilder();
    }

    public static class PrescriptionItemBuilder {
        private Long id;
        private Prescription prescription;
        private Medicine medicine;
        private String medicineName;
        private String dosage = "1 Tablet";
        private String frequency = "1-0-1";
        private String duration = "5 Days";
        private String timing = "After Food";
        private Integer quantity = 10;
        private String instructions;
        private boolean dispensed = false;

        public PrescriptionItemBuilder id(Long id) { this.id = id; return this; }
        public PrescriptionItemBuilder prescription(Prescription prescription) { this.prescription = prescription; return this; }
        public PrescriptionItemBuilder medicine(Medicine medicine) { this.medicine = medicine; return this; }
        public PrescriptionItemBuilder medicineName(String medicineName) { this.medicineName = medicineName; return this; }
        public PrescriptionItemBuilder dosage(String dosage) { this.dosage = dosage; return this; }
        public PrescriptionItemBuilder frequency(String frequency) { this.frequency = frequency; return this; }
        public PrescriptionItemBuilder duration(String duration) { this.duration = duration; return this; }
        public PrescriptionItemBuilder timing(String timing) { this.timing = timing; return this; }
        public PrescriptionItemBuilder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public PrescriptionItemBuilder instructions(String instructions) { this.instructions = instructions; return this; }
        public PrescriptionItemBuilder dispensed(boolean dispensed) { this.dispensed = dispensed; return this; }

        public PrescriptionItem build() {
            return new PrescriptionItem(id, prescription, medicine, medicineName, dosage, frequency, duration, timing, quantity, instructions, dispensed);
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Prescription getPrescription() { return prescription; }
    public void setPrescription(Prescription prescription) { this.prescription = prescription; }
    public Medicine getMedicine() { return medicine; }
    public void setMedicine(Medicine medicine) { this.medicine = medicine; }
    public String getMedicineName() { return medicineName; }
    public void setMedicineName(String medicineName) { this.medicineName = medicineName; }
    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }
    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public String getTiming() { return timing; }
    public void setTiming(String timing) { this.timing = timing; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    public boolean isDispensed() { return dispensed; }
    public void setDispensed(boolean dispensed) { this.dispensed = dispensed; }
}
