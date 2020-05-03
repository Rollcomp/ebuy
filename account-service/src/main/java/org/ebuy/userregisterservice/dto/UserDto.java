package org.ebuy.userregisterservice.dto;

import java.util.Set;

/**
 * Created by Ozgur Ustun on May, 2020
 */
public class UserDto {

    private String email;

    private String password;


    public UserDto(String email, String password, boolean enabled) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
