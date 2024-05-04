package com.akash.CareSync.appointments.service;

import com.akash.CareSync.appointments.entity.Appointment;
import com.akash.CareSync.appointments.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

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
        Optional<Appointment> optionalPracticeMember = getById(appointment.getId());
        optionalPracticeMember.ifPresent(
                appontmentDetails -> {
                    appontmentDetails.setMember_id(appointment.getMember_id());
                    appontmentDetails.setPatient_id(appointment.getPatient_id());
                    appontmentDetails.setReason(appointment.getReason());
                    appontmentDetails.setAppointment_date(appointment.getAppointment_date());
                }
        );
        return optionalPracticeMember.orElse(null);
    }

    public void deleteAppointment(Long id) {
        Optional<Appointment> appointment = getById(id);
        appointment.ifPresent(appointmentRepository::delete);
    }
}
