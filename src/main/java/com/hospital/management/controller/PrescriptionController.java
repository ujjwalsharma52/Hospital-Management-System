package com.hospital.management.controller;

import com.hospital.management.model.*;
import com.hospital.management.repository.AppointmentRepository;
import com.hospital.management.repository.DoctorRepository;
import com.hospital.management.repository.MedicineRepository;
import com.hospital.management.repository.PatientRepository;
import com.hospital.management.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private MedicineRepository medicineRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    @GetMapping
    public String listPrescriptions(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            Model model) {

        List<Prescription> prescriptions;
        if (search != null && !search.trim().isEmpty()) {
            prescriptions = prescriptionService.search(search);
        } else if (status != null && !status.trim().isEmpty() && !"ALL".equalsIgnoreCase(status)) {
            prescriptions = prescriptionService.findByStatus(status);
        } else {
            prescriptions = prescriptionService.findAll();
        }

        model.addAttribute("prescriptions", prescriptions);
        model.addAttribute("search", search);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("pendingDispenseCount", prescriptionService.countPendingDispense());
        return "prescriptions/list";
    }

    @GetMapping("/add")
    public String showAddForm(
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) Long appointmentId,
            Principal principal,
            Model model) {

        Prescription prescription = new Prescription();
        prescription.setPrescriptionDate(LocalDate.now());

        Long preSelectedDoctorId = null;
        if (principal != null) {
            Optional<Doctor> doctorOpt = doctorRepo.findByUserUsername(principal.getName());
            if (doctorOpt.isPresent()) {
                preSelectedDoctorId = doctorOpt.get().getId();
            }
        }

        // If appointment provided, pull patient and doctor from it
        if (appointmentId != null) {
            appointmentRepo.findById(appointmentId).ifPresent(apt -> {
                prescription.setAppointment(apt);
                if (apt.getPatient() != null) {
                    model.addAttribute("preSelectedPatientId", apt.getPatient().getId());
                }
                if (apt.getDoctor() != null) {
                    model.addAttribute("preSelectedDoctorId", apt.getDoctor().getId());
                }
            });
        }

        if (patientId != null) {
            model.addAttribute("preSelectedPatientId", patientId);
        }
        if (preSelectedDoctorId != null && !model.containsAttribute("preSelectedDoctorId")) {
            model.addAttribute("preSelectedDoctorId", preSelectedDoctorId);
        }

        model.addAttribute("prescription", prescription);
        model.addAttribute("patients", patientRepo.findAll());
        model.addAttribute("doctors", doctorRepo.findAll());
        model.addAttribute("medicines", medicineRepo.findAll());
        return "prescriptions/form";
    }

    @PostMapping("/save")
    public String savePrescription(
            @ModelAttribute Prescription prescription,
            @RequestParam Long patientId,
            @RequestParam Long doctorId,
            @RequestParam(required = false) Long appointmentId,
            @RequestParam(name = "medName", required = false) List<String> medNames,
            @RequestParam(name = "medId", required = false) List<Long> medIds,
            @RequestParam(name = "dosage", required = false) List<String> dosages,
            @RequestParam(name = "frequency", required = false) List<String> frequencies,
            @RequestParam(name = "duration", required = false) List<String> durations,
            @RequestParam(name = "timing", required = false) List<String> timings,
            @RequestParam(name = "quantity", required = false) List<Integer> quantities,
            @RequestParam(name = "instructions", required = false) List<String> instructions,
            RedirectAttributes redirectAttributes) {

        try {
            List<PrescriptionItem> items = new ArrayList<>();
            if (medNames != null && !medNames.isEmpty()) {
                for (int i = 0; i < medNames.size(); i++) {
                    String name = medNames.get(i);
                    if (name != null && !name.trim().isEmpty()) {
                        PrescriptionItem item = new PrescriptionItem();
                        item.setMedicineName(name.trim());

                        if (medIds != null && i < medIds.size() && medIds.get(i) != null && medIds.get(i) > 0) {
                            medicineRepo.findById(medIds.get(i)).ifPresent(item::setMedicine);
                        }

                        if (dosages != null && i < dosages.size()) {
                            item.setDosage(dosages.get(i));
                        }
                        if (frequencies != null && i < frequencies.size()) {
                            item.setFrequency(frequencies.get(i));
                        }
                        if (durations != null && i < durations.size()) {
                            item.setDuration(durations.get(i));
                        }
                        if (timings != null && i < timings.size()) {
                            item.setTiming(timings.get(i));
                        }
                        if (quantities != null && i < quantities.size() && quantities.get(i) != null) {
                            item.setQuantity(quantities.get(i));
                        } else {
                            item.setQuantity(10);
                        }
                        if (instructions != null && i < instructions.size()) {
                            item.setInstructions(instructions.get(i));
                        }
                        items.add(item);
                    }
                }
            }

            Prescription saved = prescriptionService.save(prescription, patientId, doctorId, appointmentId, items);
            redirectAttributes.addFlashAttribute("success", "Prescription #" + saved.getPrescriptionNumber() + " created successfully!");
            return "redirect:/prescriptions/view/" + saved.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to save prescription: " + e.getMessage());
            return "redirect:/prescriptions";
        }
    }

    @GetMapping("/view/{id}")
    public String viewPrescription(@PathVariable Long id, Model model) {
        Prescription prescription = prescriptionService.findById(id);
        model.addAttribute("prescription", prescription);
        return "prescriptions/view";
    }

    @PostMapping("/dispense/{id}")
    public String dispensePrescription(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Prescription dispensed = prescriptionService.dispensePrescription(id);
            redirectAttributes.addFlashAttribute("success",
                    "Prescription #" + dispensed.getPrescriptionNumber() + " successfully dispensed! Pharmacy stock deducted and patient bill updated.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Dispensing failed: " + e.getMessage());
        }
        return "redirect:/prescriptions/view/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deletePrescription(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            prescriptionService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Prescription deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting prescription: " + e.getMessage());
        }
        return "redirect:/prescriptions";
    }
}
