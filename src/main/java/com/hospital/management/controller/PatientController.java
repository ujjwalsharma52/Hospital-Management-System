package com.hospital.management.controller;

import com.hospital.management.model.Patient;
import com.hospital.management.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public String listPatients(@RequestParam(required = false) String search, Model model) {
        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("patients", patientService.searchByName(search));
            model.addAttribute("search", search);
        } else {
            model.addAttribute("patients", patientService.findAll());
        }
        return "patients/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patients/form";
    }

    @PostMapping("/save")
    public String savePatient(@ModelAttribute Patient patient, RedirectAttributes redirectAttributes) {
        patientService.save(patient);
        redirectAttributes.addFlashAttribute("success",
                patient.getId() == null ? "Patient registered successfully!" : "Patient updated successfully!");
        return "redirect:/patients";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("patient", patientService.findById(id));
        return "patients/form";
    }

    @GetMapping("/view/{id}")
    public String viewPatient(@PathVariable Long id, Model model) {
        model.addAttribute("patient", patientService.findById(id));
        return "patients/view";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        patientService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Patient deleted successfully!");
        return "redirect:/patients";
    }
}
