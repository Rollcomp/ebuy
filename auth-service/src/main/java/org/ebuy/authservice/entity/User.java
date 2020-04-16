package org.ebuy.authservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Data
@Document(collection = "user")
public class User {

    public User() {
        super();
        this.enabled = false;
    }

    @Id
    private String id;

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String mobile;
    //By Default it is false, when user click activation link, it turns true
    public boolean enabled;

    private List<String> roles;


}
