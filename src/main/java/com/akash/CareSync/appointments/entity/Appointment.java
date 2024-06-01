package com.akash.CareSync.appointments.entity;

import com.akash.CareSync.base.BaseEntity;
import jakarta.persistence.Entity;

import java.util.Date;

@Entity
public class Appointment extends BaseEntity {
    Long patient_id;
    Long member_id;
    String appointment_date;
    String reason;
    Integer status;

    public Long getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Long patient_id) {
        this.patient_id = patient_id;
    }

    public Long getMember_id() {
        return member_id;
    }

    public void setMember_id(Long member_id) {
        this.member_id = member_id;
    }

    public String getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(String appointment_date) {
        this.appointment_date = appointment_date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "patient_id=" + patient_id +
                ", member_id=" + member_id +
                ", appointment_date=" + appointment_date +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
