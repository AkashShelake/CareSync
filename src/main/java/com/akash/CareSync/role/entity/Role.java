package com.akash.CareSync.role.entity;

import com.akash.CareSync.base.BaseEntity;
import jakarta.persistence.Entity;

@Entity
public class Role extends BaseEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
