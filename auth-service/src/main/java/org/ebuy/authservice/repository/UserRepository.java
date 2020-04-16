package org.ebuy.authservice.repository;

import org.ebuy.authservice.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByEmailIgnoreCase(String email);
}
