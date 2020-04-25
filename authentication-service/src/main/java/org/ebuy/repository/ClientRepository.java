package org.ebuy.repository;

import org.ebuy.model.client.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Burak Köken on 22.4.2020.
 */
@Repository
public interface ClientRepository extends MongoRepository<Client, String> {

    Optional<Client> findByClientId(String clientId);

}
