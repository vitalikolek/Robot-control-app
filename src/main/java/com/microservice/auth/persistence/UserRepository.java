package com.microservice.auth.persistence;

import com.microservice.auth.data.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

  Optional<User> findByEmail(String email);
}
