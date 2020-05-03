package org.ebuy.repository;

import org.ebuy.model.ConfirmationToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Ozgur Ustun on May, 2020
 */
@Repository
public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken, String> {

    Optional<ConfirmationToken> findByGeneratedConfirmationToken(String token);

}
