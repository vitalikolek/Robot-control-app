package com.microservice.robot.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "robots")
public class Robot {

    @Id
    private int id;
    @DBRef
    private User administratorId;
    private Status status;
    private String currentTask;
    private Instant lastCheckIn;
}
