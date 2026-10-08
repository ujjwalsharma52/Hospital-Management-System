package com.hospital.management.controller;

import com.hospital.management.model.LabReport;
import com.hospital.management.service.LabReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/lab")
public class LabReportController {

    @Autowired
    private LabReportService labReportService;

    @GetMapping
    public String listReports(@RequestParam(required = false) String status, Model model) {
        if (status != null && !status.trim().isEmpty()) {
            model.addAttribute("reports", labReportService.findByStatus(status));
            model.addAttribute("selectedStatus", status);
        } else {
            model.addAttribute("reports", labReportService.findAll());
        }
        return "lab/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("report", new LabReport());
        model.addAttribute("patients", labReportService.getAllPatients());
        model.addAttribute("doctors", labReportService.getAllDoctors());
        return "lab/form";
    }

    @PostMapping("/save")
    public String saveReport(@ModelAttribute LabReport report,
                             @RequestParam Long patientId,
                             @RequestParam(required = false) Long doctorId,
                             RedirectAttributes redirectAttributes) {
        labReportService.save(report, patientId, doctorId);
        redirectAttributes.addFlashAttribute("success", "Lab report saved successfully!");
        return "redirect:/lab";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("report", labReportService.findById(id));
        model.addAttribute("patients", labReportService.getAllPatients());
        model.addAttribute("doctors", labReportService.getAllDoctors());
        return "lab/form";
    }

    @GetMapping("/view/{id}")
    public String viewReport(@PathVariable Long id, Model model) {
        model.addAttribute("report", labReportService.findById(id));
        return "lab/view";
    }

    @GetMapping("/delete/{id}")
    public String deleteReport(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        labReportService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Lab report deleted!");
        return "redirect:/lab";
    }
}
