package org.ebuy.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ebuy.authservice.validation.PasswordMatches;
import org.ebuy.authservice.validation.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class UserDto {

    @NotEmpty
    @NotNull
    private String firstName;
    @NotEmpty
    @NotNull
    private String lastName;
    @NotEmpty
    @NotNull
    private String password;
    @NotEmpty
    @NotNull
    private String matchingPassword;

    @ValidEmail
    @NotEmpty
    @NotNull
    private String email;

    @NotNull
    @NotEmpty
    private String mobile;

    private List<String> roles;

}
