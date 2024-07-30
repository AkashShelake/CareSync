package com.akash.CareSync.practicepatient.entity;

import com.akash.CareSync.base.BaseEntity;
import com.akash.CareSync.contactdetails.entity.ContactDetails;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class PracticePatient extends BaseEntity {
    String first_name;
    String last_name;
    String gender;
    String date_of_birth;
    String blood_group;
    String status;
    Long postaladdress_id;
    Long contactdetails_id;
    @OneToOne(cascade = CascadeType.ALL)
    ContactDetails contactDetails;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    @Override
    public String toString() {
        return "PracticePatient{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", gender='" + gender + '\'' +
                ", date_of_birth='" + date_of_birth + '\'' +
                ", blood_group='" + blood_group + '\'' +
                ", status='" + status + '\'' +
                ", postaladdress_id=" + postaladdress_id +
                ", contactdetails_id=" + contactdetails_id +
                ", contactDetails=" + contactDetails +
                '}';
    }
}
