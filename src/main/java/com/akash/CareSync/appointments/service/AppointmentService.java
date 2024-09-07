package com.akash.CareSync.appointments.service;

import com.akash.CareSync.appointments.entity.Appointment;
import com.akash.CareSync.appointments.entity.AppointmentStatus;
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
        appointment.setStatus(AppointmentStatus.REQUESTED.getStatusCode());
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Appointment appointment) {
        Optional<Appointment> optionalAppointment = getById(appointment.getId());
        if (optionalAppointment.isPresent()) {
            Appointment existingAppointment = optionalAppointment.get();

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

            return appointmentRepository.save(existingAppointment);
        }
        throw new RuntimeException("Appointment not found for id: " + appointment.getId());
    }


    public void deleteAppointment(Long appointmentId) {
        Optional<Appointment> optionalAppointment = getById(appointmentId);
        if(optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            appointment.setStatus(AppointmentStatus.DELETED.getStatusCode());
            appointment.setUpdatedAt(Instant.now());
            appointmentRepository.save(appointment);
        }
        throw new RuntimeException("Appointment not found for id: " + appointmentId);
    }

    public Appointment cancelAppointment(Long appointmentId) {
        Optional<Appointment> optionalAppointment = getById(appointmentId);
        if(optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            appointment.setStatus(AppointmentStatus.CANCELLED.getStatusCode());
            appointment.setUpdatedAt(Instant.now());
            return appointmentRepository.save(appointment);
        }
        throw new RuntimeException("Appointment not found for id: " + appointmentId);
    }

    public List<Appointment> getAppointmentsByDay(Date date) {
        return appointmentRepository.findAllByDate(date);
    }

    public List<Appointment> getAppointmentsByDateRange(Date fromDate, Date toDate) {
        return appointmentRepository.findAllByDateRange(fromDate, toDate);
    }

    public List<Appointment> getAppointmentsByDateRangeAndStatus(Date fromDate, Date toDate, Integer status) {
        return appointmentRepository.findAllByDateRangeAndStatus(fromDate, toDate, status);
    }

    public List<Appointment> getFilteredAppointments(List<Integer> statuses, Long memberId, Date fromDate, Date toDate) {
        return appointmentRepository.findByFilters(statuses, memberId, fromDate, toDate);
    }

    public List<Appointment> getWaitingList(){
        return appointmentRepository.findByStatus(AppointmentStatus.WAITING.getStatusCode());
    }
    public Appointment addToWaitList(Long appointmentId){
        Optional<Appointment> optionalAppointment = getById(appointmentId);
        if(optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            appointment.setStatus(AppointmentStatus.WAITING.getStatusCode());
            appointment.setUpdatedAt(Instant.now());
            return appointmentRepository.save(appointment);
        }
        throw new RuntimeException("Appointment not found for id: " + appointmentId);
    }
}
