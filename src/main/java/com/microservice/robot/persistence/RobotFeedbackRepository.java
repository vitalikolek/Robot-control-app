package com.microservice.robot.persistence;

import com.microservice.robot.data.Robot;
import com.microservice.robot.data.RobotFeedback;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RobotFeedbackRepository extends MongoRepository<RobotFeedback, String> {

    RobotFeedback findTopByOrderByIdDesc();

    List<RobotFeedback> findAllByRobotId(Robot robotId);

    RobotFeedback findById(Integer id);
}
