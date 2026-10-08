package com.hospital.management.service;

import com.hospital.management.model.*;
import com.hospital.management.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service handling Electronic Prescriptions (Rx) and pharmacy dispensing workflows.
 */
@Service
@Transactional
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private MedicineRepository medicineRepo;

    @Autowired
    private BillRepository billRepo;

    public List<Prescription> findAll() {
        return prescriptionRepo.findAllByOrderByCreatedAtDesc();
    }

    public List<Prescription> findByStatus(String status) {
        return prescriptionRepo.findByStatusOrderByCreatedAtDesc(status);
    }

    public List<Prescription> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return findAll();
        }
        return prescriptionRepo.searchPrescriptions(query.trim());
    }

    public Prescription findById(Long id) {
        return prescriptionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with ID: " + id));
    }

    public List<Prescription> findByPatientId(Long patientId) {
        return prescriptionRepo.findByPatientIdOrderByPrescriptionDateDesc(patientId);
    }

    public List<Prescription> findByDoctorId(Long doctorId) {
        return prescriptionRepo.findByDoctorIdOrderByPrescriptionDateDesc(doctorId);
    }

    public long countPendingDispense() {
        return prescriptionRepo.countByStatus("Active");
    }

    /**
     * Saves or updates a prescription with its line items.
     */
    public Prescription save(Prescription prescription, Long patientId, Long doctorId, Long appointmentId, List<PrescriptionItem> items) {
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + doctorId));

        prescription.setPatient(patient);
        prescription.setDoctor(doctor);

        if (appointmentId != null && appointmentId > 0) {
            appointmentRepo.findById(appointmentId).ifPresent(prescription::setAppointment);
        }

        if (prescription.getPrescriptionDate() == null) {
            prescription.setPrescriptionDate(LocalDate.now());
        }

        if (prescription.getPrescriptionNumber() == null || prescription.getPrescriptionNumber().trim().isEmpty()) {
            // Generate professional Rx number: RX-YYYYMM-XXXX
            long count = prescriptionRepo.count() + 1001;
            prescription.setPrescriptionNumber("RX-" + LocalDate.now().getYear() + "-" + count);
        }

        // Setup items bidirectional mapping
        if (prescription.getItems() == null) {
            prescription.setItems(new ArrayList<>());
        } else {
            prescription.getItems().clear();
        }

        if (items != null) {
            for (PrescriptionItem item : items) {
                if (item.getMedicineName() != null && !item.getMedicineName().trim().isEmpty()) {
                    // Try to link medicine from inventory if ID provided or by matching name
                    if (item.getMedicine() != null && item.getMedicine().getId() != null) {
                        medicineRepo.findById(item.getMedicine().getId()).ifPresent(item::setMedicine);
                    } else {
                        List<Medicine> matched = medicineRepo.findByNameContainingIgnoreCase(item.getMedicineName().trim());
                        if (!matched.isEmpty()) {
                            item.setMedicine(matched.get(0));
                        }
                    }
                    prescription.addItem(item);
                }
            }
        }

        return prescriptionRepo.save(prescription);
    }

    /**
     * Dispenses the medications for a prescription:
     * 1. Decrements stock in pharmacy inventory
     * 2. Automatically creates or appends line items to patient's bill
     * 3. Marks prescription as 'Dispensed'
     */
    public Prescription dispensePrescription(Long prescriptionId) {
        Prescription prescription = findById(prescriptionId);

        if ("Dispensed".equalsIgnoreCase(prescription.getStatus())) {
            throw new IllegalStateException("Prescription #" + prescription.getPrescriptionNumber() + " has already been dispensed.");
        }

        double totalMedicineCharges = 0.0;
        List<BillItem> newBillItems = new ArrayList<>();

        for (PrescriptionItem item : prescription.getItems()) {
            Medicine med = item.getMedicine();
            if (med == null && item.getMedicineName() != null) {
                List<Medicine> matched = medicineRepo.findByNameContainingIgnoreCase(item.getMedicineName().trim());
                if (!matched.isEmpty()) {
                    med = matched.get(0);
                    item.setMedicine(med);
                }
            }

            double unitPrice = 50.0; // fallback standard unit price if item not in inventory
            int qty = item.getQuantity() != null && item.getQuantity() > 0 ? item.getQuantity() : 1;

            if (med != null) {
                unitPrice = med.getPrice() != null ? med.getPrice() : 50.0;
                // Decrement pharmacy inventory stock
                int currentStock = med.getStockQuantity() != null ? med.getStockQuantity() : 0;
                med.setStockQuantity(Math.max(0, currentStock - qty));
                medicineRepo.save(med);
            }

            double lineTotal = unitPrice * qty;
            totalMedicineCharges += lineTotal;
            item.setDispensed(true);

            BillItem billItem = BillItem.builder()
                    .description("Rx " + prescription.getPrescriptionNumber() + ": " + item.getMedicineName() + " (" + qty + " units)")
                    .category("Pharmacy / Medicine")
                    .quantity(qty)
                    .unitPrice(unitPrice)
                    .totalPrice(lineTotal)
                    .build();
            newBillItems.add(billItem);
        }

        // Link with billing
        List<Bill> patientBills = billRepo.findByPatientId(prescription.getPatient().getId());
        Bill targetBill = null;
        for (Bill b : patientBills) {
            if ("Pending".equalsIgnoreCase(b.getStatus()) || "Partial".equalsIgnoreCase(b.getStatus())) {
                targetBill = b;
                break;
            }
        }

        if (targetBill == null) {
            targetBill = Bill.builder()
                    .patient(prescription.getPatient())
                    .consultationFee(0.0)
                    .medicineCost(totalMedicineCharges)
                    .labTestCost(0.0)
                    .roomCharges(0.0)
                    .otherCharges(0.0)
                    .discount(0.0)
                    .paymentMethod("Cash")
                    .status("Pending")
                    .description("Automated pharmacy bill for Rx #" + prescription.getPrescriptionNumber())
                    .items(new ArrayList<>())
                    .build();
        } else {
            targetBill.setMedicineCost((targetBill.getMedicineCost() != null ? targetBill.getMedicineCost() : 0.0) + totalMedicineCharges);
        }

        for (BillItem bi : newBillItems) {
            targetBill.addItem(bi);
        }

        targetBill.calculateTotal();
        billRepo.save(targetBill);

        prescription.setStatus("Dispensed");
        return prescriptionRepo.save(prescription);
    }

    public void deleteById(Long id) {
        if (!prescriptionRepo.existsById(id)) {
            throw new RuntimeException("Prescription not found with ID: " + id);
        }
        prescriptionRepo.deleteById(id);
    }
}
