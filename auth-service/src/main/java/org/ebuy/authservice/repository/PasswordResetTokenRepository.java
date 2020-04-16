package org.ebuy.authservice.repository;

import org.ebuy.authservice.entity.PasswordResetToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken, String> {

    PasswordResetToken findByGeneratedPasswordResetToken(String token);

}
