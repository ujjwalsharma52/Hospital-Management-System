package com.hospital.management.model;

import jakarta.persistence.*;

/**
 * Entity representing a line item within a bill.
 */
@Entity
@Table(name = "bill_items")
public class BillItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(nullable = false)
    private Integer quantity = 1;

    @Column(nullable = false)
    private Double unitPrice = 0.0;

    @Column(nullable = false)
    private Double totalPrice = 0.0;

    public BillItem() {}

    public BillItem(Long id, Bill bill, String description, String category, Integer quantity, Double unitPrice, Double totalPrice) {
        this.id = id;
        this.bill = bill;
        this.description = description;
        this.category = category;
        this.quantity = quantity != null ? quantity : 1;
        this.unitPrice = unitPrice != null ? unitPrice : 0.0;
        this.totalPrice = totalPrice != null ? totalPrice : 0.0;
    }

    public static BillItemBuilder builder() {
        return new BillItemBuilder();
    }

    public static class BillItemBuilder {
        private Long id;
        private Bill bill;
        private String description;
        private String category;
        private Integer quantity = 1;
        private Double unitPrice = 0.0;
        private Double totalPrice = 0.0;

        public BillItemBuilder id(Long id) { this.id = id; return this; }
        public BillItemBuilder bill(Bill bill) { this.bill = bill; return this; }
        public BillItemBuilder description(String description) { this.description = description; return this; }
        public BillItemBuilder category(String category) { this.category = category; return this; }
        public BillItemBuilder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public BillItemBuilder unitPrice(Double unitPrice) { this.unitPrice = unitPrice; return this; }
        public BillItemBuilder totalPrice(Double totalPrice) { this.totalPrice = totalPrice; return this; }

        public BillItem build() {
            return new BillItem(id, bill, description, category, quantity, unitPrice, totalPrice);
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Bill getBill() { return bill; }
    public void setBill(Bill bill) { this.bill = bill; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }
    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
}
