package com.hospital.management.controller;

import com.hospital.management.model.Doctor;
import com.hospital.management.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public String listDoctors(@RequestParam(required = false) String search, Model model) {
        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("doctors", doctorService.searchByName(search));
            model.addAttribute("search", search);
        } else {
            model.addAttribute("doctors", doctorService.findAll());
        }
        return "doctors/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctors/form";
    }

    @PostMapping("/save")
    public String saveDoctor(@ModelAttribute Doctor doctor, RedirectAttributes redirectAttributes) {
        doctorService.save(doctor);
        redirectAttributes.addFlashAttribute("success", "Doctor saved successfully!");
        return "redirect:/doctors";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("doctor", doctorService.findById(id));
        return "doctors/form";
    }

    @GetMapping("/view/{id}")
    public String viewDoctor(@PathVariable Long id, Model model) {
        model.addAttribute("doctor", doctorService.findById(id));
        return "doctors/view";
    }

    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        doctorService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Doctor deleted successfully!");
        return "redirect:/doctors";
    }
}
