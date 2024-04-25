package com.microservice.auth.persistence;

import com.microservice.auth.data.Robot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RobotRepository extends MongoRepository<Robot, String> {

    Robot findTopByOrderByIdDesc();
}
