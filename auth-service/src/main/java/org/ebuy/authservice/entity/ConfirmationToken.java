package org.ebuy.authservice.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Document(collection = "verificationToken")
public class ConfirmationToken {

    @Id
    private String id;

    private String generatedConfirmationToken;

    private User user;

    private LocalDateTime createdAt;

    public ConfirmationToken(User user) {
        this.user = user;
        createdAt = LocalDateTime.now();
        generatedConfirmationToken = UUID.randomUUID().toString();
    }


    public ConfirmationToken(String id, String generatedConfirmationToken, User user, LocalDateTime createdAt) {
        this.id = id;
        this.generatedConfirmationToken = generatedConfirmationToken;
        this.user = user;
        this.createdAt = createdAt;
    }

    @PersistenceConstructor
    public ConfirmationToken() {
    }
}
