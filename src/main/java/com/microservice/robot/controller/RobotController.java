package com.microservice.robot.controller;

import com.microservice.robot.data.Robot;
import com.microservice.robot.data.RobotFeedback;
import com.microservice.robot.request.OnRobotRequest;
import com.microservice.robot.request.RobotFeedbackRequest;
import com.microservice.robot.response.RobotStatusResponse;
import com.microservice.robot.service.RobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/robot")
public class RobotController {

    @Autowired
    private RobotService robotService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRobot(Principal principal) {
        robotService.saveRobot(principal.getName());
    }

    @GetMapping
    public List<Robot> getAdminRobots(Principal principal) {
        return robotService.getRobots(principal.getName());
    }

    @PostMapping("/on/{id}")
    public void onRobot(Principal principal, @PathVariable Integer id, @RequestBody OnRobotRequest onRobotRequest) {
        robotService.onRobot(principal.getName(), id, onRobotRequest);
    }

    @PostMapping("/off/{id}")
    public void offRobot(Principal principal, @PathVariable Integer id) {
        robotService.offRobot(principal.getName(), id);
    }

    @GetMapping("/status/{id}")
    public RobotStatusResponse getStatus(Principal principal, @PathVariable Integer id) {
        return robotService.getStatus(principal.getName(), id);
    }

    @GetMapping("/feedback/{id}")
    public List<RobotFeedback> getRobotFeedbacks(Principal principal, @PathVariable Integer id) {
        return robotService.getFeedbacks(principal.getName(), id);
    }

    @PostMapping("/feedback/{id}")
    public void addFeedback(@PathVariable Integer id, @RequestBody RobotFeedbackRequest request) {
        robotService.addFeedback(id, request);
    }
}
