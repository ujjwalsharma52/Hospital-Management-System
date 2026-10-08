package com.hospital.management.controller;

import com.hospital.management.model.Appointment;
import com.hospital.management.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public String listAppointments(@RequestParam(required = false) String status, Model model) {
        if (status != null && !status.trim().isEmpty()) {
            model.addAttribute("appointments", appointmentService.findByStatus(status));
            model.addAttribute("selectedStatus", status);
        } else {
            model.addAttribute("appointments", appointmentService.findAll());
        }
        return "appointments/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("patients", appointmentService.getAllPatients());
        model.addAttribute("doctors", appointmentService.getAllDoctors());
        return "appointments/form";
    }

    @PostMapping("/save")
    public String saveAppointment(@ModelAttribute Appointment appointment,
                                  @RequestParam Long patientId,
                                  @RequestParam Long doctorId,
                                  RedirectAttributes redirectAttributes) {
        appointmentService.save(appointment, patientId, doctorId);
        redirectAttributes.addFlashAttribute("success", "Appointment saved successfully!");
        return "redirect:/appointments";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("appointment", appointmentService.findById(id));
        model.addAttribute("patients", appointmentService.getAllPatients());
        model.addAttribute("doctors", appointmentService.getAllDoctors());
        return "appointments/form";
    }

    @GetMapping("/cancel/{id}")
    public String cancelAppointment(@PathVariable Long id,
                                    @RequestParam(required = false) String redirect,
                                    RedirectAttributes redirectAttributes) {
        appointmentService.cancel(id);
        redirectAttributes.addFlashAttribute("success", "Appointment cancelled!");
        return "redirect:" + (redirect != null && !redirect.trim().isEmpty() ? redirect : "/appointments");
    }

    @GetMapping("/complete/{id}")
    public String completeAppointment(@PathVariable Long id,
                                      @RequestParam(required = false) String redirect,
                                      RedirectAttributes redirectAttributes) {
        appointmentService.complete(id);
        redirectAttributes.addFlashAttribute("success", "Appointment marked as completed!");
        return "redirect:" + (redirect != null && !redirect.trim().isEmpty() ? redirect : "/appointments");
    }

    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        appointmentService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Appointment deleted!");
        return "redirect:/appointments";
    }
}
