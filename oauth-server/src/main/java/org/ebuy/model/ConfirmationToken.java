package org.ebuy.model;

import org.ebuy.model.user.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Ozgur Ustun on May, 2020
 */

@Document(collection = "registerVerificationToken")
public class ConfirmationToken {

    @Id
    private String id;

    private String generatedConfirmationToken;

    private User user;

    private LocalDateTime createdAt;

    public ConfirmationToken() {
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGeneratedConfirmationToken() {
        return generatedConfirmationToken;
    }

    public void setGeneratedConfirmationToken(String generatedConfirmationToken) {
        this.generatedConfirmationToken = generatedConfirmationToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
