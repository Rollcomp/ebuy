package org.ebuy.dto;

/**
 * Created by Ozgur Ustun on May, 2020
 */
public class MailDto {

    private String email;
    private String token;

    public MailDto() {
    }

    public MailDto(String email, String token) {
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
