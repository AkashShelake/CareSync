package com.akash.CareSync.appointments.controller;

import com.akash.CareSync.appointments.entity.Appointment;
import com.akash.CareSync.appointments.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rest/v1/appointments")
public class AppointmentController {
    AppointmentService appointmentService;
    AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<Appointment> getAppointmentsList() {
        return appointmentService.getAllAppointments();
    }

    @PostMapping
    public Appointment addAppointment(@RequestBody Appointment appointment) {
        appointment.setStatus(0);
        return appointmentService.addAppointment(appointment);
    }

    @PutMapping
    public Appointment updateAppointment(@RequestBody Appointment appointment) {
        return appointmentService.updateAppointment(appointment);
    }

    @DeleteMapping("{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }
}
