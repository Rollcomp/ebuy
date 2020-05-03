package org.ebuy.dto;

import org.ebuy.constant.Authority;
import org.ebuy.constant.Gender;
import org.ebuy.constant.Membership;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by Ozgur Ustun on May, 2020
 */
public class UserDto {

    private String email;

    private String password;

    private boolean enabled;

    public UserDto(String email, String password, boolean enabled, Set<Authority> authorities) {
        this.email = email;
        this.password = password;
        this.enabled = enabled;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
