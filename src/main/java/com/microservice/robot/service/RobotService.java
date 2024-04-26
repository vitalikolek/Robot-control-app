package com.microservice.robot.service;

import ch.qos.logback.core.spi.SequenceNumberGenerator;
import com.microservice.robot.data.Robot;
import com.microservice.robot.data.Status;
import com.microservice.robot.data.User;
import com.microservice.robot.persistence.RobotRepository;
import com.microservice.robot.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RobotService {

    private UserRepository userRepository;
    private RobotRepository robotRepository;
    private SequenceNumberGenerator sequenceNumberGenerator;

    @Autowired
    public RobotService(UserRepository userRepository, RobotRepository robotRepository) {
        this.userRepository = userRepository;
        this.robotRepository = robotRepository;
    }

    public void saveRobot(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        Robot robot = new Robot();

        Robot lastDocument = robotRepository.findTopByOrderByIdDesc();
        int newIndex = 1;
        if (lastDocument != null) {
            newIndex = robot.getId() + 1;
        }
        robot.setId(newIndex);

        robot.setAdministrator(user.get());
        robot.setStatus(Status.OFFLINE);
        robot.setCurrentTask("Robot offline");
        robot.setLastCheckIn(Instant.now());

        robotRepository.save(robot);
    }
}
