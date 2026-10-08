package com.hospital.management.controller;

import com.hospital.management.model.Bill;
import com.hospital.management.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @GetMapping
    public String listBills(@RequestParam(required = false) String status, Model model) {
        if (status != null && !status.trim().isEmpty()) {
            model.addAttribute("bills", billingService.findByStatus(status));
            model.addAttribute("selectedStatus", status);
        } else {
            model.addAttribute("bills", billingService.findAll());
        }
        model.addAttribute("totalRevenue", billingService.getTotalRevenue());
        model.addAttribute("totalCollected", billingService.getTotalCollected());
        model.addAttribute("totalPending", billingService.getTotalPending());
        return "billing/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("bill", new Bill());
        model.addAttribute("patients", billingService.getAllPatients());
        return "billing/form";
    }

    @PostMapping("/save")
    public String saveBill(@ModelAttribute Bill bill,
                           @RequestParam Long patientId,
                           RedirectAttributes redirectAttributes) {
        billingService.save(bill, patientId);
        redirectAttributes.addFlashAttribute("success", "Bill saved successfully!");
        return "redirect:/billing";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("bill", billingService.findById(id));
        model.addAttribute("patients", billingService.getAllPatients());
        return "billing/form";
    }

    @GetMapping("/view/{id}")
    public String viewBill(@PathVariable Long id, Model model) {
        model.addAttribute("bill", billingService.findById(id));
        return "billing/view";
    }

    @GetMapping("/delete/{id}")
    public String deleteBill(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        billingService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Bill deleted!");
        return "redirect:/billing";
    }
}
