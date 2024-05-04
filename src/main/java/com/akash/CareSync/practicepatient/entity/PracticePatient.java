package com.akash.CareSync.practicepatient.entity;

import com.akash.CareSync.base.BaseEntity;

//@Table(name = "PracticePatientsList")
//@Entity
public class  PracticePatient extends BaseEntity {
    String first_name;
    String last_name;
    String gender;
    String date_of_birth;
    String blood_group;
    Long postaladdress_id;
    Long contactdetails_id;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public Long getPostaladdress_id() {
        return postaladdress_id;
    }

    public void setPostaladdress_id(Long postaladdress_id) {
        this.postaladdress_id = postaladdress_id;
    }

    public Long getContactdetails_id() {
        return contactdetails_id;
    }

    public void setContactdetails_id(Long contactdetails_id) {
        this.contactdetails_id = contactdetails_id;
    }

    @Override
    public String toString() {
        return "PracticePatient{" +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", gender='" + gender + '\'' +
                ", date_of_birth='" + date_of_birth + '\'' +
                ", blood_group='" + blood_group + '\'' +
                ", postaladdress_id=" + postaladdress_id +
                ", contactdetails_id=" + contactdetails_id +
                '}';
    }
}
