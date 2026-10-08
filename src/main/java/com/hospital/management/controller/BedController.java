package com.hospital.management.controller;

import com.hospital.management.model.Bed;
import com.hospital.management.model.Ward;
import com.hospital.management.service.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/beds")
public class BedController {

    @Autowired
    private BedService bedService;

    @GetMapping
    public String listBeds(Model model) {
        model.addAttribute("beds", bedService.findAllBeds());
        model.addAttribute("wards", bedService.findAllWards());
        model.addAttribute("availableCount", bedService.countAvailableBeds());
        model.addAttribute("occupiedCount", bedService.countOccupiedBeds());
        model.addAttribute("totalCount", bedService.countAllBeds());
        return "beds/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("bed", new Bed());
        model.addAttribute("wards", bedService.findAllWards());
        model.addAttribute("patients", bedService.getAllPatients());
        return "beds/form";
    }

    @PostMapping("/save")
    public String saveBed(@ModelAttribute Bed bed,
                          @RequestParam Long wardId,
                          @RequestParam(required = false) Long patientId,
                          RedirectAttributes redirectAttributes) {
        bedService.saveBed(bed, wardId, patientId);
        redirectAttributes.addFlashAttribute("success", "Bed saved successfully!");
        return "redirect:/beds";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("bed", bedService.findBedById(id));
        model.addAttribute("wards", bedService.findAllWards());
        model.addAttribute("patients", bedService.getAllPatients());
        return "beds/form";
    }

    @GetMapping("/discharge/{id}")
    public String dischargeBed(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bedService.discharge(id);
        redirectAttributes.addFlashAttribute("success", "Patient discharged from bed!");
        return "redirect:/beds";
    }

    @GetMapping("/delete/{id}")
    public String deleteBed(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bedService.deleteBed(id);
        redirectAttributes.addFlashAttribute("success", "Bed deleted!");
        return "redirect:/beds";
    }

    // ---- Ward Management ----
    @GetMapping("/wards")
    public String listWards(Model model) {
        model.addAttribute("wards", bedService.findAllWards());
        return "beds/wards";
    }

    @GetMapping("/wards/add")
    public String showAddWardForm(Model model) {
        model.addAttribute("ward", new Ward());
        return "beds/ward-form";
    }

    @PostMapping("/wards/save")
    public String saveWard(@ModelAttribute Ward ward, RedirectAttributes redirectAttributes) {
        bedService.saveWard(ward);
        redirectAttributes.addFlashAttribute("success", "Ward saved successfully!");
        return "redirect:/beds/wards";
    }
}
