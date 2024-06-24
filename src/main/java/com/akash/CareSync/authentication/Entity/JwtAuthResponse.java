package com.akash.CareSync.authentication.Entity;

import com.akash.CareSync.practicemember.entity.PracticeMember;

public class JwtAuthResponse {
    private String token;
    private PracticeMember practiceMember;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public PracticeMember getPracticeMember() {
        return practiceMember;
    }

    public void setPracticeMember(PracticeMember practiceMember) {
        this.practiceMember = practiceMember;
    }
}
