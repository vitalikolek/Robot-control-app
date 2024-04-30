package com.microservice.robot.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RobotFeedbackRequest {

    @NotBlank
    private String feedback;
    @NotBlank
    @Min(1)
    @Max(5)
    private Integer rating;
}
