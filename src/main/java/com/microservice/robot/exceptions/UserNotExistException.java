package com.microservice.robot.exceptions;

public class UserNotExistException extends RuntimeException {
	public UserNotExistException() {
		super("User not exist");
	}
}
