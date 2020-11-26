package com.example.orchestration.demo.exception;

public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(String error) {
		super(error);
	}

}
