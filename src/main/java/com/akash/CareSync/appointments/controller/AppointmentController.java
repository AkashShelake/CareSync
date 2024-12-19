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
    public Appointment getAppointment(@PathVariable Long id) {
        return appointmentService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Appointment Id"));
    }

    @PostMapping
    public Appointment addAppointment(@RequestBody Appointment appointment) {
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

    @PostMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.noContent().build();
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

    @GetMapping("/range/status")
    public ResponseEntity<List<Appointment>> getAppointmentsByDateRangeAndStatus(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam Integer status) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDateRangeAndStatus(fromDate, toDate, status);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Appointment>> getAppointmentsByFilters(
            @RequestParam(required = false) List<Integer> statuses,
            @RequestParam(required = false) Long memberId,
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        List<Appointment> appointments = appointmentService.getFilteredAppointments(statuses, memberId, patientId, fromDate, toDate);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/waiting/list")
    public ResponseEntity<List<Appointment>> getWaitingList() {
        List<Appointment> waitingList = appointmentService.getWaitingList();
        return ResponseEntity.ok(waitingList);
    }

    @PostMapping("/waiting/add")
    public ResponseEntity<Appointment> addToWaitingList(@RequestParam Long appointmentId) {
        Appointment appointment = appointmentService.addToWaitList(appointmentId);
        return ResponseEntity.ok(appointment);
    }
}
