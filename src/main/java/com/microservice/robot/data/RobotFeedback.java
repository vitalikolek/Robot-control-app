package com.microservice.robot.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "robot_feedbacks")
public class RobotFeedback {

    @Id
    private int id;
    @DBRef
    private Robot robotId;
    private String feedback;
    private Integer rating;
}
