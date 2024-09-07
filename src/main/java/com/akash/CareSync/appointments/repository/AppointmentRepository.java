package com.akash.CareSync.appointments.repository;
import com.akash.CareSync.appointments.entity.Appointment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

    List<Appointment> findByStatus(Integer status);

    @Query("SELECT a FROM Appointment a WHERE DATE(a.appointment_date) = :date")
    List<Appointment> findAllByDate(@Param("date") Date date);

    @Query("SELECT a FROM Appointment a WHERE a.appointment_date BETWEEN :fromDate AND :toDate")
    List<Appointment> findAllByDateRange(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT a FROM Appointment a WHERE a.appointment_date BETWEEN :fromDate AND :toDate AND a.status = :status")
    List<Appointment> findAllByDateRangeAndStatus(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("status") Integer status);
}
