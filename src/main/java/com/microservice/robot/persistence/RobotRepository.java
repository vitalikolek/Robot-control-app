package com.microservice.robot.persistence;

import com.microservice.robot.data.Robot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RobotRepository extends MongoRepository<Robot, String> {

    Robot findTopByOrderByIdDesc();

    List<Robot> findAllByAdministratorId(String administratorId);

    Robot findById(Integer id);
}
