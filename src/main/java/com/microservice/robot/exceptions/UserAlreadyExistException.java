package com.microservice.robot.exceptions;

public class UserAlreadyExistException extends RuntimeException {

	public UserAlreadyExistException() {
		super("User already exist");
	}
}
