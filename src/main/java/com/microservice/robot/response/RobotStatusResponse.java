package com.microservice.robot.response;

import com.microservice.robot.data.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RobotStatusResponse {
    private Status status;
    private String currentTask;
    private Instant lastCheckIn;
}
