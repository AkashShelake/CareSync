package com.akash.CareSync.appointments.entity;

import com.akash.CareSync.base.BaseEntity;
import com.akash.CareSync.practicemember.entity.PracticeMember;
import com.akash.CareSync.practicepatient.entity.PracticePatient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
@Entity
public class Appointment extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "patient_id")
    private PracticePatient patient;

    @OneToOne
    @JoinColumn(name = "member_id")
    private PracticeMember member;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date appointment_date;

    private Integer duration;
    private String reason;
    private Integer status;
    private Long encounter_id;

}
