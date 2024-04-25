package com.microservice.auth.controller;

import com.microservice.auth.service.RobotService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/robot")
public class RobotController {

    @Autowired
    private RobotService robotService;

    @PostMapping
    public void createRobot(Principal principal) {
        robotService.saveRobot(principal.getName());
    }
}
