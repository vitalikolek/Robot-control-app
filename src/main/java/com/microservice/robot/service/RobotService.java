package com.microservice.robot.service;

import com.microservice.robot.data.Robot;
import com.microservice.robot.data.Status;
import com.microservice.robot.data.User;
import com.microservice.robot.persistence.RobotRepository;
import com.microservice.robot.persistence.UserRepository;
import com.microservice.robot.request.OnRobotRequest;
import com.microservice.robot.response.RobotStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RobotService {

    private UserRepository userRepository;
    private RobotRepository robotRepository;

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
            newIndex = lastDocument.getId() + 1;
        }
        robot.setId(newIndex);

        robot.setAdministratorId(user.get());
        robot.setStatus(Status.OFFLINE);
        robot.setCurrentTask("Robot offline");
        robot.setLastCheckIn(Instant.now());

        robotRepository.save(robot);
    }

    public List<Robot> getRobots(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return robotRepository.findAllByAdministratorId(user.get().getId());
    }

    public void onRobot(String email, Integer id, OnRobotRequest request) {
        Robot robot = robotRepository.findById(id);

        if (!robot.getAdministratorId().getEmail().equals(email)) return;

        robot.setCurrentTask(request.getTask());
        robot.setStatus(Status.ONLINE);
        robot.setLastCheckIn(Instant.now());

        robotRepository.save(robot);
    }

    public void offRobot(String email, Integer robotId) {
        Robot robot = robotRepository.findById(robotId);

        if (!robot.getAdministratorId().getEmail().equals(email)) return;

        robot.setCurrentTask("Robot offline");
        robot.setStatus(Status.OFFLINE);
        robot.setLastCheckIn(Instant.now());

        robotRepository.save(robot);
    }

    public RobotStatusResponse getStatus(String email, Integer robotId) {
        Robot robot = robotRepository.findById(robotId);

        if (!robot.getAdministratorId().getEmail().equals(email)) {
            throw new RuntimeException();
        }

        RobotStatusResponse response = new RobotStatusResponse();
        response.setStatus(robot.getStatus());
        response.setCurrentTask(robot.getCurrentTask());
        response.setLastCheckIn(robot.getLastCheckIn());

        return response;
    }
}
