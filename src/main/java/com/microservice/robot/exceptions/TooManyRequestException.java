package com.microservice.robot.exceptions;

public class TooManyRequestException extends RuntimeException {
	public TooManyRequestException() {
		super("Too many requests");
	}
}
