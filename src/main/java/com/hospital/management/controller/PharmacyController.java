package com.hospital.management.controller;

import com.hospital.management.model.Medicine;
import com.hospital.management.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pharmacy")
public class PharmacyController {

    @Autowired
    private PharmacyService pharmacyService;

    @GetMapping
    public String listMedicines(@RequestParam(required = false) String search, Model model) {
        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("medicines", pharmacyService.searchByName(search));
            model.addAttribute("search", search);
        } else {
            model.addAttribute("medicines", pharmacyService.findAll());
        }
        model.addAttribute("lowStockMedicines", pharmacyService.findLowStockMedicines());
        return "pharmacy/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("medicine", new Medicine());
        return "pharmacy/form";
    }

    @PostMapping("/save")
    public String saveMedicine(@ModelAttribute Medicine medicine, RedirectAttributes redirectAttributes) {
        pharmacyService.save(medicine);
        redirectAttributes.addFlashAttribute("success", "Medicine saved successfully!");
        return "redirect:/pharmacy";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("medicine", pharmacyService.findById(id));
        return "pharmacy/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteMedicine(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        pharmacyService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Medicine deleted!");
        return "redirect:/pharmacy";
    }
}
