package com.microservice.auth.service;

import ch.qos.logback.core.spi.SequenceNumberGenerator;
import com.microservice.auth.data.Robot;
import com.microservice.auth.data.Status;
import com.microservice.auth.data.User;
import com.microservice.auth.persistence.RobotRepository;
import com.microservice.auth.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

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
