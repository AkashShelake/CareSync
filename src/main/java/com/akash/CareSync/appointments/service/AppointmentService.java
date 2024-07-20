package com.akash.CareSync.appointments.service;

import com.akash.CareSync.appointments.entity.Appointment;
import com.akash.CareSync.appointments.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> getAllAppointments() {
        return (List<Appointment>) appointmentRepository.findAll();
    }

    public Optional<Appointment> getById(Long id) {
        return appointmentRepository.findById(id);
    }

    public Appointment addAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Appointment appointment) {
        Optional<Appointment> optionalAppointment = getById(appointment.getId());
        optionalAppointment.ifPresent(existingAppointment -> {
            if (appointment.getMember_id() != null) {
                existingAppointment.setMember_id(appointment.getMember_id());
            }
            if (appointment.getPatient_id() != null) {
                existingAppointment.setPatient_id(appointment.getPatient_id());
            }
            if (appointment.getReason() != null) {
                existingAppointment.setReason(appointment.getReason());
            }
            if (appointment.getAppointment_date() != null) {
                existingAppointment.setAppointment_date(appointment.getAppointment_date());
            }
            existingAppointment.setUpdatedAt(Instant.now());
        });
        return optionalAppointment.orElse(null);
    }

    public void deleteAppointment(Long id) {
        Optional<Appointment> appointment = getById(id);
        appointment.ifPresent(appointmentRepository::delete);
    }

    public List<Appointment> getAppointmentsByDay(Date date) {
        return appointmentRepository.findAllByDate(date);
    }

    public List<Appointment> getAppointmentsByDateRange(Date fromDate, Date toDate) {
        return appointmentRepository.findAllByDateRange(fromDate, toDate);
    }
}
