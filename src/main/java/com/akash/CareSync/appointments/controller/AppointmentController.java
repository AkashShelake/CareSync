package com.akash.CareSync.appointments.controller;

import com.akash.CareSync.appointments.entity.Appointment;
import com.akash.CareSync.appointments.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/rest/v1/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<Appointment> getAppointmentsList() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("{id}")
    public Appointment getMember(@PathVariable Long id) {
        return appointmentService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Appointment Id"));
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

    @GetMapping("/day")
    public ResponseEntity<List<Appointment>> getAppointmentsByDay(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDay(date);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/range")
    public ResponseEntity<List<Appointment>> getAppointmentsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDateRange(fromDate, toDate);
        return ResponseEntity.ok(appointments);
    }
}
