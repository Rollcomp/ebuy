package org.ebuy.mailservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Ozgur Ustun on May, 2020
 */
public class ReceiveMailDto {

    private String email;
    private String token;

    public ReceiveMailDto() {
    }

    public ReceiveMailDto(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
