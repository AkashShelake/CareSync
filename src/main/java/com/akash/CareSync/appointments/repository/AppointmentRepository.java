package com.akash.CareSync.appointments.repository;
import com.akash.CareSync.appointments.entity.Appointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long>{
}
