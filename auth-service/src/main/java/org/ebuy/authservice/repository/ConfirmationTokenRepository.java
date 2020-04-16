package org.ebuy.authservice.repository;

import org.ebuy.authservice.entity.ConfirmationToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken, String> {

    ConfirmationToken findByGeneratedConfirmationToken(String token);

}
