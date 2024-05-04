package com.akash.CareSync.practicemember.entity;

import com.akash.CareSync.base.BaseEntity;
import jakarta.persistence.Entity;

@Entity
public class PracticeMember extends BaseEntity {

    String first_name;
    String last_name;
    String gender;
    String blood_group;
    String degree;
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

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
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
}
