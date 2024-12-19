package com.akash.CareSync.chartnotes.entity;

import com.akash.CareSync.base.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
public class Encounter extends BaseEntity {
    Date date;
    Long appointment_id;
    Long provider_id;
    Long patient_id;
    Integer temperature;
    Integer blood_pressure;
    Integer heart_rate;
    String symptoms;
    String prescriptions;
    String treatment_notes;
    String status;

    @Override
    public String toString() {
        return "Encounter{" +
                "date=" + date +
                ", appointment_id=" + appointment_id +
                ", provider_id=" + provider_id +
                ", patient_id=" + patient_id +
                ", temperature=" + temperature +
                ", blood_pressure=" + blood_pressure +
                ", heart_rate=" + heart_rate +
                ", symptoms='" + symptoms + '\'' +
                ", prescriptions='" + prescriptions + '\'' +
                ", treatment_notes='" + treatment_notes + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
